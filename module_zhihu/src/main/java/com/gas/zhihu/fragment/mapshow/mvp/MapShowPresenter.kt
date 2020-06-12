package com.gas.zhihu.fragment.mapshow.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.fragment.mapshow.bean.ISortBean
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
 * Created by GasMvpFragment on 05/31/2020 15:45
 * ================================================
 */

@FragmentScope
class MapShowPresenter
@Inject
constructor(model: MapShowContract.Model, rootView: MapShowContract.View) :
        BasePresenter<MapShowContract.Model, MapShowContract.View>(model, rootView) {

    private var mDispose: Disposable? = null
    fun initOriginData(type: Int) {
        mModel.setType(type)
    }

    fun getFilterData(filter: String) {

        Observable.create(
                ObservableOnSubscribe<List<ISortBean>> {
                    it.onNext(mModel.getMapsByFilter(filter))
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<ISortBean>> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        mDispose = d
                    }

                    override fun onNext(t: List<ISortBean>) {
                        mView.setMapData(t)
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                    }
                })
    }

    override fun onDestroy() {
        mDispose?.dispose()
        super.onDestroy();
    }
}
