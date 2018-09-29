package kr.ac.shinhan.travelpartner;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import kr.ac.shinhan.travelpartner.Firebase.GoogleSignInActivity;

public class SplashActivity extends AppCompatActivity {
    private Handler handler;

    Runnable runnable = new Runnable() {
        public void run() {
            Intent intent = new Intent(SplashActivity.this, GoogleSignInActivity.class);
            startActivity(intent);
            finish();
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        TextView mTitleText = findViewById(R.id.logo);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().setStatusBarColor(Color.parseColor("#FAD956"));
        }

        init();

        handler.postDelayed(runnable, 1000);
    }

    public void init() {
        handler = new Handler();
    }

    public void onBackPressed(){
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
