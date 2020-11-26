package com.example.android.populartvapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroCustomLayoutFragment;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import static com.github.appintro.AppIntroPageTransformerType.*;

public class IntroActivity extends AppIntro {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addSlide(AppIntroFragment.newInstance(
                "Welcome",
                "Aplikasi List Popular TV Series",
                R.drawable.intro1,
                Color.WHITE,
                this.getResources().getColor(R.color.blue),
                this.getResources().getColor(R.color.blue)
        ));
        addSlide(AppIntroFragment.newInstance(
                "Deskripsi",
                "Informasi dari berbagai TV Series terpopular",
                R.drawable.intro2,
                Color.WHITE,
                this.getResources().getColor(R.color.blue),
                this.getResources().getColor(R.color.blue)
        ));
        setNextArrowColor(this.getResources().getColor(R.color.blue));
        setColorDoneText(this.getResources().getColor(R.color.blue));
        setColorSkipButton(this.getResources().getColor(R.color.blue));
        setIndicatorColor(this.getResources().getColor(R.color.blue), this.getResources().getColor(R.color.dark_blue));
        setTransformer((AppIntroPageTransformerType)Flow.INSTANCE);
    }

    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Decide what to do when the user clicks on "Skip"
        changePrefFirstRun();
        finish();
    }

    public void onDonePressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        // Decide what to do when the user clicks on "Skip"
        changePrefFirstRun();
        finish();
    }

    public void changePrefFirstRun() {
        SharedPreferences prefs = getSharedPreferences("prefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart", false);
        editor.apply();
    }
}