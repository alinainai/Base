package com.gas.app.ui.activity.newsplash.mvp

import android.app.Application
import android.content.Context
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.base.lib.di.scope.ActivityScope
import com.base.lib.mvp.BasePresenter
import com.gas.app.R
import com.lib.commonsdk.kotlin.extension.closeQuietly
import com.lib.commonsdk.kotlin.extension.getAppVersionName
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpTemplate on 07/22/2020 20:29
 * ================================================
 */

@ActivityScope
class SplashPresenter
@Inject
constructor(model: SplashContract.Model, rootView: SplashContract.View) :
        BasePresenter<SplashContract.Model, SplashContract.View>(model, rootView) {

    private val videoName = "welcome_video.mp4"
    private val timeDownNum = 5L

    @Inject
    lateinit var application: Application
    private val compositeDisposable: CompositeDisposable by lazy {
        CompositeDisposable()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_CREATE)
    fun onCreate() {
        copyResourceToSD()
    }

    private fun copyResourceToSD() {
        val videoFile = application.getFileStreamPath(videoName)
        if (!videoFile.exists()) {
            compositeDisposable.add(Observable.just(videoName)
                    .flatMap { s ->
                        Observable.just(copyVideoFile(s))
                                .subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                    }.subscribe({ s ->
                        s?.takeIf { it.isNotBlank() }?.let {
                            mView.playVideo(it)
                        }
                    }, { e -> e.printStackTrace() }, {}))
        } else {
            mView.playVideo(videoFile.path)
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        mView.versionCode(getAppVersionName())
        autoTimeDown()
    }

    override fun onDestroy() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        super.onDestroy()
    }

    //点击跳转，强制跳转到Main
    fun forceToMainPage() {
        if (compositeDisposable.isDisposed) {
            compositeDisposable.dispose()
        }
        mView.toMainPage()
    }

    private fun autoTimeDown() {
        compositeDisposable.add(Observable.intervalRange(0, timeDownNum, 0, 1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ l ->
                    if (l != timeDownNum-1) {
                        mView.tvTimeDown(timeDownNum-1 - l)
                    }
                }, { e -> e.printStackTrace() }, { mView.toMainPage() }))
    }

    private fun copyVideoFile(fileName: String): String? {
        var fos: FileOutputStream? = null
        var input: InputStream? = null
        try {
            fos = application.openFileOutput(fileName, Context.MODE_PRIVATE)
            input = application.resources.openRawResource(R.raw.welcome_video)
            val buff = ByteArray(1024)
            var len: Int
            while (input.read(buff).also { len = it } != -1) {
                fos.write(buff, 0, len)
            }
        } catch (e: IOException) {
            e.printStackTrace()
            return null
        } finally {
            input?.closeQuietly()
            fos?.closeQuietly()
        }
        return application.getFileStreamPath(videoName).path
    }


}
