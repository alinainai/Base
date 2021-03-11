package com.gas.flutterplugin

import android.content.Context
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor

object FlutterBridge{

    fun init( context: Context){
        val flutterEngine = FlutterEngine(context)
        flutterEngine.dartExecutor.executeDartEntrypoint(
                DartExecutor.DartEntrypoint.createDefault()
        )
        MessageBridge.init(flutterEngine)
        FlutterEngineCache
                .getInstance()
                .put(FLUTTER_ENGINE_ID, flutterEngine)
    }

    fun getEngine():FlutterEngine?{
        return  FlutterEngineCache
                .getInstance()
                .get(FLUTTER_ENGINE_ID)
    }

}