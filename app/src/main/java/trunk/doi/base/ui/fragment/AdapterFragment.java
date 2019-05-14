package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;

import com.base.baseui.dialog.GasAlertDialog;
import com.base.baseui.dialog.GasDialog;
import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.paginate.base.status.IStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;
import trunk.doi.base.R;
import trunk.doi.base.adapter.GankItemAdapter;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.view.CustomEmptyView;
import trunk.doi.base.view.CustomFooterLoadMore;


/**
 * Created by
 * 首页的fragment  (首页第一个栏目)
 */
public class AdapterFragment extends BaseFragment {

    public static final String TAG = "AdapterFragment";

    private GankItemAdapter mGankItemAdapter;//适配器

    @BindView(R.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    private int mPage = 1;
    private static final int DELAYTIME = 1000;
    private boolean showNormal = true;


    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {

            switch (msg.what) {

                case 1:
                    if (mPage <= 5) {
                        mPage++;
                        mGankItemAdapter.setLoadMoreData(getData(mPage));
                    } else {
                        mGankItemAdapter.loadEnd();
                    }
                    break;
                case 2:
                    mSwipeRefreshLayout.setRefreshing(false);
                    mPage = 1;
                    if (showNormal) {
                        mGankItemAdapter.setNewData(getData(mPage));
                        showNormal = false;
                    } else {
                        mGankItemAdapter.setEmptyView(IStatus.STATUS_FAIL);
                        showNormal = true;
                    }
                    break;

            }
            return true;
        }
    });

    @Override
    public int initLayoutId() {
        return R.layout.fragment_adapter;
    }


    public AdapterFragment() {
    }

    public static AdapterFragment newInstance() {
        return new AdapterFragment();
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {


        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        //刷新控件
        mSwipeRefreshLayout.setColorSchemeResources(R.color.white);
        mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(getResources().getColor(R.color.cff3e19));
        mGankItemAdapter = new GankItemAdapter(mContext, new ArrayList<>());


        //条目点击
        mGankItemAdapter.setOnMultiItemClickListener((viewHolder, data, position, viewType) -> {
                    Timber.tag(TAG).e("initView: position=%d", position);
//                    mGankItemAdapter.remove(position);
                    FragmentManager fragmentManager = getFragmentManager();
                    GasAlertDialog newFragment = new GasAlertDialog.Builder()
                            .setTitle("提示")
                            .setMsg("测试一下")
                            .setPosBtnTxt("点击关闭", null)
                            .setCancelable(false)
                            .creat();
                    newFragment.show(fragmentManager);
                }
        );

        //失败重新加载
        mGankItemAdapter.setOnReloadListener(() -> {
            mGankItemAdapter.setEmptyView(IStatus.STATUS_LOADING);
            mHandler.sendEmptyMessageDelayed(2, DELAYTIME);
        });

        //上拉加载更多
        mGankItemAdapter.setOnLoadMoreListener(isReload -> mHandler.sendEmptyMessageDelayed(1, DELAYTIME));
        mGankItemAdapter.setDefaultEmptyView(new CustomEmptyView(mContext));
        mGankItemAdapter.setDefaultFooterLoadMore(new CustomFooterLoadMore(mContext));

        //下拉刷新
        mSwipeRefreshLayout.setOnRefreshListener(() -> mHandler.sendEmptyMessageDelayed(2, DELAYTIME));


        mRecyclerView.addItemDecoration(new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));

        mRecyclerView.setAdapter(mGankItemAdapter);
        View header = LayoutInflater.from(mContext).inflate(R.layout.dialog_more, null);
        mGankItemAdapter.addHeaderView(header);
        mGankItemAdapter.setEmptyView(IStatus.STATUS_LOADING);
        mHandler.sendEmptyMessageDelayed(2, DELAYTIME);
    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    private List<GankItemData> getData(int i) {

        List<GankItemData> data = new ArrayList<>();
        for (int i1 = 0; i1 < 10; i1++) {
            GankItemData item = new GankItemData();
            item.setDesc(String.format(Locale.CHINA, "文件%d00%d", i, i1));
            data.add(item);
        }
        return data;
    }


    @OnClick({R.id.btn_add_one, R.id.btn_add_multi, R.id.btn_delete_one})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_one:
                GankItemData item = new GankItemData();
                item.setDesc("新数据");
                mGankItemAdapter.insert(item);
                break;
            case R.id.btn_add_multi:
                mGankItemAdapter.insert(getData(100));
                break;
            case R.id.btn_delete_one:
                break;
        }
    }
}
