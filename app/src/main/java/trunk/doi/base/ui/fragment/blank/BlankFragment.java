package trunk.doi.base.ui.fragment.blank;

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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.adapter.GalleryAdapter;
import trunk.doi.base.base.mvp.BaseMvpFragment;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.view.ImageCycleView;
import trunk.doi.base.view.pullrefresh.CustomRefreshHeader;
import trunk.doi.base.view.pullrefresh.RefreshLayout;

import static trunk.doi.base.R.id.img_class_1;
import static trunk.doi.base.R.id.img_news_1;
import static trunk.doi.base.R.id.img_part_1;
import static trunk.doi.base.R.id.img_preferential_1;

/**
 * Created by ly on 2016/5/30 11:05.
 * 首页的fragment  (首页第一个栏目)
 */
public class BlankFragment extends BaseMvpFragment<BlankView, BlankPresenter> implements BlankView{


    public static final String TAG = "BlankFragment";
    @BindView(img_part_1)
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
    @BindView(img_preferential_1)
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
    @BindView(img_news_1)
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
    @BindView(img_class_1)
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

    public BlankFragment() {


    }

    public static BlankFragment newInstance() {
        BlankFragment fragment = new BlankFragment();
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

                Glide.with(BlankFragment.this)
                        .load(imageURL.getUrl())
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
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

    @Override
    public void initFetchData() {

        mPresenter.getItemData("data/" + "福利" + "/18/" + 1);

    }


    @Override
    protected BlankPresenter initPresenter() {
        return new BlankPresenter();
    }

    @Override
    protected void fetchData() {

    }

    @OnClick({img_part_1, R.id.img_part_2, R.id.img_part_3, R.id.img_part_4})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case img_part_1:
                break;
            case R.id.img_part_2:
                break;
            case R.id.img_part_3:
                break;
            case R.id.img_part_4:
                break;

        }
    }

    @Override
    public void onError() {

    }

    @Override
    public void onSuccess(List<GankItemData> data) {


        Glide.with(this)
                .load(data.get(0).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPart1);
        Glide.with(this)
                .load(data.get(1).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPart2);
        Glide.with(this)
                .load(data.get(2).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPart3);
        Glide.with(this)
                .load(data.get(3).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPart4);

        if(data.size()>5){
            mDatas.clear();
            urlList.clear();
            for(int i =0;i<5;i++){
                mDatas.add(data.get(i));
                urlList.add(data.get(i));
            }
            mAdapter.notifyDataSetChanged();
              icv_vp.setImageResources(urlList, mAdCycleViewListener);
            icv_vp.startImageCycle();
        }
        Glide.with(this)
                .load(data.get(6).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPreferential1);
        Glide.with(this)
                .load(data.get(5).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPreferential2);
        Glide.with(this)
                .load(data.get(4).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPreferential3);
        Glide.with(this)
                .load(data.get(3).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgPreferential4);

        Glide.with(this)
                .load(data.get(3).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgNews1);
        Glide.with(this)
                .load(data.get(2).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgNews2);
        Glide.with(this)
                .load(data.get(1).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgNews3);
        Glide.with(this)
                .load(data.get(7).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgClass1);
        Glide.with(this)
                .load(data.get(8).getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(imgClass2);
    }
}
