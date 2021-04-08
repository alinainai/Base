package com.gas.test.learn.lesson002_lauchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug

class AStandardActivity : AppCompatActivity() {


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("lauchmode","AStandardActivity_onNewIntent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lauchmode_standard)
        findViewById<View>(R.id.button6).setOnClickListener {
        }
    }
    override fun onRestart() {
        super.onRestart()
        debug("lauchmode","AStandardActivity_onRestart")
    }

    override fun onStart() {
        super.onStart()
        debug("lauchmode","AStandardActivity_onStart")
    }

    override fun onResume() {
        super.onResume()
        debug("lauchmode","AStandardActivity_onResume")
    }


    override fun onPause() {
        super.onPause()
        debug("lauchmode","AStandardActivity_onPause")
    }

    override fun onStop() {
        debug("lauchmode","AStandardActivity_onStop")
        super.onStop()
    }

    override fun onDestroy() {
        debug("lauchmode","AStandardActivity_onDestroy")
        super.onDestroy()
    }
}