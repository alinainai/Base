package com.gas.app.ui.fragment.mine

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.base.baseui.dialog.ItemSelectDialog
import com.base.baseui.ui.base.FragmentContainerActivity
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.gas.app.R
import com.gas.app.test.fragment.waitnotifytest.TaskFragment
import com.gas.app.ui.fragment.mine.di.DaggerMineComponent
import com.gas.app.ui.fragment.mine.mvp.MineContract
import com.gas.app.ui.fragment.mine.mvp.MinePresenter
import com.lib.commonsdk.extension.app.debug
import com.lib.commonsdk.kotlin.utils.fromJson
import kotlinx.android.synthetic.main.fragment_mine.*
import java.util.*
import kotlin.math.absoluteValue


class MineFragment : LazyLoadFragment<MinePresenter>(), MineContract.View {

    private var tintTest: ImageView? = null

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val textV = view?.findViewById<TextView>(R.id.showCode)

        tintTest = view?.findViewById(R.id.tintTest)

        var icon: Bitmap? = null

        view?.findViewById<View>(R.id.btnMine1)?.setOnClickListener {
//            debug(getProcessName(requireActivity().application, Process.myPid()))
            icon = BitmapFactory.decodeResource(
            requireContext().resources,
            R.mipmap.baseui_adapter_data_empty)
            icon?.let {
                tintTest?.setImageBitmap(icon)
            }
        }
        view?.findViewById<View>(R.id.btnMine2)?.setOnClickListener {
//            showBatteryOptimizeTimeDialog(context as Activity, "10") { item ->
//                debug(item.key)
//            }
            icon?.recycle()
        }
        view?.findViewById<View>(R.id.btnMine3)?.setOnClickListener {
            icon?.let {
                tintTest?.setImageBitmap(icon)
            }
        }
        view?.findViewById<View>(R.id.btnMine4)?.setOnClickListener {
            initEventTitle()
        }
        view?.findViewById<View>(R.id.btnMine5)?.setOnClickListener {

            debug("DeductTime", "getDeductTime=${getDeductTime()}")


//            val a: String? = null
//            debug("test", "test =${"1" == a}")
        }
        view?.findViewById<View>(R.id.btnMine8)?.setOnClickListener {
            FragmentContainerActivity.startActivity(requireActivity(), TaskFragment::class.java)
        }
        view?.findViewById<View>(R.id.btnMine6)?.setOnClickListener {
            val testModel = fromJson<TestModel>(jsonStr2, TestModel::class.java)
            debug("testModel", testModel.description)
            debug("testModel", testModel.id)
        }
        view?.findViewById<View>(R.id.btnMineF)?.setOnClickListener {
            val str = "https://jia.360.cn/mall/index_guide.html?from=weixin&scheme=buycloudrecord/mini"
            val uri = Uri.parse(str)
            debug("scheme=${uri.getQueryParameter("scheme")}")
        }
        view?.findViewById<View>(R.id.btnMine7)?.setOnClickListener {


//            val cal = Calendar.getInstance(Locale.getDefault())
//            val zoneOffset = cal.get(java.util.Calendar.ZONE_OFFSET);
//            val dstOffset = cal.get(java.util.Calendar.DST_OFFSET);
            val timeZone = TimeZone.getDefault()

            //取到秒级别减少运算
            val zoneOffset = TimeZone.getDefault().rawOffset / 1000
            val hour = zoneOffset / 3600
            val sb = StringBuilder("UTC")
            if (hour > 0) sb.append("+")

//
//            debug("zoneOffset=${zoneOffset/3600000},dstOffset=${dstOffset},zoneOffset1=${zoneOffset1/3600000}")
            val timeStr = timeZone.getDisplayName(false, TimeZone.SHORT)
            debug("timeStr=${TimeZone.getDefault().rawOffset}")
            debug("getFormatTimezone=${getFormatTimezone()}")

        }

        tintTest?.setOnClickListener {
            tintTest?.let {
                val drawable: Drawable = it.drawable.mutate() //
                val wrap: Drawable = DrawableCompat.wrap(drawable)
                DrawableCompat.setTint(wrap, ContextCompat.getColor(requireContext(), android.R.color.holo_red_dark))
                it.setImageDrawable(wrap)
            }
        }

    }

    private fun getFormatTimezone(): String {
        //取到秒级别减少运算
        val zoneOffset = TimeZone.getDefault().rawOffset / 1000
        val hour = zoneOffset / 3600
        return StringBuilder("UTC").apply {
            if (hour >= 0) append("+")
            append(hour)
            val remain = zoneOffset % 3600
            if (remain != 0) {
                (remain.toFloat() / 3600F * 10).toInt().absoluteValue.also {
                    if (it > 0)
                        append(".").append(it)
                }
            }
        }.toString()
    }

    override fun setData(data: Any?) {}


    override fun lazyLoadData() {}
    override val wrapContext: Context
        get() = requireContext()

    override fun showMessage(message: String) {}


    val deduct_time: String? = "1.3"
    fun getDeductTime(): Long = deduct_time?.let {
        return@let try {
            it.toLong()
        } catch (e: NumberFormatException) {
            0L
        }
    } ?: 0L

    val jsonStr = "{\"id\":1,\"description\":\"Test\"}"
    val jsonStr1 = "{\"id\":1}"
    val jsonStr2 = "{\"description\":\"Test\"}"

    data class TestModel(
            val id: Int = 0,
            val description: String? = null
    )

    private fun showBatteryOptimizeTimeDialog(ctx: Context, selectKey: String, action: (item: ItemSelectDialog.DefaultItemSelect) -> Unit) {
        val items = mutableListOf(
                ItemSelectDialog.DefaultItemSelect("10$", "10"),
                ItemSelectDialog.DefaultItemSelect("15$111", "15"),
                ItemSelectDialog.DefaultItemSelect("20$111", "20"),
                ItemSelectDialog.DefaultItemSelect("30$111", "30"),
                ItemSelectDialog.DefaultItemSelect("60$111", "60")
        )
        val dialog = ItemSelectDialog(ctx, "一个title", items, selectKey, action)
        dialog.show()
    }


    private fun textFormatStyle(num: Int): SpannableStringBuilder {
        return if (num > 0) {
            val startStr = num.toString()
            val endFix = "条"
            val str = "$startStr$endFix"
            SpannableStringBuilder(str).apply {
                setSpan(ForegroundColorSpan(Color.parseColor("#2B2D2F")), 0, startStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(30, true), 0, startStr.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(ForegroundColorSpan(Color.parseColor("#7A7E8E")), startStr.length, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
                setSpan(AbsoluteSizeSpan(9, true), startStr.length, str.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        } else {
            SpannableStringBuilder("暂无消息")
        }
    }

    private fun initEventTitle() {
        llContent.removeAllViews()
        val list = listOf("第一条Preconditions.checkNotNull(message) setData(data: Any?) setData(data: Any?)",
                " 第二条 ArmsUtils.snackbarText(message) Preconditions.checkNotNull(intent)",
                "第三条 ArmsUtils.startActivity(intent) Preconditions.checkNotNull(intent)",
                "第四条 Preconditions.checkNotNull(message) setData(data: Any?) setData(data: Any?)")
        list.forEach { title ->
            val itemView = View.inflate(context, R.layout.layout_event_title, null)
            val tvTitle = itemView.findViewById<TextView>(R.id.tvReportMessage)
            tvTitle.text = title
            val drawable = resources.getDrawable(R.drawable.shape_report_message_ring_blue)
            drawable.setBounds(0, 0, drawable.minimumWidth, drawable.minimumHeight)
            tvTitle.setCompoundDrawables(drawable, null, null, null)
            llContent.addView(itemView)
        }
    }

    companion object {
        const val TAG = "MineFragment"

        @JvmStatic
        fun newInstance(): MineFragment {
            return MineFragment()
        }
    }
}