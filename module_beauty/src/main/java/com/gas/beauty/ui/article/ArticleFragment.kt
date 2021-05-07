package com.gas.beauty.ui.article

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.base.baseui.utils.ViewUtils
import com.base.baseui.view.TitleView
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.gas.beauty.R
import com.gas.beauty.ui.article.di.ArticleModule
import com.gas.beauty.ui.article.di.DaggerArticleComponent
import com.gas.beauty.ui.article.mvp.ArticleContract
import com.gas.beauty.ui.article.mvp.ArticlePresenter
import com.google.android.material.tabs.TabLayout
import com.lib.commonsdk.adapter.AdapterViewPager
import dagger.Binds
import dagger.Provides
import javax.inject.Inject

/**
 * ================================================
 * 展示 GasLazyLoadFragment 的用法
 *
 * @see [Component wiki 官方文档](https://github.com/JessYanCoding/MVPArms)
 * Created by JessYan on 09/04/2019 11:17
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
class ArticleFragment : LazyLoadFragment<ArticlePresenter>(), ArticleContract.View {
    var mTitleView: TitleView? = null
    var mTabLayout: TabLayout? = null
    var mViewPager: ViewPager? = null


    @Inject
    lateinit  var mAdapter: AdapterViewPager

    @Inject
    lateinit var mFragments:@JvmSuppressWildcards List<Fragment>

    @Inject
    lateinit var mTitles: Array<String>
    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerArticleComponent
                .builder()
                .appComponent(appComponent)
                .articleModule(ArticleModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.gank_fragment_article, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mTitleView = requireView().findViewById(R.id.article_titleView)
        mTabLayout = requireView().findViewById(R.id.magic_indicator)
        mViewPager = requireView().findViewById(R.id.viewpager)
    }

    override fun initData(savedInstanceState: Bundle?) {}
    override fun setData(data: Any?) {}
    override val wrapContext: Context
        get() = mContext
    override val currentFragment: Fragment
        get() = this

    override fun lazyLoadData() {
        ViewUtils.setIndicatorWidth(mTabLayout!!, 5)
        mTabLayout!!.setupWithViewPager(mViewPager)
        mViewPager!!.adapter = mAdapter
        mViewPager!!.offscreenPageLimit = mTitles.size - 1
    }

    override fun showMessage(message: String) {}

    companion object {
        @JvmStatic
        fun newInstance(): ArticleFragment {
            return ArticleFragment()
        }
    }
}