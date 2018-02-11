package trunk.doi.base.util;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import trunk.doi.base.R;


/**
 * ToastUtils 工具类
 */
public class ToastUtil {

    public final static int TOAST_OF_WARING = 1;
    public final static int TOAST_OF_ERROR = 2;
    public final static int TOAST_OF_SUCCESS = 0;
    private static Toast toast = null;

    private ToastUtil() {
        throw new AssertionError();
    }

    public static void show(Context context, int resId) {
        showMessage(context, context.getApplicationContext().getResources().getText(resId), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration) {
        showMessage(context, context.getApplicationContext().getResources().getText(resId), duration);
    }

    public static void show(Context context, CharSequence text) {
        showMessage(context.getApplicationContext(), text, Toast.LENGTH_SHORT);
    }

    public static void show(Context context, CharSequence text, int duration) {
        showMessage(context.getApplicationContext(), text, duration);
    }

    public static void show(Context context, int resId, Object... args) {
        showMessage(context.getApplicationContext(), String.format(context.getResources().getString(resId), args),
                Toast.LENGTH_SHORT);
    }

    public static void show(Context context, String format, Object... args) {
        showMessage(context.getApplicationContext(), String.format(format, args), Toast.LENGTH_SHORT);
    }

    public static void show(Context context, int resId, int duration, Object... args) {
        showMessage(context.getApplicationContext(), String.format(context.getResources().getString(resId), args), duration);
    }

    public static void show(Context context, String format, int duration, Object... args) {
        showMessage(context.getApplicationContext(), String.format(format, args), duration);
    }

    private static void showMessage(final Context context, final CharSequence text,
                                    final int duration) {

        FrameLayout layout = (FrameLayout) ((LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_toast_text, null, false);
        TextView textView = (TextView) layout.findViewById(R.id.tv_item);
        textView.setText(text);

        if (toast == null) {
            toast = new Toast(context.getApplicationContext());
            toast.setView(layout);//setting the view of custom toast layout
        } else {
            toast.setView(layout);//setting the view of custom toast layout
        }
        toast.setDuration(duration);
        toast.setGravity(Gravity.CENTER, 0, 10);
        toast.show();
    }

    public static void showCustomToast(final Context context, final CharSequence text,
                                       final int flag) {

        FrameLayout layout = (FrameLayout) ((LayoutInflater) context.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.view_toast, null, false);
        TextView textView = (TextView) layout.findViewById(R.id.tv_item);
        textView.setText(text);
        ImageView image = (ImageView) layout.findViewById(R.id.iv_item);
        // 对号
        if (flag == TOAST_OF_SUCCESS) {
            image.setImageResource(R.drawable.qmui_icon_notify_done);
            // 叹号
        } else if (flag == TOAST_OF_WARING) {
            image.setImageResource(R.drawable.qmui_icon_notify_info);
            // 错误
        } else if (flag == TOAST_OF_ERROR) {
            image.setImageResource(R.drawable.qmui_icon_notify_error);
        }
        if (toast == null) {
            toast = new Toast(context.getApplicationContext());
            toast.setView(layout);//setting the view of custom toast layout
        } else {
            toast.setView(layout);//setting the view of custom toast layout
        }
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 10);
        toast.show();

    }


    public static void cancel() {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
    }


}
