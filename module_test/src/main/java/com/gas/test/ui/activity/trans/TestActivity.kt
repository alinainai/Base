package com.gas.test.ui.activity.trans

import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.baseui.ui.base.FragmentContainerActivity
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.utils.fragment.customview.CustomViewFragment
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.kotlin.extension.app.*
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_test.*
import okhttp3.*
import java.io.IOException


@Route(path = RouterHub.TEST_HOMEACTIVITY)
class TestActivity : BaseActivity<IPresenter>() {

    private val disposes = CompositeDisposable()
    private val vibrator by lazy{
        getSystemService(VIBRATOR_SERVICE) as Vibrator
    }
    override fun setupActivityComponent(appComponent: AppComponent) {
    }

    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.activity_test
    }

    val map = mutableMapOf<String, String>().apply {
        put("11", "title1")
        put("12", "title2")
        put("13", "title3")
        put("14", "title3")
        put("15", "title3")
    }

    val list = mutableListOf("11", "12", "13", "15")


    override fun initData(savedInstanceState: Bundle?) {


        iconText.startIcon(R.drawable.public_map_baidu)
        iconText.endIcon(R.drawable.public_map_baidu)
        iconText.topIcon(R.drawable.public_map_baidu)
        iconText.bottomIcon(R.drawable.public_map_baidu)


        btnModule1.setOnClickListener {

            val pattern = longArrayOf(500, 1000, 500, 1000)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val vibe = VibrationEffect.createWaveform(pattern, 0)
                vibrator.vibrate(vibe)
            } else {
                vibrator.vibrate(pattern, 0)
            }

//            FragmentContainerActivity.startActivity(this, CoroutinesFragment::class.java)

//            FragmentContainerActivity.startActivity(this, AsyncListFragment::class.java)

//            val iterator =map.entries.iterator()
//            while (iterator.hasNext()) {
//                val item = iterator.next()
//                if (!list.contains(item.key)) {
//                    iterator.remove()
//                }
//            }
//            debug(map)

//            map.keys.forEach {key->
//                if (!list.contains(key)) {
//                    map.remove(key)
//                }
//            }
//            debug(map)

           val client= OkHttpClient()
            client.newCall(Request.Builder()
                    .url("http://api.github.com/users/rengwuxian/repo")
                    .build())
                    .enqueue(object : Callback {
                        override fun onFailure(call: Call, e: IOException) {
                        }

                        override fun onResponse(call: Call, response: Response) {
                        }

                    })


        }
        btnModule2.setOnClickListener {
            vibrator.cancel()
//            FragmentContainerActivity.startActivity(this, CustomViewFragment::class.java)

//            val disposable = Observable.interval(0, 1, TimeUnit.SECONDS)
//                    .take(5)
//                    .subscribe { print("$it  ") }
//            if (!disposable.isDisposed) disposable.dispose()

            val new = System.currentTimeMillis()

//            Single.create(SingleOnSubscribe<Int> { emitter -> emitter.onSuccess(1) })
//                    .subscribe({ t -> debug("onSuccess+$t") }, { e -> debug("onError+$e") })


//            FragmentContainerActivity.startActivity(this, CustomViewFragment::class.java)

//            val list1 = mutableListOf<String>("11","12","13","14")
//            val list2 = mutableListOf<String>("11","12","13","14")
//            debug("btnModule2=${list1 == list2}")

        }
        btnModule3.setOnClickListener {

            shadowImg.visible()
            shadowImg.hideQuickly()

//            debug(Math.sin(Math.toRadians(60.0)))
//            FragmentContainerActivity.startActivity(this, ScopeStorageFragment::class.java)

        }
        btnPlugin1.setOnClickListener {

//            Single.create<String> { emitter ->
//                emitter.onSuccess("111")
//            }.subscribeOn(Schedulers.newThread())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe({}, {}).also {
//                        disposes.add(it)
//                    }

            Single.create<String> { emitter ->
                debug("subscribeOn=${Thread.currentThread().name}")
                emitter.onSuccess("111")
            }.subscribeOn(Schedulers.io())
                    .observeOn(Schedulers.newThread())
                    .doOnSuccess {
                        debug("doOnSuccess=${Thread.currentThread().name}")
                    }
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        debug("observeOn=${Thread.currentThread().name}")
                    }, {})
                    .also {
                        disposes.add(it)
                    }

//            Completable.create { e -> e.onComplete() }
//                    .subscribe( { debug("onComplete") },{ e -> debug("onError+$e") })

//            FragmentContainerActivity.startActivity(this, CoordinateFragment::class.java)
//            val list1 = mutableListOf<String>("11","12","13","14")
//            val list2 = mutableListOf<String>("11","13","12","14")
//            debug("btnPlugin1=${list1 == list2}")
        }
        btnPlugin2.setOnClickListener {
//            Maybe.create(MaybeOnSubscribe<Int> {e->
////                e.onSuccess(1)
//                e.onComplete()
//            }).subscribe({ t -> debug("onSuccess+$t")},{ e -> debug("onError+$e") },{debug("onComplete")})
//            val list1 = mutableListOf<String>("11", "12", "13")
//            val list2 = mutableListOf<String>("11", "13", "12", "14")
//            debug("btnPlugin2=${list1 == list2}")
        }
        btnPlugin3.setOnClickListener {
            val list1 = mutableListOf<String>()
            val list2 = mutableListOf<String>()
            debug("btnPlugin3=${list1 == list2}")
        }
    }
}