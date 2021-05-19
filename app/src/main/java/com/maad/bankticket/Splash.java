package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveToLogin();
                    }
                }, 500);
    }

    public void moveToLogin() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

}