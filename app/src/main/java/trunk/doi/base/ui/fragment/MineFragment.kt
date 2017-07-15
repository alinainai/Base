package trunk.doi.base.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_account.*
import trunk.doi.base.R
import trunk.doi.base.base.BaseFragment
import trunk.doi.base.ui.activity.extra.LoginActivity


/**
 * Created by ly on 2016/5/30 11:07.
 * 我的fragment
 */
class MineFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener  {


    private val handler_2 = Handler()
    //下拉刷新
    override fun onRefresh() {
        handler_2.postDelayed({
            activity.runOnUiThread(Runnable {
                type_item_swip.setRefreshing(false)
            })
        }, 2000)

    }


    override fun initLayoutId(): Int {
        return R.layout.fragment_account
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup, savedInstanceStae: Bundle?) {



    }

    override fun setListener(view: View, savedInstanceState: Bundle?) {

        type_item_swip.setColorSchemeResources(R.color.white)
           type_item_swip.setProgressBackgroundColorSchemeColor(activity.resources.getColor(R.color.cff3e19))
        type_item_swip.setOnRefreshListener(this)
        //实现首次自动显示加载提示
        type_item_swip.post(Runnable { type_item_swip.setRefreshing(true) })
        handler_2.postDelayed({
            activity.runOnUiThread(Runnable {
                type_item_swip.setRefreshing(false)
            })
        }, 2000)
        tixian.setOnClickListener({ startActivity(Intent(activity, LoginActivity::class.java ))})
    }

    override fun initData(savedInstanceState: Bundle?) {

    }

    companion object {
        val TAG = "MineFragment"


        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }


}
