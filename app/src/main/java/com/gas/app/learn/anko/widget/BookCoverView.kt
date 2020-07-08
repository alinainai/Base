//package ai.ling.luka.app.widget
//
//import ai.ling.luka.app.R
//import ai.ling.luka.app.constant.Colors
//import ai.ling.luka.app.extension.loadImageWithRoundCorners
//import ai.ling.luka.app.view.ShadowLayout
//import ai.ling.skel.utils.DimenUtils
//import android.content.Context
//import android.view.Gravity
//import android.view.View
//import android.widget.ImageView
//import android.widget.RelativeLayout
//import com.bumptech.glide.load.resource.drawable.GlideDrawable
//import com.bumptech.glide.request.animation.GlideAnimation
//import com.bumptech.glide.request.target.SimpleTarget
//import com.bumptech.glide.request.target.Target
//import jp.wasabeef.glide.transformations.RoundedCornersTransformation
//import org.jetbrains.anko.backgroundResource
//import org.jetbrains.anko.centerInParent
//import org.jetbrains.anko.custom.customView
//import org.jetbrains.anko.dip
//import org.jetbrains.anko.displayMetrics
//import org.jetbrains.anko.imageResource
//import org.jetbrains.anko.imageView
//import org.jetbrains.anko.linearLayout
//import org.jetbrains.anko.relativeLayout
//import org.jetbrains.anko.sameBottom
//import org.jetbrains.anko.sameTop
//import org.jetbrains.anko.wrapContent
//
//class BookCoverView(ctx: Context) : RelativeLayout(ctx) {
//
//    lateinit var imgIcon: ImageView
//
//    var imgUrl: String = ""
//        set(value) {
//            field = value
//            imgIcon.loadImageWithRoundCorners(imgUrl, dip(4), RoundedCornersTransformation.CornerType.RIGHT, imageTarget)
//        }
//    lateinit var imageTarget: Target<GlideDrawable>
//
//    init {
//        relativeLayout {
//            val picWith = (ctx.displayMetrics.widthPixels - DimenUtils.dp2px(context, 26f)) / 3
//            customView<ShadowLayout> {
//                shadowLeft = DimenUtils.dp2px(context, 6f).toFloat()
//                shadowRight = DimenUtils.dp2px(context, 6f).toFloat()
//                shadowTop = DimenUtils.dp2px(context, 6f).toFloat()
//                shadowBottom = DimenUtils.dp2px(context, 6f).toFloat()
//                blur = DimenUtils.dp2px(context, 6f).toFloat()
//                color = Colors.color("#23000000")
//                linearLayout {
//                    lparams(width = picWith, height = (picWith - dip(20)) / 4 * 5 - dip(6))
//                }
//            }
//            linearLayout {
//                id = View.generateViewId()
//                relativeLayout {
//                    imgIcon = imageView {
//                        id = View.generateViewId()
//                        scaleType = ImageView.ScaleType.CENTER_CROP
//                        backgroundResource = R.drawable.icon_default_book_cover
//                    }.lparams(
//                        width = picWith - dip(18),
//                        height = (picWith - dip(20)) / 4 * 5
//                    ) {
//                        imageTarget = object : SimpleTarget<GlideDrawable>(width, height) {
//                            override fun onResourceReady(
//                                resource: GlideDrawable?,
//                                glideAnimation: GlideAnimation<in GlideDrawable>?
//                            ) {
//                                imgIcon.setImageDrawable(resource)
//                            }
//                        }
//                    }
//                    // mask
//                    imageView {
//                        scaleType = ImageView.ScaleType.FIT_XY
//                        imageResource = R.drawable.icon_book_mask
//                    }.lparams {
//                        marginStart = dip(2)
//                        sameTop(imgIcon)
//                        sameBottom(imgIcon)
//                        width = wrapContent
//                        height = (picWith - dip(20)) / 4 * 5
//                    }
//                }.lparams {
//                    width = dip(0)
//                    weight = 1f
//                }
//                imageView {
//                    scaleType = ImageView.ScaleType.FIT_XY
//                    imageResource = R.drawable.icon_book_right_side
//                }.lparams {
//                    width = dip(6)
//                    height = (picWith - dip(20)) / 4 * 5
//                    gravity = Gravity.CENTER_VERTICAL
//                }
//            }.lparams {
//                centerInParent()
//            }
//        }
//    }
//}
