package com.wy.djreader.welcome_page.View;

import android.content.ComponentName;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.wy.djreader.R;
import com.wy.djreader.main_page.view.MainActivity;

public class IndexActivity extends AppCompatActivity {

    private Button jump;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        initview();
        init();

    }

    /**
     * @desc
     * @author wy
     * @date 2018/10/30 16:11
     */
    private void init() {
        jump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ComponentName componentName = new ComponentName(IndexActivity.this,MainActivity.class);
                Intent jumpIntent = new Intent();
                jumpIntent.setComponent(componentName);
                Bundle bundle = new Bundle();
                startActivity(jumpIntent);
            }
        });
    }

    private void initview() {
        jump = this.findViewById(R.id.button);
    }
}
