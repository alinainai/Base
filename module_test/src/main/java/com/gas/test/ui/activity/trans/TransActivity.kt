package com.gas.test.ui.activity.trans

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.gas.test.R
import com.gas.test.ui.fragment.ratioview.RatioViewFragment
import com.gas.test.ui.fragment.retrofit.RetrofitFragment
import com.gas.test.ui.fragment.timedown.TimeDownFragment
import kotlinx.android.synthetic.main.test_activity_trans.*

class TransActivity : AppCompatActivity() {

    companion object{
        const val CLOUD_RECORD_TAG = "cloud_fragment"
        const val BASE_JS_WEBVIEW_TAG = "new_user_web_fragment"
        const val RECORD_VIDEO_PLAY_TAG = "record_video"
    }


    private lateinit var mFragmentManager: FragmentManager

    private var mCloudParentFragment: RetrofitFragment? = null
    private var mRecordVideoPlayFragment: RatioViewFragment? = null
    private var mJsWebViewFragment: TimeDownFragment? = null

    private  var mCurrentFragment:Fragment?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mFragmentManager=supportFragmentManager
        setContentView(R.layout.test_activity_trans)
        addOrAttachFragment1.setOnClickListener {
            if(mCurrentFragment!=mCloudParentFragment)
            switchFragment(CLOUD_RECORD_TAG)
        }
        addOrAttachFragment2.setOnClickListener {
            if(mCurrentFragment!=mJsWebViewFragment)
            switchFragment(BASE_JS_WEBVIEW_TAG)
        }
        addOrAttachFragment3.setOnClickListener {
            if(mCurrentFragment!=mRecordVideoPlayFragment)
            switchFragment(RECORD_VIDEO_PLAY_TAG)
        }
        switchFragment(CLOUD_RECORD_TAG)
    }

    private fun switchFragment(tag:String){
        changeFragment(tag)
    }

    override fun onPause() {
        overridePendingTransition(0, 0)
        super.onPause()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    @SuppressLint("LogNotTimber")
    @Synchronized
    private fun changeFragment(tag: String) {
        val transaction: FragmentTransaction = mFragmentManager.beginTransaction()
        //Fragment切换时的动画
        transaction.setTransition(FragmentTransaction.TRANSIT_NONE)
        var tofragment: Fragment? = mFragmentManager.findFragmentByTag(tag)
        if (tofragment == null) {
            tofragment = getFragmentFromFactory(tag)
        }
        var fromfragment: Fragment? = null
        if (mCurrentFragment != null) {
            fromfragment = mCurrentFragment
        }
        if (fromfragment != null) {
            transaction.detach(fromfragment)
        }
        if (tofragment != null) {
            if (tofragment.isDetached) {

                mFragmentManager.fragments.forEach {
                    Log.d("TransActivity","tofragment.isDetached/mFragmentManager.fragments=="+it.tag)
                }

                Log.e("TransActivity","tofragment.isDetached/tofragment("+tag+").isAdd="+tofragment.isAdded)
                transaction.attach(tofragment).commitAllowingStateLoss()
            } else {

                mFragmentManager.fragments.forEach {
                    Log.d("TransActivity","tofragment.isNotDetached/mFragmentManager.fragments"+it.tag)
                }

                Log.e("TransActivity","tofragment.isNotDetached/tofragment("+tag+")..isAdd="+tofragment.isAdded)
                if (null == mFragmentManager.findFragmentByTag(tag)) {
                    Log.e("TransActivity","notDetached: findFragmentByTag"+tag+"==null")
                    transaction.add(R.id.container, tofragment, tag)
                } else {
                    Log.i("TransActivity","notDetached: attach")
                    transaction.attach(tofragment)
                }
                transaction.commitAllowingStateLoss()
            }
            mCurrentFragment = tofragment
        }
    }
    private fun getFragmentFromFactory(tag: String): Fragment? {
        when (tag) {
            CLOUD_RECORD_TAG -> {
                if (mCloudParentFragment == null) {
                    mCloudParentFragment = RetrofitFragment()
                }
                return mCloudParentFragment
            }
           BASE_JS_WEBVIEW_TAG -> {
                if (mJsWebViewFragment == null) {
                    mJsWebViewFragment = TimeDownFragment()
                }
                return mJsWebViewFragment
            }
          RECORD_VIDEO_PLAY_TAG -> {
                if (mRecordVideoPlayFragment == null) {
                    mRecordVideoPlayFragment = RatioViewFragment()
                }
                return mRecordVideoPlayFragment
            }
        }
        return null
    }
}