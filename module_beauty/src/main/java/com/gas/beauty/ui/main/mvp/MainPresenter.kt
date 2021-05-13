package com.gas.beauty.ui.main.mvp

import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter
import com.base.paginate.interfaces.EmptyInterface
import com.gas.beauty.app.GankConstants
import com.gas.beauty.bean.GankResponse
import com.gas.beauty.bean.BeautyBean
import com.gas.beauty.ui.main.MainAdapter
import com.lib.commonsdk.rx.RxBindManager
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import timber.log.Timber
import javax.inject.Inject

@ActivityScope
class MainPresenter @Inject constructor(model: MainContract.Model, rootView: MainContract.View) : BasePresenter<MainContract.Model, MainContract.View>(model, rootView) {
    @Inject
   lateinit var mAdapter: MainAdapter
    private var lastPage = 1
    fun requestGirls(pullToRefresh: Boolean) {
        if (pullToRefresh) lastPage = 1 //下拉刷新默认只请求第一页
        RxBindManager.getInstance().doSubscribe(mModel!!.getGirlList(GankConstants.NUMBER_OF_PAGE, lastPage),
                object : Observer<GankResponse<List<BeautyBean>>> {
                    override fun onSubscribe(d: Disposable) {}
                    override fun onNext(result: GankResponse<List<BeautyBean>>) {
                        Timber.e("请求成功success")
                        mView!!.success()
                        if (null != result.results && result.results!!.isNotEmpty()) {
                            if (lastPage == 1) {
                                mAdapter.setNewData(result.results)
                            } else {
                                mAdapter.setLoadMoreData(result.results!!)
                            }
                            if (result.results!!.size < GankConstants.NUMBER_OF_PAGE) { //如果小于10个
                                mAdapter.loadEnd()
                            }
                            lastPage++
                        } else {
                            if (lastPage > 1) {
                                mAdapter.showFooterFail()
                            } else {
                                mAdapter.setEmptyView(EmptyInterface.STATUS_FAIL)
                            }
                        }
                    }

                    override fun onError(e: Throwable) {
                        mView!!.onError()
                        if (lastPage > 1) {
                            mAdapter.showFooterFail()
                        } else {
                            mAdapter.setEmptyView(EmptyInterface.STATUS_FAIL)
                        }
                    }

                    override fun onComplete() {}
                }, mView)
    }

    override fun onDestroy() {}
}