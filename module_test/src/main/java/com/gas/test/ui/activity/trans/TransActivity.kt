package com.gas.test.ui.activity.trans

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.base.baseui.ui.base.FragmentContainerActivity
import com.gas.test.R
import com.gas.test.utils.fragment.asynclist.AsyncListFragment
import com.gas.test.utils.fragment.customview.CustomViewFragment
import com.gas.test.utils.fragment.lazyload.LazyLoadParentFragment
import com.lib.commonsdk.constants.RouterHub
import com.lib.commonsdk.kotlin.extension.app.debug
import kotlinx.android.synthetic.main.test_activity_trans.*


class TransActivity : AppCompatActivity() {

    companion object {
        const val CLOUD_RECORD_TAG = "cloud_fragment"
        const val BASE_JS_WEBVIEW_TAG = "new_user_web_fragment"
        const val RECORD_VIDEO_PLAY_TAG = "record_video"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_trans)
        addOrAttachFragment1.setOnClickListener {
//            FragmentContainerActivity.startActivity(this, AsyncListFragment::class.java)
            FragmentContainerActivity.startActivity(this, CustomViewFragment::class.java)
        }
        addOrAttachFragment2.setOnClickListener {
            debug("addOrAttachFragment2 click")
//            FragmentContainerActivity.startActivity(this, AsyncListFragment::class.java)
            FragmentContainerActivity.startActivity(this, LazyLoadParentFragment::class.java)
        }
        addOrAttachFragment1.text=getString(R.string.money_unit_special,3.29)
        addOrAttachFragment2.text=getString(R.string.money_unit_special_dollar,3.29)
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

}