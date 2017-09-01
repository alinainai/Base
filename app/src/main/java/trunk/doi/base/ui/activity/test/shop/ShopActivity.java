package trunk.doi.base.ui.activity.test.shop;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.ui.activity.test.discretescrollview.DiscreteScrollView;
import trunk.doi.base.ui.activity.test.discretescrollview.transform.ScaleTransformer;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class ShopActivity extends AppCompatActivity implements DiscreteScrollView.CurrentItemChangeListener,
        View.OnClickListener {

    private List<Item> data;
    private Shop shop;


    private DiscreteScrollView itemPicker;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);



        shop = Shop.get();
        data = shop.getData();
        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
        itemPicker.setCurrentItemChangeListener(this);
        itemPicker.setAdapter(new ShopAdapter(data));
        itemPicker.setItemTransitionTimeMillis(150);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());






    }

    @Override
    public void onClick(View v) {

    }



    @Override
    public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int position) {

    }


}
