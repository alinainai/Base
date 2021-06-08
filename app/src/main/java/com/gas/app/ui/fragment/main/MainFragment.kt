package com.gas.app.ui.fragment.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.launcher.ARouter
import com.base.componentservice.gank.service.GankInfoService
import com.base.componentservice.test.service.TestInfoService
import com.base.componentservice.zhihu.service.ZhihuInfoService
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.lib.util.Preconditions
import com.gas.app.R
import com.gas.app.ui.fragment.main.di.DaggerMainComponent
import com.gas.app.ui.fragment.main.mvp.MainContract
import com.gas.app.ui.fragment.main.mvp.MainPresenter
import com.gas.flutterplugin.idlefish.MainActivity
import com.gas.flutterplugin.learn.TurnToFlutterActivity
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.extension.app.debug
import com.lib.commonsdk.extension.app.navigation
import dalvik.system.BaseDexClassLoader
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Predicate
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_main.*
import java.lang.Exception
import java.util.*
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


class MainFragment : BaseFragment<MainPresenter?>(), MainContract.View {


    @JvmField
    @Autowired(name = RouterHub.ZHIHU_SERVICE_ZHIHUINFOSERVICE)
    var mZhihuInfoService: ZhihuInfoService? = null

    @JvmField
    @Autowired(name = RouterHub.GANK_SERVICE_GANKINFOSERVICE)
    var mGankInfoService: GankInfoService? = null

    @JvmField
    @Autowired(name = RouterHub.TEST_SERVICE_TESTINFOSERVICE)
    var mTestInfoService: TestInfoService? = null

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMainComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        ARouter.getInstance().inject(this)
        loadModuleInfo()

        btnModule1.setOnClickListener {
            navigation(activity, RouterHub.ZHIHU_HOMEACTIVITY)
        }
        btnModule2.setOnClickListener {
            navigation(activity, RouterHub.GANK_MAINACTIVITY)
        }
        btnModule3.setOnClickListener {
            navigation(activity, RouterHub.TEST_HOMEACTIVITY)
        }
        btnPlugin1.setOnClickListener {
//            startActivity(Intent(context, TurnToFlutterActivity::class.java))
            startActivity(Intent(context, MainActivity::class.java))
        }
        btnPlugin2.setOnClickListener {
            loopSequence()
            debug("start")
        }
        btnPlugin3.setOnClickListener {
            compositeDisposable.clear()
            debug("stop")
//            System.gc()
        }
    }

    private val compositeDisposable = CompositeDisposable()

    // 按照顺序loop，意味着第一次结果请求完成后，再考虑下次请求
    private fun loopSequence() {
        val disposable = getDataFromServer()
                .delay(1, TimeUnit.SECONDS, true) // 设置delayError为true，表示出现错误的时候也需要延迟5s进行通知，达到无论是请求正常还是请求失败，都是5s后重新订阅，即重新请求。
                .subscribeOn(Schedulers.io())
                .repeat() // repeat保证请求成功后能够重新订阅。
                .retry(Predicate<Throwable> {
                    when (it) {
                        is RuntimeException -> {
                            debug(it.message ?: "error null")
                            false
                        }
                        is TimeoutException -> {
                            debug(it.message ?: "error null")
                            true
                        }
                        else -> true
                    }
                }) // retry保证请求失败后能重新订阅
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    debug("onNext", it.toString())
                }, {
                    debug(it.message ?: "error null")
                })
        compositeDisposable.add(disposable)
    }

    private fun getDataFromServer(): Observable<Int> {
        return Observable.create(ObservableOnSubscribe<Int> { emitter ->
            if (emitter.isDisposed) {
                return@ObservableOnSubscribe
            }
            val randomSleep = Random().nextInt(5)
            try {
//                Thread.sleep((randomSleep * 1000).toLong())
                Thread.sleep(1000)
            } catch (e: Exception) {
            }
            if (emitter.isDisposed) {
                return@ObservableOnSubscribe
            }
            when {
                randomSleep % 3 == 0 -> {
                    emitter.onError(RuntimeException("Runtime int=${randomSleep}"))
                }
                randomSleep % 2 == 0 -> {
                    emitter.onError(TimeoutException("timeout int=${randomSleep}"))
                }
                else -> {
                    emitter.onNext(randomSleep)
                    emitter.onComplete()
                }
            }

        })
    }


    override fun setData(data: Any?) {}
    override fun showMessage(message: String) {
        Preconditions.checkNotNull(message)
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        Preconditions.checkNotNull(intent)
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {}
    private fun loadModuleInfo() {
        //当非集成调试阶段, 宿主 App 由于没有依赖其他组件, 所以使用不了对应组件提供的服务
        mZhihuInfoService?.let {
            btnModule2.text = it.info.name
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }


}