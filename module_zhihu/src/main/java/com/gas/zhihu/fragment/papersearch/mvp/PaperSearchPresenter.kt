package com.gas.zhihu.fragment.papersearch.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import java.util.concurrent.TimeUnit

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 06/20/2020 17:46
 * ================================================
 */

@FragmentScope
class PaperSearchPresenter
@Inject
constructor(model: PaperSearchContract.Model, rootView: PaperSearchContract.View) :
        BasePresenter<PaperSearchContract.Model, PaperSearchContract.View>(model, rootView) {


    private val publishSubject: PublishSubject<String> = PublishSubject.create()

    init {
        addDispose(publishSubject.debounce(200, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .switchMap { t -> Observable.just(mModel.getPagersByFilter(t)) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    mView.setPaperData(t)
                }, { e -> e.printStackTrace() }))
    }

    fun initOriginData(type: Int) {
        mModel.setType(type)
    }

    fun startSearch(str: String) {
        publishSubject.onNext(str)
    }

    override fun onDestroy() {
        super.onDestroy();
    }
}
