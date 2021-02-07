package com.gas.flutterplugin

import android.content.Context
import com.lib.commonsdk.kotlin.utils.SingletonHolder
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

class FlutterMain private constructor(val context: Context){


    companion object : SingletonHolder<FlutterMain, Context>(::FlutterMain)

    private val flutterEngine by lazy {
        FlutterEngine(context)
    }

    fun init(){
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        FlutterEngineCache
                .getInstance()
                .put("engine_id", flutterEngine)
    }

    fun flutterEngine():FlutterEngine{
        return flutterEngine
    }

    fun destory(){
        FlutterEngineCache
                .getInstance()
                .get("engine_id")
                ?.destroy()
    }

}