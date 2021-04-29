package com.gas.test.ui.fragment.login

import android.os.Bundle
import android.view.View
import com.gas.test.R
import com.lib.commonsdk.mvvm.fragment.BaseVMFragment

class LoginFragment :BaseVMFragment<LoginViewModel>(){

    override fun layoutId(): Int {
        return R.layout.fragment_login
    }

    override fun initData(savedInstanceState: Bundle?) {
        view?.findViewById<View>(R.id.btnMine1)?.setOnClickListener {

        }
    }
}