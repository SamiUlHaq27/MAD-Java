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

public class SignupActivity extends AppCompatActivity {

    Button loginBtn, registerBtn;
    EditText user, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_signup);
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
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String u1 = user.getText().toString();
                String p1 = pass.getText().toString();
                if (u1.isEmpty() || p1.isEmpty()) {
                    Toast.makeText(SignupActivity.this, "Please fil the above fields", Toast.LENGTH_SHORT).show();
                } else {
                    SharedPreferences.Editor edit = sp.edit();
                    edit.putString("username:", u1);
                    edit.putString("password:", p1);
                    edit.apply();
                    Toast.makeText(SignupActivity.this, "User has been registered", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(SignupActivity.this, LoginActivity.class));
                }
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }
}