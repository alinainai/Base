package com.gas.flutterplugin.idlefish;

import android.content.Intent;

import com.idlefish.flutterboost.FlutterBoost;
import com.idlefish.flutterboost.FlutterBoostDelegate;
import com.idlefish.flutterboost.containers.FlutterBoostActivity;

import java.util.HashMap;

import io.flutter.embedding.android.FlutterActivityLaunchConfigs;

public class MyFlutterBoostDelegate implements FlutterBoostDelegate {

    @Override
    public void pushNativeRoute(String pageName, HashMap<String, String> arguments) {
        Intent intent = new Intent(FlutterBoost.instance().currentActivity(), NativePageActivity.class);
        FlutterBoost.instance().currentActivity().startActivity(intent);
    }

    @Override
    public void pushFlutterRoute(String pageName, String uniqueId, HashMap<String, String> arguments) {
        Intent intent = new FlutterBoostActivity.CachedEngineIntentBuilder(FlutterBoostActivity.class, FlutterBoost.ENGINE_ID)
                .backgroundMode(FlutterActivityLaunchConfigs.BackgroundMode.opaque)
                .destroyEngineWithActivity(false)
                .uniqueId(uniqueId)
                .url(pageName)
                .urlParams(arguments)
                .build(FlutterBoost.instance().currentActivity());
        FlutterBoost.instance().currentActivity().startActivity(intent);
    }
}