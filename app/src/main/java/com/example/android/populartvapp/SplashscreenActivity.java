package com.example.android.populartvapp;

import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class SplashscreenActivity extends AppCompatActivity {

    private int waktu_loading=2000; //2000 = 2 detik

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splashscreen);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //setelah loading maka akan langsung berpindah ke home activity
                Intent IntroActivity=new Intent(SplashscreenActivity.this, IntroActivity.class);
                startActivity(IntroActivity);
                finish();
            }
        },waktu_loading);
    }
}