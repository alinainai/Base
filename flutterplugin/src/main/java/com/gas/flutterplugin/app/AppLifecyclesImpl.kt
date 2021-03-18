package com.gas.flutterplugin.app

import android.app.Application
import android.content.Context
import com.base.lib.base.delegate.AppLifecyclers
import com.gas.flutterplugin.idlefish.MyFlutterBoostDelegate
import com.idlefish.flutterboost.FlutterBoost
import com.idlefish.flutterboost.FlutterBoostDelegate
import io.flutter.embedding.engine.FlutterEngine
import java.util.*


class AppLifecyclesImpl : AppLifecyclers {
    override fun attachBaseContext(base: Context) {}
    override fun onCreate(application: Application) {
//        FlutterBoost.instance().setup(application, object : FlutterBoostDelegate {
//            override fun pushNativeRoute(pageName: String?, arguments: HashMap<String, String>?) {
//            }
//
//            override fun pushFlutterRoute(pageName: String?, uniqueId: String?, arguments: HashMap<String, String>?) {
//            }
//
//        }, FlutterBoost.Callback { engine: FlutterEngine -> engine.plugins })
        FlutterBoost.instance().setup(application, MyFlutterBoostDelegate(), FlutterBoost.Callback { engine: FlutterEngine -> engine.plugins })
    }
    override fun onTerminate(application: Application) {}
}