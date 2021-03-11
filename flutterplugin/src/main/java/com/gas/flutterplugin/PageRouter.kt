package com.gas.flutterplugin

import android.content.Context
import android.os.Bundle
import org.json.JSONObject

object PageRouter {

    fun startFlutterMainDart(ctx: Context, bundle: Bundle) {
        val channelName = "channelName_FlutterBridgeActivity"
        val androidMethod = "methodName_FlutterBridgeActivity"
        val jsonObject = JSONObject()
        jsonObject.put("path", "InvokeMethodPage")
        jsonObject.put("title", "FlutterBridgeActivity")
        jsonObject.put("channelName", channelName)
        jsonObject.put("androidMethod", androidMethod)
        FlutterBridgeActivity.start(ctx,jsonObject.toString())
    }

}