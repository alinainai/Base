package trunk.doi.base.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account.*
import trunk.doi.base.R
import trunk.doi.base.base.BaseFragment
import trunk.doi.base.ui.activity.LoginActivity
import trunk.doi.base.ui.activity.SettingActivity
import trunk.doi.base.util.ToastUtils


/**
 * Created by ly on 2016/5/30 11:07.
 * 我的fragment （首页第五个）
 */
class MineFragment : BaseFragment()  {

    companion object {
        val TAG = "MineFragment"
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }

    override fun initLayoutId(): Int {
        return R.layout.fragment_mine
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup, savedInstanceStae: Bundle?) {



    }

    override fun setListener(view: View, savedInstanceState: Bundle?) {

        tixian.setOnClickListener({ startActivity(Intent(activity, LoginActivity::class.java ))})
        iv_head.setOnClickListener({ ToastUtils.showShort(activity,"头像被点击了")})
        tv_intent.setOnClickListener({ ToastUtils.showShort(activity,"我一不小心被点击了")})
        img_set.setOnClickListener({ startActivity(Intent(activity, SettingActivity::class.java ))})

    }

    override fun initData(savedInstanceState: Bundle?) {

    }




}
