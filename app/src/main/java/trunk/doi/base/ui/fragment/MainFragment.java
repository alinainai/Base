package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.api.GankItemService;
import trunk.doi.base.https.net.NetManager;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.https.rx.RxSubscriber;


/**
 * Created by
 * 首页的fragment  (首页第一个栏目)
 */
public class MainFragment extends BaseFragment {


    public static final String TAG = "MainFragment";
    @BindView(R.id.tv_show)
    TextView tvShow;


    public MainFragment() {
    }

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    protected int initLayoutId() {
        return R.layout.activity_blank;
    }


    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }


    private void laodData() {

        RxManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class).getBeautyData("data/" + "福利" + "/18/" + 1),
                new RxSubscriber<BeautyResult<List<GankItemData>>>() {
                    @Override
                    protected void _onNext(BeautyResult<List<GankItemData>> listBeautyResult) {

                    }

                    @Override
                    protected void _onError(int code) {

                    }
                });


    }

    @OnClick({R.id.btn_load, R.id.btn_etr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:


                break;
            case R.id.btn_etr:
                laodData();
                break;
        }
    }


}
