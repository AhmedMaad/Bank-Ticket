package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class Splash extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        setTitle(R.string.app_name);

        new Handler()
                .postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moveToLogin();
                    }
                }, 5000);
    }

    public void moveToLogin() {
        Intent intent = new Intent(this, SignUp.class);
        startActivity(intent);
        finish();
    }

}