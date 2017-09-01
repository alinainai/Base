package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.JsonSyntaxException;

import java.net.SocketTimeoutException;

import butterknife.OnClick;
import retrofit2.HttpException;
import rx.Subscriber;
import trunk.doi.base.R;
import trunk.doi.base.base.BaseFragment;
import trunk.doi.base.https.api.MessageApi;
import trunk.doi.base.https.net.NetManager;
import trunk.doi.base.https.rx.RxManager;
import trunk.doi.base.util.ToastUtils;

/**
 * Created by ly on 2016/5/30 11:05.
 * 首页的fragment  (首页第四个栏目)
 */
public class CartFragment extends BaseFragment {

    public static final String TAG = "CartFragment";


    public static CartFragment newInstance() {
        return new CartFragment();
    }


    @Override
    protected int initLayoutId() {
        return R.layout.fragment_cart;
    }

    @Override
    public void initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


    }

    @Override
    public void setListener(View view, Bundle savedInstanceState) {
    }

    @Override
    public void initData(Bundle savedInstanceState) {

    }


    @OnClick({R.id.img_close,R.id.img_get})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_close:

                break;
            case R.id.img_get:

                Log.e("TAG","img_get");

                RxManager.getInstance().doSubscribe(NetManager.getInstance().create(MessageApi.class).
                      getMessageData("18600524853"),
                        new Subscriber<Object>() {
                    @Override
                    public void onCompleted() {


                    }
                    @Override
                    public void onError(Throwable throwable) {

                        throwable.printStackTrace();
                        if (throwable instanceof SocketTimeoutException) {
                            ToastUtils.showShort(context,"连接超时");
                        } else if(throwable instanceof JsonSyntaxException){
                            ToastUtils.showShort(context,"请求数据错误");
                        }else if(throwable instanceof HttpException){
                            ToastUtils.showShort(context,"连接异常");
                        }else{
                            ToastUtils.showShort(context,"连接异常");
                        }
                    }
                    @Override
                    public void onNext(Object message) {
                        Log.e("TAG",message.toString());

                    }
                });



                break;

        }
    }

}
