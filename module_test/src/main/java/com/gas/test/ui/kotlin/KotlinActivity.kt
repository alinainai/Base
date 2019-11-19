package com.gas.test.ui.kotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gas.test.R
import timber.log.Timber

//继承通过":"实现 AppCompatActivity要添加(),实现接口 直接加 ","
class KotlinActivity : AppCompatActivity(), View.OnClickListener {

    private val username = "username"
    private val password = "password"

    //? 表示可空类型的变量 可以赋值为 null 也可赋值为非空
    private var user: User? = null
    private var user2: User? = User()
//    private var user3:User=null //这里会报错 不加？表示不可空类型不能辅助为null

    private var kotlinEtUsername: EditText? = null
    //lateinit 就是一个提示的作用
    //lateinit 不能修饰可空类型
    //lateinit 不能在初始化时赋值
    private lateinit var kotlinEtPassword: EditText
    private lateinit var kotlinCv: CodeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.test_activity_kotlin)

        kotlinEtUsername = findViewById(R.id.kotlin_et_username)
        kotlinEtPassword = findViewById(R.id.kotlin_et_password)
        //!! 强制调用 不管为不为空
        // ?. 不为空在调用 为空不调用 安全调用符 kotlin的一种语法糖
        kotlinEtUsername?.setText("李佳星")
        kotlinEtPassword.setText("ljx19910518")

        //平台类型
        val textView = findViewById<TextView>(R.id.kotlin_tv)
        textView.setOnClickListener(this)

        textView.text="${'$'}9.99"
        kotlinCv = findViewById(R.id.kotlin_cv)
        kotlinCv.setOnClickListener(this)

        var kotlinUser = KotlinUser()
        kotlinUser.password = "ljx"
        kotlinUser.username = "李佳星"
        Timber.tag("TAG").e("name= %s,pwd=%s", kotlinUser.username, kotlinUser.password)

    }

    override fun onClick(v: View?) {
        //强转
//        val tv = v as TextView
//        if (v is TextView) {
//            // is 里面不用强转
//            v.text = "我被点击了"
//            login()
//        }else{
//            kotlinCv.updateCode()
//        }
        when(v?.id){

            R.id.kotlin_cv -> kotlinCv.updateCode()
            R.id.kotlin_tv ->  login()

        }

    }

    private fun login() {
        Timber.tag("TAG").e(kotlinEtPassword.text.toString())
        startActivity(Intent(this, InfoActivity::class.java))
    }

}
