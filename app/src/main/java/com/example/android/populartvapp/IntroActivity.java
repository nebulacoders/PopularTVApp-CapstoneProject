package com.example.android.populartvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance(
                "Welcome",
                "This is the first slide",
                R.drawable.ic_tv
        ));
        addSlide(AppIntroFragment.newInstance(
                "Let's get started!",
                "I won't annoy you more :)",
                R.drawable.ic_tv
        ));

    }

    public void onSkipPressed(Fragment currentFragment){
        super.onSkipPressed(currentFragment);
        // Decide what to do when the user clicks on "Skip"
        changePrefFirstRun();
        finish();
    }

    public void onDonePressed(Fragment currentFragment){
        super.onSkipPressed(currentFragment);
        // Decide what to do when the user clicks on "Skip"
        changePrefFirstRun();
        finish();
    }

    public void changePrefFirstRun(){
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}