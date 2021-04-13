package com.gas.app.ui.fragment.mine

import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Process
import android.text.Html
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
import com.base.lib.base.LazyLoadFragment
import com.base.lib.di.component.AppComponent
import com.gas.app.R
import com.gas.app.ui.fragment.mine.di.DaggerMineComponent
import com.gas.app.ui.fragment.mine.mvp.MineContract
import com.gas.app.ui.fragment.mine.mvp.MinePresenter
import com.gas.app.utils.AppModuleUtil
import com.gas.app.utils.getProcessName
import com.lib.commonsdk.extension.app.debug
import com.lib.commonsdk.kotlin.utils.fromJson
import kotlinx.android.synthetic.main.fragment_mine.*


class MineFragment : LazyLoadFragment<MinePresenter>(), MineContract.View {

    private var tintTest:ImageView?=null

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMineComponent.builder().appComponent(appComponent).view(this).build().inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.fragment_mine, container, false)
    }

    override fun initData(savedInstanceState: Bundle?) {

        val textV = view?.findViewById<TextView>(R.id.showCode)

        tintTest = view?.findViewById(R.id.tintTest)

        view?.findViewById<View>(R.id.btnMine1)?.setOnClickListener {
            debug(getProcessName(requireActivity().application, Process.myPid()))
        }
        view?.findViewById<View>(R.id.btnMine2)?.setOnClickListener {
            showBatteryOptimizeTimeDialog(context as Activity, "10") { item ->
                debug(item.key)
            }
        }
        view?.findViewById<View>(R.id.btnMine3)?.setOnClickListener {
            val str1 = getString(R.string.little_cloud_data_size, AppModuleUtil.formatMemorySizeB(53687091200), AppModuleUtil.formatMemorySizeB(12345678))
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                textV?.text = Html.fromHtml(str1, Html.FROM_HTML_MODE_LEGACY);
            } else {
                textV?.text = Html.fromHtml(str1);
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
        view?.findViewById<View>(R.id.btnMine6)?.setOnClickListener {
            val testModel =  fromJson<TestModel>(jsonStr2, TestModel::class.java)
            debug("testModel", testModel.description)
            debug("testModel", testModel.id)
        }
        view?.findViewById<View>(R.id.btnMine7)?.setOnClickListener {
           val str = "https://jia.360.cn/mall/index_guide.html?from=weixin&scheme=buycloudrecord/mini"
            val uri = Uri.parse(str)
            debug("scheme=${uri.getQueryParameter("scheme")}")
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

    override fun setData(data: Any?) {}


    override fun lazyLoadData() {}
    override val wrapContext: Context
        get() = requireContext()

    override fun showMessage(message: String) {}


    val  deduct_time:String? = "1.3"
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