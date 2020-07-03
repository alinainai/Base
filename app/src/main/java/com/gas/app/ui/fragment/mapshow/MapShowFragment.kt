package  com.gas.app.ui.fragment.mapshow

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.gas.app.R
import com.gas.app.ui.fragment.mapshow.di.DaggerMapShowComponent
import com.gas.app.ui.fragment.mapshow.di.MapShowModule
import com.gas.app.ui.fragment.mapshow.mvp.MapShowContract
import com.gas.app.ui.fragment.mapshow.mvp.MapShowPresenter

class MapShowFragment : BaseFragment<MapShowPresenter>(), MapShowContract.View {
    companion object {
        const val TYPE = "type"
        const val OPTION = "option"

        fun setPagerArgs(type: Int, option: Int): Bundle? {
            val args = Bundle()
            args.putInt(TYPE, type)
            args.putInt(OPTION, option)
            return args
        }

        fun newInstance(): MapShowFragment {
            val fragment = MapShowFragment()
            return fragment
        }
    }





    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMapShowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mapShowModule(MapShowModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {

    }


    override fun setData(data: Any?) {

    }

    override fun getWrapActivity(): Activity {
       return activity!!
    }


    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        activity?.finish()
    }
}
