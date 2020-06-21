package com.gas.zhihu.fragment.paper.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.bean.PaperShowBean
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/17/2020 10:01
 * ================================================
 */

@FragmentScope
class PagerPresenter
@Inject
constructor(model: PagerContract.Model, rootView: PagerContract.View) :
        BasePresenter<PagerContract.Model, PagerContract.View>(model, rootView) {


    private var mDispose: Disposable? = null

    fun initOriginData(type: Int) {
        invalidData()
        mModel.setType(type)
        mModel.getValidMapList()
    }

    fun invalidData(){
        mModel.resetOriginData()
    }

    fun getFilterData(voltage: String, mapKey: String) {

        Observable.create(ObservableOnSubscribe<List<PaperShowBean>> {
            it.onNext(mModel.getPagersByFilter(voltage, mapKey))
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<PaperShowBean>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        mDispose = d
                    }

                    override fun onNext(t: List<PaperShowBean>) {
                        mView.setPaperData(t)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }

    fun getValidMaps(): List<MapBean> {
        return mModel.getValidMapList()
    }

    fun deletePaperInfo(key: String) {
        mModel?.deletePaperInfo(key)
        mView?.updateData()
    }

    override fun onDestroy() {
        mDispose?.isDisposed
        super.onDestroy();
    }
}
