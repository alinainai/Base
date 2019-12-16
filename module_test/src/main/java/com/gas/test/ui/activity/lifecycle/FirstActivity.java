package com.gas.test.ui.activity.lifecycle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gas.test.R;

import timber.log.Timber;

public class FirstActivity extends AppCompatActivity {


    private static final String TAG=FirstActivity.class.getSimpleName();




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Timber.tag(TAG).e("OnCreate");
        setContentView(R.layout.test_activity_main);
        findViewById(R.id.tv_to_second).setOnClickListener(v ->
                startActivity(new Intent(FirstActivity.this,SecondActivity.class)));
    }

    @Override
    protected void onRestart() {

        super.onRestart();
        Timber.tag(TAG).e("onRestart");
    }

    @Override
    protected void onStart() {

        super.onStart();
        Timber.tag(TAG).e("onStart");
    }

    @Override
    protected void onResume() {

        super.onResume();
        Timber.tag(TAG).e("onResume");
    }

    @Override
    protected void onPause() {
        Timber.tag(TAG).e("onPause");
        super.onPause();
    }

    @Override
    protected void onStop() {
        Timber.tag(TAG).e("onStop");
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Timber.tag(TAG).e("onDestroy");
        super.onDestroy();
    }
}
