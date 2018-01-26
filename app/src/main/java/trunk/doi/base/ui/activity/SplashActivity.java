package trunk.doi.base.ui.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.WindowManager;
import android.widget.TextView;


import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.base.BaseApplication;
import trunk.doi.base.ui.MainActivity;
import trunk.doi.base.util.AppUtils;
import trunk.doi.base.util.Const;
import trunk.doi.base.util.PermissionUtils;
import trunk.doi.base.util.SPUtils;
import trunk.doi.base.util.ToastUtil;

public class SplashActivity extends BaseActivity {


    private Handler handler =new Handler();

    @BindView(R.id.tv_version)
    TextView tv_version;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView(@Nullable Bundle savedInstanceState) {

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        String[] pers={  android.Manifest.permission.READ_PHONE_STATE,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
                    android.Manifest.permission.RECEIVE_SMS};
            PermissionUtils.requestAppPermissions(SplashActivity.this,1008,pers);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0 全透明状态栏
//            View decorView = getWindow().getDecorView();
//            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
//            decorView.setSystemUiVisibility(option);
//            //   getWindow().setStatusBarColor(Color.rgb(0x8f,0xc4,0x50));
//            getWindow().setStatusBarColor(getResources().getColor(color));
//        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4 全透明状态栏
//            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
//            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
//        }

        tv_version.setText("当前版本 v" + AppUtils.getVersionName(BaseApplication.getInstance()));
        String version = SPUtils.loadString(SplashActivity.this, Const.APP_VERSION);
//        if(TextUtils.isEmpty(version) || !version.equals(AppUtils.getVersionName(SplashActivity.this))){
//
//             String[] pers={  android.Manifest.permission.READ_PHONE_STATE,
//                    android.Manifest.permission.CAMERA,
//                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
//                    android.Manifest.permission.ACCESS_COARSE_LOCATION,
//                    android.Manifest.permission.RECEIVE_SMS};
//            PermissionUtils.requestAppPermissions(SplashActivity.this,1008,pers);
//            SPUtils.saveString(SplashActivity.this, Constants.APP_VERSION,AppUtils.getVersionName(BaseApplication.instance));
//
//        }else{

//            if(!PermissionUtils.hasPermissions(SplashActivity.this, android.Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                PermissionUtils.requestPermissions(SplashActivity.this,1001,android.Manifest.permission.READ_EXTERNAL_STORAGE);
//            }else{
//                // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
//                SophixManager.getInstance().queryAndLoadNewPatch();
//            }

            handler.postDelayed(toMain,1000);

      //  countDown();
      //  }



    }


    /**
     * 使用RxJava实现倒计时
     */
//    private void countDown() {
//        final long count = 5;
//        Observable.interval(1, TimeUnit.SECONDS)
//                .take(6)//计时次数
//                .map(new Function<Long, Object>() {
//                    @Override
//                    public Long apply(Long integer) {
//                        return count - integer;
//                    }
//                })
//                .doOnSubscribe(new Action() {
//                    @Override
//                    public void run() { //开始
//                        Log.e("TAG","call");
//
//                    }
//                })
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<Long>() {
//
//                    @Override
//                    public void onComplete() { //结束
//                        Log.e("TAG","onCompleted");
//
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.e("TAG","onError");
//                    }
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(Long aLong) {
//                        Log.e("TAG","onNext"+aLong);
//
//                    }
//                });
//    }



    private Runnable toMain=new Runnable() {
        @Override
        public void run() {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
            SplashActivity.this.finish();
            SplashActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacks(toMain);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1001 && grantResults.length > 0) {
            if (permissions.length > 0 && permissions.length == grantResults.length) {
                for (int i = 0; i < grantResults.length; i++) {
                    //do something
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        // queryAndLoadNewPatch不可放在attachBaseContext 中，否则无网络权限，建议放在后面任意时刻，如onCreate中
//                        SophixManager.getInstance().queryAndLoadNewPatch();
                    } else {
                        ToastUtil.show(SplashActivity.this,"请去设置中开启软件读取文件信息的权限，否则软件不能正常使用");
                    }
                }
            }
        }

    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void initData() {

    }
//    @OnClick({R.id.tv_to_main})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.tv_to_main:
//                SplashActivity.this.startActivity(new Intent(SplashActivity.this, LruActivity.class));
//                SplashActivity.this.finish();
//                SplashActivity.this.overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
//                break;
//        }
//    }




}
