package com.faizurazadri.pustakaku.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.faizurazadri.pustakaku.R;
import com.faizurazadri.pustakaku.ui.home.HomeActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getSupportActionBar()!=null){
            getSupportActionBar().hide();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Thread splash = new Thread(){
            @Override
            public void run() {
                super.run();

                try {
                    sleep(3000);
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    finish();
                }
            }
        };
        splash.start();
    }
}