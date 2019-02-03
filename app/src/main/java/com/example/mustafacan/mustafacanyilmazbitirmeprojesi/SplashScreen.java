package com.example.mustafacan.mustafacanyilmazbitirmeprojesi;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {

    boolean shouldOpenMainActivity = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                if (shouldOpenMainActivity) {
                    Intent i = new Intent(SplashScreen.this, LoginAndRegister.class);
                    startActivity(i);
                    finish();
                }
            }
        }, 3500);

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        shouldOpenMainActivity = false;
    }

}