package com.maad.bankticket;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

public class Language extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        setTitle(R.string.selectlang);
    }

    public void English(View view) {
        saveLanguage("en");
    }

    public void Arabic(View view) {
        saveLanguage("ar");
    }

    private void saveLanguage(String languageCode) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.close_app)
                .setPositiveButton(R.string.proceed, (dialog, which) -> {
                    SharedPreferences.Editor editor =
                            getSharedPreferences("settings", MODE_PRIVATE).edit();
                    editor.putString("code", languageCode);
                    editor.apply();
                    Intent i = new Intent(this, SignUp.class);
                    startActivity(i);
                    finishAffinity();
                })
                .setCancelable(false)
                .show();
    }

}