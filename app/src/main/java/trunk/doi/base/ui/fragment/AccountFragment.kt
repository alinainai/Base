package trunk.doi.base.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account.*
import trunk.doi.base.R
import trunk.doi.base.base.BaseFragment
import trunk.doi.base.ui.activity.test.MainActivity
import trunk.doi.base.ui.activity.utils.WebViewActivity

/**
 * Created by li on 2016/6/29.
 */
class AccountFragment : BaseFragment() {

    override fun initLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup, savedInstanceState: Bundle ?) {



    }

    override fun initData(savedInstanceState: Bundle ?) {


    }

    override fun setListener(view: View, savedInstanceState: Bundle ?) {
        tixian.setOnClickListener({ startActivity(Intent(activity,MainActivity::class.java ))})
        ll_chongzhi.setOnClickListener({ startActivity(Intent(activity,WebViewActivity::class.java ).putExtra("url","http://www.q2002.cn"))})
    }

    companion object {

        val TAG = "AccountFragment"
        fun newInstance(): AccountFragment {
            return AccountFragment()
        }
    }


}
