package trunk.doi.base.ui.activity.extra

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import trunk.doi.base.R

/**
 * Created by ly on 2016/6/22.
 * 设置
 */
class SettingActivity : AppCompatActivity() {


    //  kotlin使用var，val关键字定义变量，如果在定义变量时直接赋值，则可以不用指定变量类型，否则需要在变量名后使用“：”来指定类型。
    // 在类型后加“？”表示该变量可空。如果变量可空，那在后续使用变量时，就必须先判断是否为空。
//    var a:Int = null  //错误，类型后没？，即不可空类型，自然不能等于null
//    var a:Int? = null  //正确
//
//    val canNull: Int ? = 0
//    canNull.toFloat  //错误，可空变量调用时要判断空
//    canNull?.toFloat   //正确，(如果是null，什么都不做)
//    canNull!!.Float   //正确，(如果null，报空指针)
//    val canNotNull: Int  = 0
//    canNotNull.toFloat  //正确

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

    }

}
