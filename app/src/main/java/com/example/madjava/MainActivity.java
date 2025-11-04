package com.example.madjava;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    ConstraintLayout layout;
    Switch sw;
    Boolean isDarkMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        SharedPreferences sp = getSharedPreferences("login", MODE_PRIVATE);
        isDarkMode = sp.getBoolean("isDarkMode", false);
        if(isDarkMode){
            setTheme(R.style.DarkTheme);
        } else {
            setTheme(R.style.LightTheme);
        }
        setContentView(R.layout.activity_main);

        layout = findViewById(R.id.main);
        if(isDarkMode){
            layout.setBackgroundColor(Color.parseColor("black"));
        }

        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        sw = findViewById(R.id.switch1);
        sw.setChecked(isDarkMode);

        layout = findViewById(R.id.main);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.parseColor("Green"));
            }
        });

        btn2.setOnClickListener(new View.OnClickListener() {
            @Override()
            public void onClick(View view) {
                startActivity();
            }
        });

        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("IsLoggedIn", false);
                edit.apply();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });

        sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(@NonNull CompoundButton buttonView, boolean isChecked) {
                isDarkMode = isChecked;
                SharedPreferences.Editor edit = sp.edit();
                edit.putBoolean("isDarkMode", isChecked);
                edit.apply();
                recreate();
            }
        });
       
        Toast.makeText(this, "this is onCreate Function", Toast.LENGTH_SHORT).show();
    }

    private void startActivity() {
        Intent myintent = new Intent(this, MainActivity2.class);
        startActivity(myintent);
    }


    protected void onStart() {
        super.onStart();
        Toast.makeText(this, "this is onStart Function", Toast.LENGTH_SHORT).show();

    }

    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "this is onResume Function", Toast.LENGTH_SHORT).show();
    }

    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "this is onPause Function", Toast.LENGTH_SHORT).show();
    }

    protected void onStop() {
        Toast.makeText(this, "this is onStop Function", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    protected void onDestroy() {
        Toast.makeText(this, "this is onDestroy Function", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}