package trunk.doi.base.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import trunk.doi.base.R;


/**
 * ToastUtils 工具类
 */
public class ToastUtil {

    private ToastUtil() {
        throw new AssertionError();
    }

    public static void show(Context context, CharSequence text) {
       Toast.makeText(context.getApplicationContext(),text,Toast.LENGTH_SHORT).show();
    }

}
