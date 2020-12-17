package com.gas.app.utils


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gas.app.R
import com.lib.commonsdk.extension.app.debug
import kotlinx.android.synthetic.main.activity_remote.*


/**
 * ================================================
 * desc:首页
 *
 * created by author ljx
 * date  2020/7/22
 * email 569932357@qq.com
 *
 * ================================================
 */
class RemoteActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_remote)
        btnRemote.setOnClickListener {
           debug("process${getProcessName(this.application, android.os.Process.myPid())}")
        }
    }


}
