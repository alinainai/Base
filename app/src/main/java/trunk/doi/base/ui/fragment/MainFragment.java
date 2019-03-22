package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.base.lib.base.BaseFragment;
import com.base.lib.https.NetManager;
import com.base.lib.rx.RxRetrofitManager;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.R;
import trunk.doi.base.bean.BeautyResult;
import trunk.doi.base.bean.GankItemData;
import trunk.doi.base.https.GankItemService;


/**
 * Created by
 * 首页的fragment  (首页第一个栏目)
 */
public class MainFragment extends BaseFragment {


    public static final String TAG = "MainFragment";
    @BindView(R.id.tv_show)
    TextView tvShow;
    private Disposable mDisposable;


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

        RxRetrofitManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class).getBeautyData("data/" + "福利" + "/18/" + 1),
                new Observer<BeautyResult<List<GankItemData>>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        mDisposable=d;
                    }

                    @Override
                    public void onNext(BeautyResult<List<GankItemData>> listBeautyResult) {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

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

    @Override
    public void onDestroy() {
        if(mDisposable!=null&&mDisposable.isDisposed()){
            mDisposable.dispose();
        }
        mDisposable=null;
        super.onDestroy();

    }
}
