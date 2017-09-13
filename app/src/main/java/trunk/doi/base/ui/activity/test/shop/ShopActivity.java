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

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    private List<Item> data;
    private Shop shop;


    private DiscreteScrollView itemPicker;
    private DiscreteScrollView itemPicker_1;

    private String aName;
    private String aPrice;

    private DiscreteScrollView.CurrentItemChangeListener changeListener= new DiscreteScrollView.CurrentItemChangeListener(){

        @Override
        public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int adapterPosition) {

        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);



        shop = Shop.get();
        data =shop.getData();
        itemPicker = (DiscreteScrollView) findViewById(R.id.item_picker);
        itemPicker.setCurrentItemChangeListener(null);
        itemPicker.setCurrentItemChangeListener(new DiscreteScrollView.CurrentItemChangeListener() {
            @Override
            public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                itemPicker_1.smoothScrollToPosition(adapterPosition);
                aName=data.get(adapterPosition).getName();
            }
        });
        itemPicker.setAdapter(new ShopAdapter(data));
        itemPicker.setItemTransitionTimeMillis(150);
        itemPicker.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());

        itemPicker_1 = (DiscreteScrollView) findViewById(R.id.item_picker_1);

        itemPicker_1.setCurrentItemChangeListener(new DiscreteScrollView.CurrentItemChangeListener() {
            @Override
            public void onCurrentItemChanged(RecyclerView.ViewHolder viewHolder, int adapterPosition) {
                aPrice=data.get(adapterPosition).getPrice();
            }
        });
        itemPicker_1.setAdapter(new ShopAdapter(data));
        itemPicker_1.setItemTransitionTimeMillis(150);
        itemPicker_1.setItemTransformer(new ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build());


    }

    @Override
    public void onClick(View v) {

    }



}
