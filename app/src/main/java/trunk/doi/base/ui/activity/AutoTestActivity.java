package trunk.doi.base.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.text.method.ScrollingMovementMethod;
import android.widget.EditText;
import android.widget.TextView;

import trunk.doi.base.R;


public class AutoTestActivity extends AppCompatActivity {


    private TextView LocationResult;
    private AppCompatButton startLocation;

    private EditText editText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        // -----------demo view config ------------
        setContentView(R.layout.activity_auto_test);
        LocationResult = (TextView) findViewById(R.id.tv_location);
        LocationResult.setMovementMethod(ScrollingMovementMethod.getInstance());
        startLocation = (AppCompatButton) findViewById(R.id.btn_start);
        editText = (EditText) findViewById(R.id.email);


    }

    /**
     * 显示请求字符串
     *
     * @param str
     */
    public void logMsg(String str) {
        final String s = str;
        try {
            if (LocationResult != null) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        LocationResult.post(new Runnable() {
                            @Override
                            public void run() {
                                LocationResult.setText(s);
                            }
                        });

                    }
                }).start();
            }
            //LocationResult.setText(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
