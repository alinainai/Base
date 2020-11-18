package com.gas.test.ui.fragment.coroutines

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R
import com.gas.test.ui.activity.album.BrowseAlbumActivity
import com.lib.commonsdk.kotlin.extension.app.debug
import kotlinx.android.synthetic.main.fragment_coroutines_layout.*
import kotlinx.android.synthetic.main.fragment_storage_scope.*
import kotlinx.coroutines.*
import org.jetbrains.anko.runOnUiThread
import java.io.BufferedInputStream
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

class CoroutinesFragment : BaseFragment<IPresenter>() {

    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_coroutines_layout, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        button.setOnClickListener {
            MainScope().launch {
                val startTime = System.currentTimeMillis()
                debug("tag1：" + Thread.currentThread().name)
                val text1 = getText1()
                val text2 = getText2()
                debug(text1 + text2)
                debug("耗时：" + (System.currentTimeMillis() - startTime))
            }
        }
        button2.setOnClickListener {  }
        button3.setOnClickListener {  }
        button4.setOnClickListener {  }
        button5.setOnClickListener {  }

    }

    private suspend fun getText1():String {
       return withContext(Dispatchers.IO){
           delay(1000)
            "hello"
        }

    }
    private suspend fun getText2():String {
       return withContext(Dispatchers.IO){
           delay(1000)
            "world"
        }
    }

    override fun setData(data: Any?) {
    }



}