package trunk.doi.base.ui.activity.cache;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import trunk.doi.base.R;

public class LruActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lru);
        findViewById(R.id.lru_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LruActivity.this,LruActivityActivity.class));

            }
        });

        findViewById(R.id.disk_lru_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LruActivity.this,DiskLruCacheActivity.class));
            }
        });

    }





}
