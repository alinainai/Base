package com.gas.zhihu.fragment.mapshow.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.bean.MapBean
import com.gas.zhihu.fragment.mapshow.bean.ISortBean
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit
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

    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        addDispose(publishSubject.debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMap { t -> Observable.just(mModel.getSortBeanWithFilter(t)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> mView.setMapData(t) }, { e -> e.printStackTrace() }))
    }

    fun initOriginData(type: Int) {
        mModel.setType(type)
    }

    fun startSearch(str: String) {
        publishSubject.onNext(str)
    }


    fun getFilterData(filter: String) {
        addDispose(Observable.create(
                ObservableOnSubscribe<List<ISortBean>> {
                    it.onNext(mModel.getSortBeanWithFilter(filter))
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t -> mView.setMapData(t) }, { e -> e.printStackTrace() }))
    }

}
