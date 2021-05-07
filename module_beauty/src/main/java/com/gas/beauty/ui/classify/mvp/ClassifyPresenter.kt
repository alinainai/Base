package com.gas.beauty.ui.classify.mvp

import android.app.Application
import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.base.paginate.interfaces.EmptyInterface
import com.gas.beauty.app.GankConstants
import com.gas.beauty.bean.GankItemBean
import com.gas.beauty.ui.classify.ClassifyAdapter
import com.lib.commonsdk.rx.RxBindManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import java.util.*
import javax.inject.Inject

@FragmentScope
class ClassifyPresenter @Inject constructor(model: ClassifyModle?, rootView: ClassifyContract.View?) : BasePresenter<ClassifyModle?, ClassifyContract.View?>(model, rootView) {
    @JvmField
    @Inject
    var mApplication: Application? = null

    @JvmField
    @Inject
    var mClassifyAdapter: ClassifyAdapter? = null
    private var mPage = 1 //页数
    fun getGankItemData(suburl: String?, refresh: Boolean) {
        if (refresh) {
            mPage = 1
        }
        RxBindManager.getInstance().doSubscribe(mModel!!.getGankItemData(String.format(Locale.CHINA, "data/%s/" + GankConstants.NUMBER_OF_PAGE + "/%d", suburl, mPage)),
                object : Observer<List<GankItemBean>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(result: List<GankItemBean>) {
                        mView!!.loadEnd()
                        if (null != result && result.size > 0) {
                            if (mPage == 1) {
                                mClassifyAdapter!!.setNewData(result)
                            } else {
                                mClassifyAdapter!!.setLoadMoreData(result)
                            }
                            if (result.size < GankConstants.NUMBER_OF_PAGE) { //如果小于10个
                                mClassifyAdapter!!.loadEnd()
                            }
                            mPage++
                        } else {
                            mView!!.loadEnd()
                            if (mPage > 1) {
                                mClassifyAdapter!!.showFooterFail()
                            } else {
                                mClassifyAdapter!!.setEmptyView(EmptyInterface.STATUS_FAIL)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        if (mPage > 1) {
                            mClassifyAdapter!!.showFooterFail()
                        } else {
                            mClassifyAdapter!!.setEmptyView(EmptyInterface.STATUS_FAIL)
                        }
                        mView!!.loadEnd()
                    }

                    override fun onComplete() {}
                }, mView)
    }

    override fun onDestroy() {
        mApplication = null
        super.onDestroy()
    }
}