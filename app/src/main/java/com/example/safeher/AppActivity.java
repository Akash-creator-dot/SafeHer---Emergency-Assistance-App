package com.example.safeher;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

    public class AppActivity extends AppCompatActivity {
        EditText user, pass, number, name;
        TextView loginRedirect;
        Button signup;
        DBHelper firebaseHelper;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_app);

            user = findViewById(R.id.user);
            pass = findViewById(R.id.pass);
            number = findViewById(R.id.number);
            name = findViewById(R.id.name);
            signup = findViewById(R.id.signup);
            loginRedirect = findViewById(R.id.login_redirect);

            firebaseHelper = new DBHelper(AppActivity.this);

            signup.setOnClickListener(v -> {
                String us = user.getText().toString();
                String ps = pass.getText().toString();

                if (us.isEmpty() || ps.isEmpty()) {
                    Toast.makeText(AppActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                } else {
                    firebaseHelper.checkUserExists(us, exists -> {
                        if (!exists) {
                            firebaseHelper.insertData(us, ps);
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(AppActivity.this, "User already exists", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            });

            loginRedirect.setOnClickListener(v -> {
                Intent loginIntent = new Intent(AppActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            });
        }
    }