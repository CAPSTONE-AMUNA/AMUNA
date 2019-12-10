package com.example.amuna;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Splash1Activity extends AppCompatActivity {

    private ImageView logo_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash1);

        logo_image = findViewById(R.id.logo_image);
        logo_image.setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);

        Handler hd = new Handler();
        hd.postDelayed(new Splash1Activity.splashhandler(), 3000);
    }

    private class splashhandler implements Runnable {
        public void run() {
            startActivity(new Intent(getApplication(), LoginActivity.class));
        }
    }



    @Override
    public void onBackPressed() {

    }
}