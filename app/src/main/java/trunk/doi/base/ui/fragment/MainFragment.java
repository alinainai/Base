package trunk.doi.base.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.ui.adapter.GalleryAdapter;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.api.GankItemService;
import trunk.doi.base.https.net.NetManager;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.https.rx.RxSubscriber;
import trunk.doi.base.ui.activity.utils.ImageZoomActivity;
import trunk.doi.base.view.ImageCycleView;
import trunk.doi.base.view.pullrefresh.CustomRefreshHeader;
import trunk.doi.base.view.pullrefresh.RefreshLayout;



/**
 * Created by ly on 2016/5/30 11:05.
 * 首页的fragment  (首页第一个栏目)
 */
public class MainFragment extends BaseFragment {


    public static final String TAG = "MainFragment";
    @BindView(R.id.img_part_1)
    ImageView imgPart1;
    @BindView(R.id.img_part_2)
    ImageView imgPart2;
    @BindView(R.id.ll_part_1a)
    LinearLayout llPart1a;
    @BindView(R.id.img_part_3)
    ImageView imgPart3;
    @BindView(R.id.img_part_4)
    ImageView imgPart4;
    @BindView(R.id.ll_part_1b)
    LinearLayout llPart1b;
    @BindView(R.id.rl_horizontal)
    RecyclerView mRecyclerView;
    @BindView(R.id.tv_gift_more)
    TextView tvGiftMore;
    @BindView(R.id.img_preferential_1)
    ImageView imgPreferential1;
    @BindView(R.id.img_preferential_2)
    ImageView imgPreferential2;
    @BindView(R.id.img_preferential_3)
    ImageView imgPreferential3;
    @BindView(R.id.img_preferential_4)
    ImageView imgPreferential4;
    @BindView(R.id.ll_preferential_below)
    LinearLayout llPreferentialBelow;
    @BindView(R.id.ll_preferential)
    LinearLayout llPreferential;
    @BindView(R.id.tv_more_news)
    TextView tvMoreNews;
    @BindView(R.id.img_news_1)
    ImageView imgNews1;
    @BindView(R.id.tv_news_title_1)
    TextView tvNewsTitle1;
    @BindView(R.id.tv_news_date_1)
    TextView tvNewsDate1;
    @BindView(R.id.ll_news_1)
    LinearLayout llNews1;
    @BindView(R.id.img_news_2)
    ImageView imgNews2;
    @BindView(R.id.tv_news_title_2)
    TextView tvNewsTitle2;
    @BindView(R.id.tv_news_date_2)
    TextView tvNewsDate2;
    @BindView(R.id.rl_news_1)
    RelativeLayout rlNews1;
    @BindView(R.id.img_news_3)
    ImageView imgNews3;
    @BindView(R.id.tv_news_title_3)
    TextView tvNewsTitle3;
    @BindView(R.id.tv_news_date_3)
    TextView tvNewsDate3;
    @BindView(R.id.rl_news_2)
    RelativeLayout rlNews2;
    @BindView(R.id.ll_news)
    LinearLayout llNews;
    @BindView(R.id.tv_class_info)
    TextView tvClassInfo;
    @BindView(R.id.img_class_1)
    ImageView imgClass1;
    @BindView(R.id.tv_class_title_1)
    TextView tvClassTitle1;
    @BindView(R.id.tv_class_date_1)
    TextView tvClassDate1;
    @BindView(R.id.rl_class_0)
    RelativeLayout rlClass0;
    @BindView(R.id.img_class_2)
    ImageView imgClass2;
    @BindView(R.id.tv_class_title_2)
    TextView tvClassTitle2;
    @BindView(R.id.tv_class_date_2)
    TextView tvClassDate2;
    @BindView(R.id.rl_class_1)
    RelativeLayout rlClass1;
    @BindView(R.id.icv_vp)
    ImageCycleView icv_vp;
    @BindView(R.id.refresh)
    RefreshLayout refreshLayout;


    private GalleryAdapter mAdapter;
    private ArrayList<GankItemData> mDatas=new ArrayList<>();
    private ArrayList<GankItemData> urlList=new ArrayList<>();
    private ImageCycleView.ImageCycleViewListener<GankItemData> mAdCycleViewListener;


    public MainFragment() {}

    public static MainFragment newInstance() {
        MainFragment fragment = new MainFragment();
        return fragment;
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_blank;
    }


    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        //设置布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);


        refreshLayout.setRefreshListener(new RefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // 延迟3秒后刷新成功
                refreshLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshLayout.refreshComplete();

                    }
                }, 1000);
            }
        });

        CustomRefreshHeader header  = new CustomRefreshHeader(getActivity());
        refreshLayout.setRefreshHeader(header);
        refreshLayout.autoRefresh();


        mRecyclerView.setLayoutManager(linearLayoutManager);
        mRecyclerView.setNestedScrollingEnabled(false);
        //设置适配器
        mAdapter = new GalleryAdapter(getActivity(), mDatas);
        mAdapter.setOnItemClickLitener(new GalleryAdapter.OnItemClickLitener() {
            @Override
            public void onItemClick(View view, int position) {

            }
        });
        mRecyclerView.setAdapter(mAdapter);

         mAdCycleViewListener = new ImageCycleView.ImageCycleViewListener<GankItemData>() {
            @Override
            public void onImageClick(int position, View imageView) {


            }
            @Override
            public void displayImage(GankItemData imageURL, ImageView imageView) {

                Glide.with(MainFragment.this)
                        .load(imageURL.getUrl())
                        .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                        .into(imageView);

            }
        };
        /**设置数据*/
      //  icv_vp.setImageResources(urlList, mAdCycleViewListener);
    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }


    private void laodData(boolean isShowDialig){

        RxManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class).getBeautyData("data/" + "福利" + "/18/" + 1),
                new RxSubscriber<BeautyResult<List<GankItemData>>>(mContext,true,true) {
            @Override
            protected void _onNext(BeautyResult<List<GankItemData>> listBeautyResult) {

            }

            @Override
            protected void _onError(int code) {

            }
        });


    }




    @OnClick({R.id.img_part_1, R.id.img_part_2, R.id.img_part_3, R.id.img_part_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_part_1:

                Intent intent = new Intent(mContext, ImageZoomActivity.class);
                ArrayList<String> urls = new ArrayList<>();
                urls.add("http://7xi8d6.com1.z0.glb.clouddn.com/20171114101305_NIAzCK_rakukoo_14_11_2017_10_12_58_703.jpeg");
                intent.putStringArrayListExtra("imgpath", urls);
                startActivity(intent);


                break;
            case R.id.img_part_2:
                break;
            case R.id.img_part_3:
                break;
            case R.id.img_part_4:
                break;

        }
    }



}
