package trunk.doi.base.ui.activity.utils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.util.MD5;
import trunk.doi.base.util.ToastUtils;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * Use the {@link ImageZoomFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ImageZoomFragment extends BaseFragment {


    private static final String TAG = "ImageZoomFragment";
    private static final String URL_TAG = "URL_TAG";
    @BindView(R.id.photo_view)
    PhotoView photoView;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    private String url;
    private Bitmap mBmtp;


    public ImageZoomFragment() {
    }


    public static ImageZoomFragment newInstance(String url) {
        ImageZoomFragment fragment = new ImageZoomFragment();
        Bundle args = new Bundle();
        args.putString(URL_TAG, url);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    protected int initLayoutId() {
        return R.layout.fragment_image_zoom;
    }
    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) { }
    @Override
    public void setListener(View view, Bundle savedInstanceState) {}
    @Override
    public void initData(Bundle savedInstanceState) {
        if (getArguments() == null && getArguments().getString(URL_TAG)==null) {
            return;
        }
        url = getArguments().getString(URL_TAG);
        Glide.with(context)
                .load(url)
                .asBitmap()
                .into(new BitmapImageViewTarget(photoView){
            @Override
            public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
                super.onResourceReady(bitmap, anim);
                progressBar.setVisibility(View.GONE);
                mBmtp=bitmap;
            }
        });
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float x, float y) {
                getActivity().finish();
               getActivity().overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
            }
        });
    }

    public void saveDrawble(){

        if(mBmtp==null) return;
        final String name= MD5.GetMD5Code(url).substring(8,24)+".jpg";
        if(new File(BaseApplication.AJYFILE_IMG,name).exists()){
            ToastUtils.show(getContext(),"已保存"+BaseApplication.AJYFILE_IMG,0);
            return;
        }
        Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> sub) {
                String path = BaseApplication.AJYFILE_IMG+ File.separator+name;
                OutputStream os=null;
                try{
                    os= new FileOutputStream(path);
                    mBmtp.compress(Bitmap.CompressFormat.PNG, 100, os);
                }catch(Exception e){
                   sub.onError(e);
                }finally {
                        try {
                            if(os!=null)
                            os.close();
                            sub.onNext("保存在"+BaseApplication.AJYFILE_IMG);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    sub.onCompleted();
                }
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable e) {
                        ToastUtils.showShort(context,e.toString());
                    }
                    @Override
                    public void onNext(String s) {
                        ToastUtils.showShort(context,s);
                    }
                });
    }

    /**
     * 下载文件
     */
    public void downloadFile(String downloadUrl) {
        int DEFAULT_TIMEOUT= 10;
        final Request request = new Request.Builder().url(downloadUrl).tag(this).build();

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //配置log打印拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient=   builder.addInterceptor(loggingInterceptor).retryOnConnectionFailure(false).addNetworkInterceptor(loggingInterceptor).build();

        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                ToastUtils.showShort(context,"加载失败");
            }
            @Override
            public void onResponse(Call call, final Response response)  {

                Observable.create(new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> sub) {

                        InputStream inputStream = response.body().byteStream();
                        FileOutputStream fileOutputStream = null;
                        try {
                            if(inputStream.available()==0){
                                sub.onError(new RuntimeException("文件未找到"));
                                return;
                            }
                            fileOutputStream = new FileOutputStream(new File(BaseApplication.AJYFILE_IMG,String.valueOf(System.currentTimeMillis())+".jpg"));
                            byte[] buffer = new byte[2048];
                            int len = 0;
                            while ((len = inputStream.read(buffer)) != -1) {
                                fileOutputStream.write(buffer, 0, len);
                            }
                            fileOutputStream.flush();
                            sub.onNext("已经保存在"+BaseApplication.AJYFILE_IMG);
                        } catch (IOException e) {
                            sub.onError(e);
                        }finally {
                            try {
                                if(fileOutputStream!=null){
                                    fileOutputStream.close();
                                }
                                inputStream.close();
                            } catch (IOException e) { }
                            sub.onCompleted();
                        }
                    }
                }).subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(new Subscriber<String>() {
                            @Override
                            public void onCompleted() {

                            }
                            @Override
                            public void onError(Throwable e) {
                                ToastUtils.showShort(context,e.toString());
                            }
                            @Override
                            public void onNext(String s) {
                                ToastUtils.showShort(context,s);
                            }
                        });

            }
        });

    }

}
