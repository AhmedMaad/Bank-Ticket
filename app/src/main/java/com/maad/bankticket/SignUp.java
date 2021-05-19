package com.maad.bankticket;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class SignUp extends AppCompatActivity {

    private EditText fname;
    private EditText lname;
    private EditText email;
    private EditText password;
    private EditText confirmpassword;
    private ProgressBar progress;
    private Button signupbtn;
    private String fNameText;
    private String lNameText;
    private String emailText;
    private String passwordText;
    private String confirmPasswordText;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Making instance from firebase
        auth = FirebaseAuth.getInstance();
        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.confirmpassword);
        progress = findViewById(R.id.progress);
        signupbtn = findViewById(R.id.signupbtn);

    }

    public void signUpUser(View view) {
        fNameText = fname.getText().toString();
        lNameText = lname.getText().toString();
        emailText = email.getText().toString();
        passwordText = password.getText().toString();
        confirmPasswordText = confirmpassword.getText().toString();
        if (!passwordText.equals(confirmPasswordText))
            Toast.makeText(this, R.string.passwords_not_match, Toast.LENGTH_SHORT).show();
        else
            addUserToFirebase();
    }

    public void openLoginActivity(View view) {
        Intent i = new Intent(this, Login.class);
        startActivity(i);
        finish();
    }

    //Adding new user to firebase using email and password
    private void addUserToFirebase() {
        signupbtn.setVisibility(View.GONE);
        progress.setVisibility(View.VISIBLE);
        auth.createUserWithEmailAndPassword(emailText, passwordText)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Helper.USER_ID = task.getResult().getUser().getUid();
                            addUserDataToFirestore();
                        } else {
                            signupbtn.setVisibility(View.VISIBLE);
                            progress.setVisibility(View.GONE);
                            Toast.makeText(SignUp.this, R.string.failedadduser, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void addUserDataToFirestore() {
        Client client = new Client(fNameText, lNameText, emailText, passwordText);
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db
                .collection("clients")
                .document(Helper.USER_ID)
                .set(client)
                .addOnSuccessListener(documentReference -> {
                    progress.setVisibility(View.GONE);
                    Toast.makeText(SignUp.this, R.string.useradded, Toast.LENGTH_SHORT).show();
                    //navigate to main activity
                    Intent i = new Intent(this, Home.class);
                    startActivity(i);
                    finish();
                });
    }

}