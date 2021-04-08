package com.gas.test.learn.lesson001_lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug

class SecondActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug("lifecycle","SecondActivity_onCreate")
        setContentView(R.layout.activity_second_lifecycle)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("lifecycle","SecondActivity_onNewIntent")
    }

    override fun onRestart() {
        super.onRestart()
        debug("lifecycle","SecondActivity_onRestart")
    }

    override fun onStart() {
        super.onStart()
        debug("lifecycle","SecondActivity_onStart")
    }

    override fun onResume() {
        super.onResume()
        debug("lifecycle","SecondActivity_onResume")
    }

    override fun onPause() {
        super.onPause()
        debug("lifecycle","SecondActivity_onPause")
    }

    override fun onStop() {
        debug("lifecycle","SecondActivity_onStop")
        super.onStop()
    }

    override fun onDestroy() {
        debug("lifecycle","SecondActivity_onDestroy")
        super.onDestroy()
    }

}