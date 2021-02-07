package com.gas.flutterplugin.learn

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.gas.flutterplugin.FlutterBridgeActivity
import com.gas.flutterplugin.R
import kotlinx.android.synthetic.main.activity_turn_to_flutter.*

class TurnToFlutterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_turn_to_flutter)


        //使用FlutterFragmentActivity
        page3.setOnClickListener {
            startActivity(Intent(this, FlutterBridgeActivity::class.java))
        }

        //使用FlutterActivity
        page4.setOnClickListener {
            startActivity(Intent(this,PageFlutterActivity::class.java))
        }

        //进入Flutter页面演示通过Channel跳转到Activity
        jumpByChannel.setOnClickListener {
            startActivity(Intent(this,JumpActivityFlutterWidget::class.java))
        }

        //进入嵌入了Android平台的View的Flutter页面
        insertAndroidView.setOnClickListener {
            startActivity(Intent(this,InsertAndroidViewFlutterWidget::class.java))
        }

    }
}