package com.gas.zhihu.fragment.addpaper.mvp

import com.base.lib.di.scope.FragmentScope
import com.base.lib.mvp.BasePresenter
import com.gas.zhihu.app.ZhihuConstants
import com.gas.zhihu.bean.MapSelectShowBean
import com.gas.zhihu.bean.PaperBean
import com.lib.commonsdk.utils.AppUtils
import com.lib.commonsdk.utils.FileUtils
import com.lib.commonsdk.utils.Utils
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import java.io.File
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 05/21/2020 11:45
 * ================================================
 */

@FragmentScope
class AddPaperPresenter
@Inject
constructor(model: AddPaperContract.Model, rootView: AddPaperContract.View) :
        BasePresenter<AddPaperContract.Model, AddPaperContract.View>(model, rootView) {

    private var mDispose: Disposable? = null

    fun showMapDialog() {
        mModel.getMapList().apply {
            val maps = mutableListOf<MapSelectShowBean>()
            forEach {
                maps.add(MapSelectShowBean(it))
            }
            mView.showMapSelectDialog(maps)
        }
    }

    fun showVoltageDialog() {
        mView.showVolSelectDialog(mModel.getVoltageList())
    }

    fun addPaperToDatabase(bean: PaperBean, absolutePath: String) {
        Observable.create(ObservableOnSubscribe<PaperBean> {
            it.onNext(bean)
        })
                .flatMap(object : Function<PaperBean, Observable<Int>> {
                    override fun apply(t: PaperBean): Observable<Int> {
                        if (mModel.insertPaperBean(t)) {
                            val file = File("${Utils.getExternalFilesDir(AppUtils.getApp())}${File.separator}${ZhihuConstants.FILE_ZIP_FOLDER}",
                                    bean.pathName)
                            if (FileUtils.copy(File(absolutePath), file)) {
                                return Observable.just(0)
                            }
                            return Observable.just(2)
                        } else {
                            return Observable.just(1)
                        }

                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<Int> {
                    override fun onComplete() {

                    }

                    override fun onSubscribe(d: Disposable) {
                        mView.showLoading()
                        mDispose = d
                    }

                    override fun onNext(t: Int) {
                        when(t){
                            0->{mView.showCommitSuccess()}
                            1->{  mView.showMessage("保存失败，目标已经插入")}
                            2->{  mView.showMessage("保存失败，文件获取失败")}
                        }
                        mView.hideLoading()
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        mView.hideLoading()
                        mView.showMessage("保存失败，发生未知异常")
                    }
                })
    }

    override fun onDestroy() {
        mDispose?.let {
            if (it.isDisposed)
                it.dispose()
        }
        super.onDestroy();
    }
}
