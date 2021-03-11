package com.gas.flutterplugin

import android.util.Log
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel


object MessageBridge {

    fun init(engine: FlutterEngine) {
        val channelName = "channelName_FlutterBridgeActivity"
        val androidMethod = "methodName_FlutterBridgeActivity"
        MethodChannel(
                engine.dartExecutor.binaryMessenger,
                channelName
        ).apply {
            setMethodCallHandler { methodCall, result ->
                if (methodCall.method == androidMethod) {
                    Log.e("Android", "接收到了Flutter传递的参数:${methodCall.arguments}")
                    result.success("$androidMethod ok")
                    Log.e("Android", "主动调用Flutter的methodInvokeMethodPageState方法")
                    invokeMethod("methodInvokeMethodPageState", "Android发送给Flutter的参数")
                }
            }
        }
    }

}