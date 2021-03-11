package com.gas.flutterplugin

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import io.flutter.embedding.android.FlutterActivityLaunchConfigs
import io.flutter.embedding.android.FlutterFragmentActivity
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.plugin.common.MethodChannel
import org.json.JSONObject

/**
 * 描述:
 * 作者:liuhc
 * 创建日期：2019-09-04 on 21:07
 */
class FlutterBridgeActivity : FlutterFragmentActivity() {
    companion object{
        fun start(ctx: Context, route: String){
            FlutterEngineCache.getInstance()[FLUTTER_ENGINE_ID]?.navigationChannel?.pushRoute(route)
            withCachedEngine(FLUTTER_ENGINE_ID)
                    .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.transparent)
                    .build(ctx).also {
                        it.setClass(ctx,FlutterBridgeActivity::class.java)
                        ctx.startActivity(it) }

        }
    }
}