package com.gas.test.learn.lesson001_lifecycle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gas.test.R
import com.lib.commonsdk.extension.app.debug

class MainActivity : LifecycleActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_lifecycle)
        findViewById<View>(R.id.button6).setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }
        findViewById<View>(R.id.button7).setOnClickListener {
            startActivity(Intent(this, TransActivity::class.java))
        }
    }
}