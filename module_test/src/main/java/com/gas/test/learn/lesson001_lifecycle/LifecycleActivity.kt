package com.gas.test.learn.lesson001_lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug

open class LifecycleActivity : AppCompatActivity() {


   private val tag = this.javaClass.simpleName
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        debug("lifecycle","${tag}_onCreate")
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("lifecycle","${tag}_onNewIntent")
    }
    override fun onRestart() {
        super.onRestart()
        debug("lifecycle","${tag}_onRestart")
    }

    override fun onStart() {
        super.onStart()
        debug("lifecycle","${tag}_onStart")
    }

    override fun onResume() {
        super.onResume()
        debug("lifecycle","${tag}_onResume")
    }


    override fun onPause() {
        super.onPause()
        debug("lifecycle","${tag}_onPause")
    }

    override fun onStop() {
        debug("lifecycle","${tag}_onStop")
        super.onStop()
    }

    override fun onDestroy() {
        debug("lifecycle","${tag}_onDestroy")
        super.onDestroy()
    }
}