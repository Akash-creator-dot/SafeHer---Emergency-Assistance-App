package com.example.safeher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
        EditText username, password;
        Button signin;
        DBHelper firebaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            username = findViewById(R.id.username);
            password = findViewById(R.id.password);
            signin = findViewById(R.id.signin);
            firebaseHelper = new DBHelper(LoginActivity.this);

            signin.setOnClickListener(v -> {
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if (user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseHelper.signIn(user, pass, new DBHelper.OnSignInListener() {
                        @Override
                        public void onSignInSuccess() {
                            Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        }

                        @Override
                        public void onSignInFailed() {
                            Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });
        }
    }
