//package ai.ling.luka.app.widget
//
//
//import android.content.Context
//import android.content.Intent
//import android.graphics.drawable.GradientDrawable
//import android.net.Uri
//import android.provider.Settings
//import android.view.Gravity
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import org.jetbrains.anko.*
//
///**
// * 网络失败占位
// */
//class NetworkErrorView(ctx: Context) : RelativeLayout(ctx) {
//
//    private lateinit var imageView: ImageView
//    var tryAgainClick: () -> Unit = {}
//    var networkPermissionClick: () -> Unit = {}
//
//    init {
//        relativeLayout {
//            lparams {
//                width = matchParent
//                height = matchParent
//            }
////            backgroundColor = Colors.white
//            verticalLayout {
//                gravity = Gravity.CENTER_HORIZONTAL
//                imageView = imageView {
//                }
//                textView(str(R.string.ai_ling_luka_network_tip_tip_network_failed)) {
//                    textSize = 15f
//                    textColor = Colors.black
//                }
//                text(str(R.string.ai_ling_luka_network_tip_tip_confirm)) {
//                    textColor = Colors.color("#9B9B9B")
//                    textSize = 12f
//                }.lparams {
//                    topMargin = dip(10)
//                }
//                linearLayout {
//                    text(str(R.string.ai_ling_luka_network_button_try_again)) {
//                        textSize = 14f
//                        textColor = Colors.white
//                        backgroundDrawable = GradientDrawable().apply {
//                            colors = intArrayOf(Colors.color("#FFC107"), Colors.color("#FFC107"))
//                            cornerRadius = dip(27).toFloat()
//                        }
//                        horizontalPadding = dip(35)
//                        verticalPadding = dip(10)
//                        onClick {
//                            tryAgainClick()
//                        }
//                    }
//                    text(str(R.string.ai_ling_luka_network_button_network_permission)) {
//                        textSize = 14f
//                        textColor = Colors.color("#8B572A")
//                        horizontalPadding = dip(21)
//                        verticalPadding = dip(10)
//                        onClick {
//                            toPermissionSetting()
//                            networkPermissionClick()
//                        }
//                        backgroundDrawable = GradientDrawable().apply {
//                            setStroke(1, Colors.color("#8B572A"))
//                            cornerRadius = dip(27).toFloat()
//                        }
//                    }.lparams {
//                        leftMargin = dip(15)
//                    }
//                }.lparams {
//                    topMargin = dip(40)
//                }
//            }.lparams {
//                centerVertically()
//                width = matchParent
//                height = wrapContent
//            }
//        }
//    }
//
//    private fun toPermissionSetting() {
//        Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
//            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            data = Uri.fromParts("package", context.packageName, null)
//        }.let {
//            context.startActivity(it)
//        }
//    }
//}