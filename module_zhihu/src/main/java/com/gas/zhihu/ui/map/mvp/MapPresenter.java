package com.gas.zhihu.ui.map.mvp;


import android.content.res.AssetManager;

import androidx.core.app.ComponentActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.util.PermissionUtil;
import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.utils.MapBeanDbUtils;
import com.gas.zhihu.utils.ZhihuUtils;
import com.google.gson.reflect.TypeToken;
import com.lib.commonsdk.utils.AssetHelper;
import com.lib.commonsdk.utils.FileUtils;
import com.lib.commonsdk.utils.GasAppUtils;
import com.lib.commonsdk.utils.GsonUtils;
import com.lib.commonsdk.utils.Utils;
import com.lib.commonsdk.utils.ZipUtils;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import me.jessyan.rxerrorhandler.core.RxErrorHandler;


@ActivityScope
public class MapPresenter extends BasePresenter<MapContract.Model, MapContract.View> {

    private static final String DATA_JSON_PATH = "config" + File.separator + "datajson.json";
    private static final String IMAGE_DATA_NAME = "testimage.zip";
    private static final String DATA_IMAGE_PATH = "data" + File.separator + IMAGE_DATA_NAME;
    private static final String TEST_IMAGE_PATH = "testimage";

    private Disposable mDispose;

    @Inject
    RxErrorHandler mErrorHandler;


    @Inject
    public MapPresenter(MapContract.Model model, MapContract.View rootView) {
        super(model, rootView);
    }

    /**
     * 使用 2017 Google IO 发布的 Architecture Components 中的 Lifecycles 的新特性 (此特性已被加入 Support library)
     * 使 {@code Presenter} 可以与 {@link ComponentActivity} 和 {@link Fragment} 的部分生命周期绑定
     */
    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    void onCreate() {
        requestStoragePermission();
    }

    public void requestStoragePermission() {
        //请求外部存储权限用于适配android6.0的权限管理机制
        PermissionUtil.externalStorage(new PermissionUtil.RequestPermission() {
            @Override
            public void onRequestPermissionSuccess() {
                //request permission success, do something.
                if (mModel.getMapDataCount() == 0 || ZhihuUtils.getSpVersionCode() != GasAppUtils.getAppVersionCode()) {
                    getMapDataFromAsset();
                }
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                GasAppUtils.toast("请开启数据读取权限，否则搜索功能无法读取数据");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

            }
        }, mView.getRxPermissions(), mErrorHandler);
    }

    private void getMapDataFromAsset() {

        File filePath = Utils.getExternalFilesDir(mView.getActivity());
        File imageZipFile = new File(filePath.getPath(), IMAGE_DATA_NAME);
        File imageFile = new File(filePath.getPath(), TEST_IMAGE_PATH);


        Observable.just(DATA_JSON_PATH)
                .flatMap((Function<String, ObservableSource<String>>) s -> {
                    StringBuilder stringBuilder = new StringBuilder();
                    //获取assets资源管理器
                    AssetManager assetManager = mView.getActivity().getApplicationContext().getAssets();
                    //通过管理器打开文件并读取
                    InputStreamReader reader = new InputStreamReader(assetManager.open(s));
                    BufferedReader bf = new BufferedReader(reader);
                    String line;
                    while ((line = bf.readLine()) != null) {
                        stringBuilder.append(line);
                    }
                    reader.close();
                    bf.close();
                    return Observable.just(stringBuilder.toString());
                })
                .subscribeOn(Schedulers.io())
                .flatMap((Function<String, ObservableSource<Boolean>>) s -> {
                    MapBeanDbUtils.deleteAll();
                    Type type = new TypeToken<List<MapBean>>() {
                    }.getType();
                    List<MapBean> list = GsonUtils.fromJson(s, type);
                    for (MapBean bean : list) {
                        //添加预存数据到数据库
                        MapBean dbBean = MapBeanDbUtils.queryData(bean.getKeyName());
                        if (dbBean == null) {
                            MapBeanDbUtils.insertMapBean(bean);
                        } else {
                            dbBean.updateInfo(bean);
                            MapBeanDbUtils.updateBean(dbBean);
                        }
                    }
                    return Observable.just(true);
                })
                .flatMap((Function<Boolean, ObservableSource<Boolean>>) aBoolean -> {
                    if (FileUtils.isFileExists(imageZipFile)) {
                        FileUtils.delete(imageZipFile);
                    }
                    AssetHelper.copyFilesFromAssets(mView.getActivity(), DATA_IMAGE_PATH, filePath.getPath());
                    return Observable.just(true);
                })
                .flatMap((Function<Boolean, ObservableSource<Boolean>>) aBoolean -> {
                    if (FileUtils.isFileExists(imageZipFile)) {
                        if (FileUtils.isFileExists(imageFile)) {
                            FileUtils.delete(imageFile);
                        }
                        ZipUtils.unzipFile(imageZipFile, filePath);
                        FileUtils.delete(imageZipFile);
                    }
                    return Observable.just(true);
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mDispose = d;
                        mView.showLoading();
                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        ZhihuUtils.setSpVersionCode();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mView.hideLoading();
                    }

                    @Override
                    public void onComplete() {
                        mView.hideLoading();
                    }
                });


    }

    public List<String> getSearchRecord() {
        return mModel.getRecordHistory();
    }

    public void setSearchRecord(List<String> items) {
        mModel.setRecordHistory(items);
    }

    public void clearSearchRecord() {
        mModel.clearRecordHistory();
    }


    @Override
    public void onDestroy() {
        if (mDispose != null && !mDispose.isDisposed()) {
            mDispose.dispose();
        }
        super.onDestroy();
    }
}
