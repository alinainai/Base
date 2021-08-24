package com.gas.app.test.fragment.okhttplearn

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.BasePresenter
import com.base.lib.mvp.IModel
import com.base.lib.mvp.IView
import com.gas.app.R
import okhttp3.*
import java.util.concurrent.TimeUnit


class OkhttpLearnFragment : BaseFragment<BasePresenter<IModel, IView>>() {
    override fun setupFragmentComponent(appComponent: AppComponent) {

    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_test, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {
        view?.findViewById<View>(R.id.btnModule1)?.setOnClickListener {
            loadData()
        }
    }

    override fun setData(data: Any?) {


    }

    companion object {
        @JvmStatic
        fun newInstance(): OkhttpLearnFragment {
            return OkhttpLearnFragment()
        }
    }

    private fun loadData() {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)

        builder.addInterceptor { chain ->
            Log.d(TAG, "Interceptor url:" + chain.request().url.toString())
            chain.proceed(chain.request())
        }

        builder.addNetworkInterceptor { chain ->
            Log.d(TAG, "NetworkInterceptor url:" + chain.request().url.toString())
            chain.proceed(chain.request())
        }

        val client = builder.build()

        val request: Request = Request.Builder()
            .url("https://www.baidu.com")
            .build()

        val call: Call = client.newCall(request)

        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: java.io.IOException) {
                Log.d(TAG, "onFailure: " + e.message);
            }

            override fun onResponse(call: Call, response: Response) {
                Log.d(TAG, "response:" + response.body?.string());
            }
        })
    }


}