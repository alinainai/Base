package com.gas.test.ui.activity.trans

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/*
* 实体类
* */

data class LoginBean(var code: Int, var message: String)

/*处理登录逻辑*/
class LoginModel {

    /*模拟请求接口返回的数据*/
    fun login(): LoginBean {
        return LoginBean(1,"登录成功")
    }
}
/*
* LoginViewModel
*
*   管理登录的数据以及处理登录相关逻辑
* */
class LoginVm : ViewModel() {

    private var loginModel = LoginModel()

    /*LiveData，使该数据可以被感知*/
    var loginBean = MutableLiveData<LoginBean>()

    var account: String = ""
    var pwd: String = ""

    fun doLogin() {
        Handler().postDelayed(object : Runnable {
            override fun run() {

                /*通知Activity刷新数据*/
                loginBean.value = loginModel.login()

            }
        }, 3000)
    }

}
