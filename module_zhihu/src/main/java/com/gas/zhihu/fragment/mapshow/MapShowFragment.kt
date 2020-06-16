package com.gas.zhihu.fragment.mapshow

import android.Manifest
import android.app.Activity
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.base.baseui.view.RecyclerStickHeaderHelper
import com.base.baseui.view.WaveSideBarView
import com.base.lib.base.BaseFragment
import com.base.lib.di.component.AppComponent
import com.base.lib.util.ArmsUtils
import com.base.paginate.interfaces.EmptyInterface
import com.gas.zhihu.R
import com.gas.zhihu.app.MapConstants.Companion.MAP_CONST_KEY
import com.gas.zhihu.app.MapConstants.Companion.MAP_ID
import com.gas.zhihu.app.MapConstants.Companion.MAP_NAME
import com.gas.zhihu.app.MapConstants.Companion.MAP_OPTION_DEFAULT
import com.gas.zhihu.app.MapConstants.Companion.MAP_OPTION_SELECT
import com.gas.zhihu.app.MapConstants.Companion.MAP_OPTION_TO_SHOW
import com.gas.zhihu.app.MapConstants.Companion.PAPER_TYPE_DEFAULT
import com.gas.zhihu.fragment.mapshow.bean.ISortBean
import com.gas.zhihu.fragment.mapshow.bean.MapShowBean
import com.gas.zhihu.fragment.mapshow.di.DaggerMapShowComponent
import com.gas.zhihu.fragment.mapshow.di.MapShowModule
import com.gas.zhihu.fragment.mapshow.mvp.MapShowContract
import com.gas.zhihu.fragment.mapshow.mvp.MapShowPresenter
import com.lib.commonsdk.utils.Permission
import kotlinx.android.synthetic.main.zhihu_fragment_map_show.*
import javax.inject.Inject

class MapShowFragment : BaseFragment<MapShowPresenter>(), MapShowContract.View {
    companion object {
        const val TYPE = "type"
        const val OPTION = "option"

        fun setPagerArgs(type: Int, option: Int): Bundle? {
            val args = Bundle()
            args.putInt(TYPE, type)
            args.putInt(OPTION, option)
            return args
        }

        fun newInstance(): MapShowFragment {
            val fragment = MapShowFragment()
            return fragment
        }
    }

    @Inject
    lateinit var mAdapter: MapShowAdapter

    @Inject
    lateinit var mLayoutManager: RecyclerView.LayoutManager

    private val vibrator: Vibrator by lazy {
        activity?.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
    }

    private var topPos = -1
    private var touchBar = false

    private val stickHeader: RecyclerStickHeaderHelper by lazy {
        RecyclerStickHeaderHelper(itemRecycler, listStickHolder, MapShowAdapter.TYPE_CHAR)
    }

    private var mType: Int = PAPER_TYPE_DEFAULT
    private var mOption: Int = MAP_OPTION_DEFAULT

    override fun setupFragmentComponent(appComponent: AppComponent) {
        DaggerMapShowComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .mapShowModule(MapShowModule(this))
                .build()
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.zhihu_fragment_map_show, container, false);
    }

    override fun initData(savedInstanceState: Bundle?) {
        mType = activity!!.intent.getIntExtra(TYPE, PAPER_TYPE_DEFAULT)
        mOption = activity!!.intent.getIntExtra(OPTION, MAP_OPTION_DEFAULT)
        guideTitle.setOnBackListener {
            killMyself()
        }
        mAdapter.setOnMultiItemClickListener { _, data, _, _ ->
            data.let {
                if (it is MapShowBean) {
                    when (mOption) {
                        MAP_OPTION_SELECT -> {
                            val intent = Intent()
                            intent.putExtra(MAP_NAME, it.mapBean.mapName)
                            intent.putExtra(MAP_ID, it.mapBean.keyName)
                            activity?.setResult(Activity.RESULT_OK, intent)
                            killMyself()
                        }
                        MAP_OPTION_TO_SHOW -> {

                        }
                        else -> {
                            //do nothing. later use
                        }
                    }
                }
            }
        }
        sideBarView.setOnTouchLetterChangeListener(object : WaveSideBarView.OnTouchLetterChangeListener {
            override fun onLetterChange(letter: String) {
                if (touchBar) {
                    mAdapter.findCharTipPos(letter).takeIf { it in 0 until mAdapter.itemCount }?.let { pos ->
                        itemRecycler.scrollToPosition(pos)
                        (itemRecycler.layoutManager as? LinearLayoutManager)?.scrollToPositionWithOffset(pos, 0)
                    }
                }
            }

            override fun onBarTouchDown() {
                touchBar = true
            }

            override fun onBarTouchMove() {
            }

            override fun onBarTouchCancel() {
                touchBar = false
                topPos.takeIf { it in 0 until mAdapter.itemCount }?.let { pos ->
                    mAdapter.getItem(pos).showChar.let {
                        sideBarView.setSelectChar(it)
                    }
                }
            }
        })
        etSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                mPresenter?.startSearch(s?.let { s.toString().trim() } ?: "")
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        itemRefresh.isEnabled = false
        ArmsUtils.configRecyclerView(itemRecycler, mLayoutManager)
        itemRecycler.adapter = mAdapter
        mAdapter.setEmptyView(EmptyInterface.STATUS_LOADING)
        stickHeader.addOnViewChangedListener(object : RecyclerStickHeaderHelper.OnViewChangedListener {
            override fun onViewChanged() {
//                if (Permission.hasPermissions(activity, Manifest.permission.VIBRATE)) {
//                    if (vibrator.hasVibrator()) {
//                        vibrator.cancel()
//                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                            vibrator.vibrate(VibrationEffect.createOneShot(10L, 100), null)
//                        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                            vibrator.vibrate(longArrayOf(10L), -1, null)
//                        }
//                    }
//                }
            }

            override fun onTopViewPosition(view: View, pos: Int) {
                if (topPos != pos) {
                    mAdapter.getItem(pos).showChar.let {
                        if (!touchBar) {
                            sideBarView.setSelectChar(it)
                        }
                    }
                    topPos=pos
                }
            }
        })
        mPresenter!!.initOriginData(mType)
        mPresenter!!.getFilterData("")
    }

    override fun setSliderBarTxt(chars: List<String>) {
        sideBarView.letters = chars
    }

    override fun setData(data: Any?) {

    }

    override fun getWrapActivity(): Activity {
        return activity!!
    }

    override fun setMapData(t: List<ISortBean>) {
        mAdapter.setNewData(t)
        if (t.isNotEmpty()) {
            itemRecycler.smoothScrollToPosition(0)
            mAdapter.loadEnd()
        } else {
            mAdapter.setEmptyView(EmptyInterface.STATUS_EMPTY)
        }
    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    override fun showMessage(message: String) {
        ArmsUtils.snackbarText(message)
    }

    override fun launchActivity(intent: Intent) {
        ArmsUtils.startActivity(intent)
    }

    override fun killMyself() {
        activity?.finish()
    }
}
