package com.gas.zhihu.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityOptionsCompat
import androidx.fragment.app.Fragment
import com.base.lib.base.BaseActivity
import com.base.lib.di.component.AppComponent
import com.base.lib.mvp.IPresenter
import com.gas.zhihu.R

class FragmentContainerActivity : BaseActivity<IPresenter>() {

    private var mFragment: Fragment? = null

    override fun setupActivityComponent(appComponent: AppComponent) {


    }
    override fun initView(savedInstanceState: Bundle?): Int {
        return R.layout.zhihu_activity_fragment_container
    }

    override fun initData(savedInstanceState: Bundle?) {

        val fragmentClazz = intent.getSerializableExtra("clazz") as Class<out Fragment>
        try {
            mFragment = fragmentClazz.newInstance()
            mFragment!!.arguments = intent.extras
            val beginTransaction = supportFragmentManager
                    .beginTransaction()
            beginTransaction.replace(R.id.container, mFragment!!)
                    .commitAllowingStateLoss()
        } catch (e: Exception) {
            finish()
        }


    }


    companion object{
        fun startActivity(context: Activity, fragmentClazz: Class<out Fragment?>?) {
            startActivity(context, fragmentClazz, null)
        }

        fun startActivity(context: Activity, fragmentClazz: Class<out Fragment?>?, args: Bundle?, vararg intentFlags: Int) {
            val intent = Intent(context, FragmentContainerActivity::class.java)
            intent.putExtra("clazz", fragmentClazz)
            if (args != null) {
                intent.putExtras(args)
            }
            if (intentFlags.isNotEmpty()) {
                for (flag in intentFlags) {
                    intent.addFlags(flag)
                }
            }
            context.startActivity(intent)
        }

        fun startActivityForResult(context: Activity, fragmentClazz: Class<out Fragment?>?, requestCode: Int) {
            startActivityForResult(context, fragmentClazz, requestCode, null)
        }

        fun startActivityForResult(context: Activity, fragmentClazz: Class<out Fragment?>?, requestCode: Int, args: Bundle?) {
            val intent = Intent(context, FragmentContainerActivity::class.java)
            intent.putExtra("clazz", fragmentClazz)
            if (args != null) {
                intent.putExtras(args)
            }
            context.startActivityForResult(intent, requestCode)
        }

        /**
         * 转场动画启动
         * @param activity
         * @param fragmentClazz
         * @param args
         * @param view
         */
        fun startSceneTrans(activity: Activity, fragmentClazz: Class<out Fragment?>?, args: Bundle?, view: View) {
            val intent = Intent(activity, FragmentContainerActivity::class.java)
            intent.putExtra("clazz", fragmentClazz)
            if (args != null) {
                intent.putExtras(args)
            }
            val campat = ActivityOptionsCompat.makeScaleUpAnimation(view, view.width / 2, view.height / 2, 0, 0)
            ActivityCompat.startActivity(activity, intent, campat.toBundle())
            activity.overridePendingTransition(0, 0)
        }
    }





    fun onBackClick(view: View?) {
        onBackPressed()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        if (fragment != null) {
            if (fragment is BackPressCallback) {
                if (fragment.OnBackPressed()) {
                    return
                }
            }
        }
        super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.findFragmentById(R.id.container)
        fragment?.onActivityResult(requestCode, resultCode, data)
    }

}