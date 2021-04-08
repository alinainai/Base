package com.gas.test.learn.lesson001_lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug

class FirstActivity : AppCompatActivity() {


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("lifecycle","FirstActivity_onNewIntent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_lifecycle)
        findViewById<View>(R.id.button6).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
    }
    override fun onRestart() {
        super.onRestart()
        debug("lifecycle","FirstActivity_onRestart")
    }

    override fun onStart() {
        super.onStart()
        debug("lifecycle","FirstActivity_onStart")
    }

    override fun onResume() {
        super.onResume()
        debug("lifecycle","FirstActivity_onResume")
    }


    override fun onPause() {
        super.onPause()
        debug("lifecycle","FirstActivity_onPause")
    }

    override fun onStop() {
        debug("lifecycle","FirstActivity_onStop")
        super.onStop()
    }

    override fun onDestroy() {
        debug("lifecycle","FirstActivity_onDestroy")
        super.onDestroy()
    }
}