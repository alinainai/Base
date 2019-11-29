package trunk.doi.base.ui.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.base.lib.base.BaseFragment;
import com.base.lib.di.component.AppComponent;
import com.base.paginate.interfaces.EmptyInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;
import trunk.doi.base.R;
import trunk.doi.base.R2;
import trunk.doi.base.bean.GankItemData;


/**
 * Created by
 * 首页的fragment  (首页第一个栏目)
 */
public class AdapterFragment extends BaseFragment {



    @BindView(R2.id.type_item_recyclerview)
    RecyclerView mRecyclerView;
    @BindView(R2.id.type_item_swipfreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;







    @Override
    public void initData(@Nullable Bundle savedInstanceState) {




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
    public View initView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_adapter,container,false);
    }


    @Override
    public void setData(@Nullable Object data) {

    }


}
