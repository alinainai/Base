package com.gas.test.learn.lesson002_lauchmode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug

class TopActivity : AppCompatActivity() {


    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        debug("lauchmode","TopActivity_onNewIntent")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lauchmode_standard)
        findViewById<TextView>(R.id.textView2).text="SingleTop启动模式"
        findViewById<View>(R.id.button1).setOnClickListener {
            startActivity(Intent(this,AStandardActivity::class.java))
        }
        findViewById<View>(R.id.button2).setOnClickListener {
            startActivity(Intent(this,TopActivity::class.java))
        }
        findViewById<View>(R.id.button6).setOnClickListener {
            startActivity(Intent(this,TaskActivity::class.java))
        }
    }
    override fun onRestart() {
        super.onRestart()
        debug("lauchmode","TopActivity_onRestart")
    }

    override fun onStart() {
        super.onStart()
        debug("lauchmode","TopActivity_onStart")
    }

    override fun onResume() {
        super.onResume()
        debug("lauchmode","TopActivity_onResume")
    }


    override fun onPause() {
        super.onPause()
        debug("lauchmode","TopActivity_onPause")
    }

    override fun onStop() {
        debug("lauchmode","TopActivity_onStop")
        super.onStop()
    }

    override fun onDestroy() {
        debug("lauchmode","TopActivity_onDestroy")
        super.onDestroy()
    }
}