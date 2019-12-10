package com.gas.test.ui.fragment.retrofit;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.lib.util.ArmsUtils;
import com.gas.test.R;
import com.gas.test.bean.Repo;
import com.gas.test.http.TestService;
import com.gas.test.ui.fragment.retrofit.di.DaggerRetrofitComponent;
import com.gas.test.ui.fragment.retrofit.mvp.RetrofitContract;
import com.gas.test.ui.fragment.retrofit.mvp.RetrofitPresenter;
import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.base.lib.util.Preconditions.checkNotNull;


/**
 * ================================================
 * Description:
 * <p>
 * Created by GasMvpFragment on 12/05/2019 19:55
 * ================================================
 */
public class RetrofitFragment extends BaseFragment<RetrofitPresenter> implements RetrofitContract.View {

    /*
     * Retrofit
     *
     * A type-safe HTTP client for Android and Java
     *
     * 类型安全的HTTP客户端
     *
     * 类型安全 运行时不会报类型错误，错误在编译器就能察觉出来
     *
     *
     *
     *
     */



    public static RetrofitFragment newInstance() {
        RetrofitFragment fragment = new RetrofitFragment();
        return fragment;
    }

    @Override
    public void setupFragmentComponent(@NonNull AppComponent appComponent) {
        DaggerRetrofitComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .view(this)
                .build()
                .inject(this);
    }

    @Override
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.test_fragment_retrofit, container, false);
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

        Gson gson=new Gson();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        TestService service=retrofit.create(TestService.class);
        Call<List<Repo>> repos=service.listRepos("octocat");
        repos.enqueue(new Callback<List<Repo>>() {
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.e("respose",response.body().get(0).getName());
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {

            }
        });

    }


    @Override
    public void setData(@Nullable Object data) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {

    }
}
