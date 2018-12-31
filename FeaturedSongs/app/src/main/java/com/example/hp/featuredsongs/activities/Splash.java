package com.example.hp.featuredsongs.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.hp.featuredsongs.activities.FeaturedSong;
import com.example.hp.featuredsongs.R;

public class Splash extends AppCompatActivity {
    private static int SPLASH_TIMEOUT=4000; // 4 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                ImageView tv = (ImageView)findViewById(R.id.ryhthmhive);
                Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideup);
                tv.startAnimation(animation1);
                Intent in = new Intent(Splash.this,com.example.hp.featuredsongs.activities.MainActivity.class);
                startActivity(in);
                finish();
            }
        },SPLASH_TIMEOUT);
    }
}
