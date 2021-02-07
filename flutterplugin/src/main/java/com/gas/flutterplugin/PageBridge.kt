package com.gas.flutterplugin

import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel

class PageBridge {

    companion object {
        val ins = PageBridge()
    }

    lateinit var methodChannel: MethodChannel

    fun init(flutterEngine: FlutterEngine) {
        methodChannel = MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "com.qihoo.camera/page")
        methodChannel.setMethodCallHandler { call, result ->
            when (call.method) {
                "finishActivity" -> {
//                    FlutterBridgeActivity.finish();
                    result.success(0)
                }
                else -> result.notImplemented()
            }
        }
    }


}