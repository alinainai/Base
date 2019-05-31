package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;
import trunk.doi.base.R;
import trunk.doi.base.util.GasUtils;


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
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {

    }

    @Override
    public void initView(@NonNull View view, @Nullable Bundle savedInstanceState) {

    }


    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public int initLayoutId() {
        return R.layout.activity_blank;
    }


    private void loadData() {

//        RxBindManager.getInstance().doSubscribe(NetManager.getInstance().create(GankItemService.class,mContext).getBeautyData("data/" + "福利" + "/18/" + 1),
//                new Observer<BeautyResult<List<GankItemData>>>() {
//
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        mDisposable=d;
//                    }
//
//                    @Override
//                    public void onNext(BeautyResult<List<GankItemData>> listBeautyResult) {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });


    }

    @OnClick({R.id.btn_load, R.id.btn_etr})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_load:
                tvShow.setText(String.valueOf(GasUtils.dpTopx(mContext,16)));
                break;
            case R.id.btn_etr:
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
