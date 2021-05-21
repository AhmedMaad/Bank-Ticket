package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class Settings extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting);
        setTitle(R.string.settings);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
        finishAffinity();
    }

    public void openChangeLanguage(View view) {
        Intent i = new Intent(this, Language.class);
        startActivity(i);
    }
}