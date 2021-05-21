package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends ParentActivity {

    private EditText emaillogin;
    private EditText passwordlog;
    private Button loginbtn;
    private ProgressBar progress;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle(R.string.login);

        auth = FirebaseAuth.getInstance();
        emaillogin = findViewById(R.id.emaillogin);
        passwordlog = findViewById(R.id.passwordlog);
        loginbtn = findViewById(R.id.loginbtn);
        progress = findViewById(R.id.progress);

    }

    public void openForgetPasswordActivity(View view) {
        Intent i = new Intent(this, ForgetPassword.class);
        startActivity(i);
    }

    public void openSignupActivity(View view) {
        Intent i = new Intent(this, SignUp.class);
        startActivity(i);
        finish();
    }

    public void login(View view) {
        String emailText = emaillogin.getText().toString();
        String passwordText = passwordlog.getText().toString();

        if (emailText.isEmpty() || passwordText.isEmpty())
            Toast.makeText(this, R.string.missingfields, Toast.LENGTH_SHORT).show();
        else
            loginUserToFirebase(emailText, passwordText);
    }

    private void loginUserToFirebase(String email, String password) {
        loginbtn.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        loginbtn.setVisibility(View.VISIBLE);
                        progress.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            //intent to home activity
                            Helper.USER_ID = task.getResult().getUser().getUid();
                            Intent i = new Intent(Login.this, Home.class);
                            startActivity(i);
                            finish();
                        } else
                            Toast.makeText(Login.this, R.string.wrongfields, Toast.LENGTH_SHORT).show();
                    }
                });
    }

}

