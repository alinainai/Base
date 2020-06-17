package com.gas.test.ui.activity.trans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.gas.test.R
import com.lib.commonsdk.constants.RouterHub

@Route(path = RouterHub.TEST_HOMEACTIVITY)
class TransActivity : AppCompatActivity() {

    companion object {
        const val CLOUD_RECORD_TAG = "cloud_fragment"
        const val BASE_JS_WEBVIEW_TAG = "new_user_web_fragment"
        const val RECORD_VIDEO_PLAY_TAG = "record_video"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_trans)
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

}