package com.example.gettoknowip.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.example.gettoknowip.MainActivity;
import com.example.gettoknowip.R;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        new Handler().postDelayed(() -> {
            //This method will be executed once the timer is over
            // Start your app main activity
            Intent i = new Intent(SplashScreenActivity.this, MainActivity.class);
            startActivity(i);
            // close this activity
            finish();
        }, 3000);

        animateImageView();
    }

    private void animateImageView() {
        ImageView imageViewSplashImage = findViewById(R.id.imageViewSplashImage);
        imageViewSplashImage.animate()
                .setDuration(2000)
                .rotationXBy(360f);
    }
}