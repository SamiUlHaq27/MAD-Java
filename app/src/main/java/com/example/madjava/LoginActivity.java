package com.example.madjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class LoginActivity extends AppCompatActivity {
    Button loginBtn, registerBtn;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        loginBtn = findViewById(R.id.loginbtn);
        registerBtn = findViewById(R.id.registerbtn);
        user = findViewById(R.id.userInput);
        pass = findViewById(R.id.passInput);

        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        Boolean isLoggedIn;
        isLoggedIn = sp.getBoolean("IsLoggedIn", false);
        if (isLoggedIn) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("username", sp.getString("username:", "no-name"));
            startActivity(intent);
            finish();
        }
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u1 = user.getText().toString();
                String p1 = pass.getText().toString();
                String username = sp.getString("username:", "");
                String password = sp.getString("password:", "");
                if (u1.equals(username) && p1.equals(password)) {
                    SharedPreferences.Editor ed = sp.edit();
                    ed.putBoolean("IsLoggedIn", true);
                    ed.apply();
                    Toast.makeText(LoginActivity.this, "login Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.putExtra("username", u1);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Invalid User name or password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}