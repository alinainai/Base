package com.gas.zhihu.ui.main.mvp;

import android.app.Application;
import android.content.res.AssetManager;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;

import com.base.lib.di.scope.ActivityScope;
import com.base.lib.mvp.BasePresenter;
import com.base.lib.util.PermissionUtil;
import com.gas.zhihu.bean.MapBean;
import com.gas.zhihu.bean.PaperBean;
import com.gas.zhihu.utils.MapBeanDbUtils;
import com.gas.zhihu.utils.PagerBeanDbUtils;
import com.gas.zhihu.utils.ZhihuUtils;
import com.google.gson.reflect.TypeToken;
import com.lib.commonsdk.utils.AppUtils;
import com.lib.commonsdk.utils.AssetHelper;
import com.lib.commonsdk.utils.FileUtils;
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

import static com.gas.zhihu.app.ZhihuConstants.DATA_JSON_PATH;
import static com.gas.zhihu.app.ZhihuConstants.EXPERIENCE_JSON_PATH;
import static com.gas.zhihu.app.ZhihuConstants.FILE_ZIP;
import static com.gas.zhihu.app.ZhihuConstants.FILE_ZIP_ASSET;
import static com.gas.zhihu.app.ZhihuConstants.FILE_ZIP_FOLDER;
import static com.gas.zhihu.app.ZhihuConstants.IMAGE_ZIP;
import static com.gas.zhihu.app.ZhihuConstants.IMAGE_ZIP_ASSET;
import static com.gas.zhihu.app.ZhihuConstants.IMAGE_ZIP_FOLDER;

@ActivityScope
public class MainPresenter extends BasePresenter<MainContract.Model, MainContract.View> {

    private Disposable mDispose;

    @Inject
    RxErrorHandler mErrorHandler;

    @Inject
    Application mApplication;

    @Inject
    RecyclerView.Adapter mAdapter;

    @Inject
    public MainPresenter(MainContract.Model model, MainContract.View rootView) {
        super(model, rootView);
    }

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
                if (mModel.getMapDataCount() == 0 || ZhihuUtils.getSpVersionCode() != AppUtils.getAppVersionCode()) {
                    getMapDataFromAsset();
                }
            }

            @Override
            public void onRequestPermissionFailure(List<String> permissions) {
                AppUtils.toast("请开启数据读取权限，否则搜索功能无法读取数据");
            }

            @Override
            public void onRequestPermissionFailureWithAskNeverAgain(List<String> permissions) {

            }
        }, mView.getRxPermissions(), mErrorHandler);
    }

    private void getMapDataFromAsset() {

        File filePath = Utils.getExternalFilesDir(mView.getActivity());

        File imageZipFile = new File(filePath.getPath(), IMAGE_ZIP);
        File imageFile = new File(filePath.getPath(), IMAGE_ZIP_FOLDER);

        File fileZipFile = new File(filePath.getPath(), FILE_ZIP);
        File fileFile = new File(filePath.getPath(), FILE_ZIP_FOLDER);


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
                .flatMap((Function<String, ObservableSource<String>>) s -> {
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
                    return Observable.just(EXPERIENCE_JSON_PATH);
                })
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
                .flatMap((Function<String, ObservableSource<Boolean>>) s -> {
                    PagerBeanDbUtils.deleteAll();
                    Type type = new TypeToken<List<MapBean>>() {
                    }.getType();
                    List<PaperBean> list = GsonUtils.fromJson(s, type);
                    for (PaperBean bean : list) {
                        //添加预存数据到数据库
                        PaperBean dbBean = PagerBeanDbUtils.queryData(bean);
                        if (dbBean == null) {
                            PagerBeanDbUtils.insertMapBean(bean);
                        } else {
                            dbBean.updateInfo(bean);
                            PagerBeanDbUtils.updateBean(dbBean);
                        }
                    }
                    return Observable.just(true);
                })
                .flatMap((Function<Boolean, ObservableSource<Boolean>>) aBoolean -> {
                    if (FileUtils.isFileExists(imageZipFile)) {
                        FileUtils.delete(imageZipFile);
                    }
                    if (FileUtils.isFileExists(fileZipFile)) {
                        FileUtils.delete(fileZipFile);
                    }

                    AssetHelper.copyFilesFromAssets(mView.getActivity(), IMAGE_ZIP_ASSET, filePath.getPath());
                    AssetHelper.copyFilesFromAssets(mView.getActivity(), FILE_ZIP_ASSET, filePath.getPath());

                    return Observable.just(true);
                })
                .flatMap((Function<Boolean, ObservableSource<Boolean>>) aBoolean -> {
                    if (FileUtils.isFileExists(imageZipFile)) {
                        ZipUtils.unzipFile(imageZipFile, filePath);
                        FileUtils.delete(imageZipFile);
                    }
                    if (FileUtils.isFileExists(fileZipFile)) {
                        ZipUtils.unzipFile(fileZipFile, filePath);
                        FileUtils.delete(fileZipFile);
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

    @Override
    public void onDestroy() {

        if (mDispose != null && !mDispose.isDisposed()) {
            mDispose.dispose();
        }
        super.onDestroy();

    }


}
