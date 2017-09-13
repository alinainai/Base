package trunk.doi.base.ui.activity.test.shop;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.Arrays;
import java.util.List;

import trunk.doi.base.R;
import trunk.doi.base.base.BaseApplication;

/**
 * Created by yarolegovich on 07.03.2017.
 */

public class Shop {

    private static final String STORAGE = "shop";

    public static Shop get() {
        return new Shop();
    }

    private SharedPreferences storage;

    private Shop() {
        storage = BaseApplication.instance.getSharedPreferences(STORAGE, Context.MODE_PRIVATE);
    }

    public List<Item> getData() {
        return Arrays.asList(
                new Item(1, "Everyday Candle", "$12.00 USD", R.mipmap.shop1),
        new Item(2, "Small Porcelain Bowl", "$50.00 USD", R.mipmap.shop2),
                new Item(3, "Favourite Board", "$265.00 USD", R.mipmap.shop3),
                new Item(4, "Earthenware Bowl", "$18.00 USD", R.mipmap.shop4),
                new Item(5, "Porcelain Dessert Plate", "$36.00 USD", R.mipmap.shop5),
                new Item(6, "Detailed Rolling Pin", "$145.00 USD", R.mipmap.shop5),
                new Item(5, "Detailed Dessert Pin", "$145.00 USD", R.mipmap.shop4),
                new Item(3, "Detailed Board Pin", "$145.00 USD", R.mipmap.shop3),
                new Item(2, "Detailed Candle Pin", "$145.00 USD", R.mipmap.shop2),
                new Item(1, "Detailed Rolling Bowl", "$145.00 USD", R.mipmap.shop1)
               );

    }

    public boolean isRated(int itemId) {
        return storage.getBoolean(String.valueOf(itemId), false);
    }

    public void setRated(int itemId, boolean isRated) {
        storage.edit().putBoolean(String.valueOf(itemId), isRated).apply();
    }
}
