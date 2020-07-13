package com.gas.app.ui.fragment.product

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.app.R
import com.gas.app.ui.fragment.product.di.DaggerProductComponent
import com.gas.app.ui.fragment.product.mvp.ProductContract
import com.gas.app.ui.fragment.product.mvp.ProductPresenter
import kotlinx.android.synthetic.main.fragment_product.*
import java.io.File

/**
 * ================================================
 * Description:
 *
 *
 * Created by GasMvpFragment on 11/30/2019 14:55
 * ================================================
 */
class ProductFragment : BaseFragment<ProductPresenter>(), ProductContract.View {
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerProductComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_product, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        activity?.application?.assets.let {
            val typeface = Typeface.createFromAsset(it, "font${File.separator}DINCond-Regular.otf")
            productNameText.typeface = typeface
        }

        productNameText.setOnClickListener {
            circleProgress.setProgress(70F)
        }

    }
    override fun setData(data: Any?) {}
    override fun showLoading() {}
    override fun hideLoading() {}
    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {}

    companion object {
        @JvmStatic
        fun newInstance(): ProductFragment {
            return ProductFragment()
        }
    }
}