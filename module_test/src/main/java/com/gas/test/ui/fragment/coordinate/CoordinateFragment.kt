package com.gas.test.ui.fragment.coordinate

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
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.test.R


class CoordinateFragment : BaseFragment<IPresenter>() {


    companion object {
        const val PICK_FILE = 1
    }

    override fun setupFragmentComponent(appComponent: AppComponent) {
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_coordinate_layout, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val permissionsToRequire = ArrayList<String>()


    }

    override fun setData(data: Any?) {
    }



}