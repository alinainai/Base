package trunk.doi.base.ui.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseActivity;
import trunk.doi.base.util.ToastUtils;
import trunk.doi.base.view.Lock9View;
import trunk.doi.base.view.LockPatternIndicator;

/**
 */
public class GuesterActivity extends BaseActivity {

    @BindView(R.id.lock_9_view)
    Lock9View lock9View;//锁屏界面
    @BindView(R.id.tv_user_name)
    TextView tv_user_name;//账号
    @BindView(R.id.tv_lock_Remind_info)
    TextView tv_lock_Remind_info;
    @BindView(R.id.tv_forget_pwd)
    TextView tv_forget_pwd;//忘记手势密码
    @BindView(R.id.tv_lock_Remind)
    TextView tv_lock_Remind;//没有账号的提示信息
    @BindView(R.id.lpi_indicator)
    LockPatternIndicator lpi_indicator;//没有账号的提示信息

    private String lock_keep_key ;//是否是验证
    private String from;//跳转的标志
    private String lock_key = null;
    private int input_num = 1;//输入次数
    private static final int error_num = 4;//允许错误总次数
    private Animation shake;//震动动画
   private  String userMobile;

    @Override
    protected int initLayoutId() {
        return R.layout.activity_guester_set;
    }

    @Override
    public void initView(Bundle savedInstanceState) {

        lock_keep_key="12369";
        userMobile="18519702340";
        from=getIntent().getStringExtra("from");

        if (!TextUtils.isEmpty(lock_keep_key)) {// 有用户信息

            tv_lock_Remind.setVisibility(View.GONE);
            tv_forget_pwd.setVisibility(View.VISIBLE);
            tv_user_name.setVisibility(View.VISIBLE);
            tv_user_name.setText(userMobile);
            //测试数据
            tv_lock_Remind_info.setVisibility(View.VISIBLE);

        } else {// 无密码
            tv_lock_Remind.setText("为了您的账户安全，请您设置手势密码");
            tv_forget_pwd.setVisibility(View.GONE);
            tv_user_name.setVisibility(View.GONE);
            tv_lock_Remind_info.setVisibility(View.GONE);
        }

    }

    @Override
    public void setListener() {

        tv_forget_pwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        lock9View.setCallBack(new Lock9View.CallBack() {
            @Override
            public void onFinish(String password) {
                ToastUtils.showShort(context,password);

                lpi_indicator.setDefaultIndicator();
                if(!TextUtils.isEmpty(password)){
                    List<Integer> ints=new ArrayList<Integer>();
                    for(int i=0;i<password.length();i++){
                        ints.add(Integer.valueOf(password.substring(i,i+1)));
                    }
                   lpi_indicator.setIndicator(ints);
                }
                Gesture(password);
            }
        });

    }

    @Override
    public void initData() {

        shake = AnimationUtils.loadAnimation(this, R.anim.shake_x);

    }

    /**
     * what： 手势逻辑
     * who： fjw
     * when：2016/07/19 15:13
     */
    private void Gesture(String password) {
        if (TextUtils.isEmpty(lock_keep_key)) {//无密码
            Log.e("手势密码", "无密码" + input_num);
            if (input_num == 1) {//设置手势
                Log.e("手势密码", "无密码设置手势密码" + input_num);
                if (password.length() < 3) {
                    tv_lock_Remind.setText("最少连接3个点,请重新输入");
                    tv_lock_Remind.setTextColor(Color.RED);
                    return;
                }
                lock_key = password;
                input_num++;
                tv_lock_Remind.setText("确认手势密码");
                tv_lock_Remind.setTextColor(Color.RED);
                tv_lock_Remind.startAnimation(shake);
            } else if (input_num == 2) {//二次确认手势手势设置成功
                if (lock_key.equals(password)) {
                  //  SPUtils.getInstance().putString(userMobile + Const.LOCK_KEEP_KEY, lock_key);
                    ToastUtils.showShort(context, "手势密码设置成功");
                    finish();
                    overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);
                } else {
                    tv_lock_Remind.setText("两次输入的不一样,请从新设置手势密码");
                    tv_lock_Remind.setTextColor(Color.RED);
                    input_num = 1;
                    lock_key = "";
                    tv_lock_Remind.startAnimation(shake);
                }
            }
        } else {//有密码

            if (lock_keep_key.equals(password)) {
                finish();
                overridePendingTransition(android.R.anim.fade_in,android.R.anim.fade_out);

            } else {
                Log.e("手势密码", "有密码密码不正确" + input_num);
                tv_lock_Remind_info.setText("密码错误，还可输入" + (error_num - input_num) + "次");
                tv_lock_Remind_info.startAnimation(shake);
                input_num++;
                if (input_num > error_num)
                    ToastUtils.showShort(context, "输入次数用完了");//进行操作
            }
        }
    }

    @Override
    public void onBackPressed() {

    }

    @OnClick(R.id.tv_forget_pwd)
    public void onClick() {
    }
}