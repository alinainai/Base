package com.gas.zhihu.fragment.fileselect.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.bean.FileItemBean
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/24/2020 11:32
 * ================================================
 */

@FragmentScope
class FileSelectPresenter
@Inject
constructor(model: FileSelectContract.Model, rootView: FileSelectContract.View) :
        BasePresenter<FileSelectContract.Model, FileSelectContract.View>(model, rootView) {
    private var mDispose: Disposable? = null

    fun getFileItem(type:String?){
        Observable.create(ObservableOnSubscribe<List<FileItemBean>> {
            it.onNext( mModel.getDocumentData(type))
        })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object :Observer<List<FileItemBean>>{
            override fun onComplete() {

            }

            override fun onSubscribe(d: Disposable) {
                mDispose=d
            }

            override fun onNext(t: List<FileItemBean>) {
                mView.setPaperData(t)
            }

            override fun onError(e: Throwable) {
                e.printStackTrace()
            }
        })

    }

    override fun onDestroy() {
        mDispose?.let {
            if(it.isDisposed)
                it.dispose()
        }
        super.onDestroy();
    }
}
