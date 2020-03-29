package com.gas.zhihu.ui.map;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.baseui.dialog.CommonDialog;
import com.base.baseui.utils.ViewUtils;
import com.base.lib.base.BaseActivity;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.zhihu.R;
import com.gas.zhihu.ui.map.di.DaggerMapComponent;
import com.gas.zhihu.ui.map.mvp.MapContract;
import com.gas.zhihu.ui.map.mvp.MapPresenter;
import com.gas.zhihu.ui.show.ShowActivity;
import com.lib.commonsdk.utils.GasAppUtils;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.OnClick;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * desc: 地图搜索功能
 * <p>
 * created by author ljx
 * date  2020/3/28
 * email 569932357@qq.com
 * <p>
 * ================================================
 */

public class MapActivity extends BaseActivity<MapPresenter> implements MapContract.View {

    @BindView(R.id.et_input)
    EditText etInput;
    private Context mContext;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerMapComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
        mContext = this;
        return R.layout.zhihu_activity_map;

    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @OnClick({R.id.iv_clear, R.id.btn_go_login, R.id.text_see_match_rule})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_clear:
                etInput.setText("");
                break;
            case R.id.btn_go_login:
                toSearch();
                break;
            case R.id.text_see_match_rule:
                showRuleDialog();
                break;
        }
    }


    private void showRuleDialog() {
        View view = LayoutInflater.from(mContext).inflate(R.layout.zhihu_dialog_forget_pwd, null);
        TextView tv = view.findViewById(R.id.dialog_title);
        tv.setText("这个是匹配规则的提示");
        CommonDialog pauseDialog = new CommonDialog.Builder(mContext).setCustomView(view).create();
        view.findViewById(R.id.btn_go_login).setOnClickListener(view1 -> {
            pauseDialog.dismiss();
        });
        pauseDialog.show();
    }

    private void toSearch() {

        String search = ViewUtils.getNoSpaceText(etInput);

        if (TextUtils.isEmpty(search)) {
            GasAppUtils.toast("请输入正确的匹配规则");
            return;
        }
        startActivity(new Intent(mContext, ShowActivity.class));

    }

    /**
     * 双击退出函数
     */
    private static Boolean isExit = false;

    private void exitBy2Click() {
        Timer tExit;
        if (!isExit) {
            isExit = true; // 准备退出
            ArmsUtils.snackbarText("再按一次退出程序");
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); //如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务

        } else {
            ArmsUtils.exitApp();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click();      //调用双击退出函数
        }
        return false;
    }

}
