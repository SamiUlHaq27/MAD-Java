package com.example.madjava;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Toast;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class MainActivity extends AppCompatActivity {
    Button btn1, btn2, btn3;
    ConstraintLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        btn1 = findViewById(R.id.btn1);
        btn2 = findViewById(R.id.btn2);
        btn3 = findViewById(R.id.btn3);
        layout = findViewById(R.id.main);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 layout.setBackgroundColor(Color.parseColor("Green"));
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.parseColor("Red"));
            }
        });
        btn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                layout.setBackgroundColor(Color.parseColor("Blue"));
            }
        });
        Toast.makeText(this, "this is onCreate Function", Toast.LENGTH_SHORT).show();
    }

    protected void onStart()
    {
        super.onStart();
        Toast.makeText(this, "this is onStart Function", Toast.LENGTH_SHORT).show();

    }

    protected void onResume()
    {
        super.onResume();
        Toast.makeText(this, "this is onResume Function", Toast.LENGTH_SHORT).show();
    }

    protected void onPause()
    {
        super.onPause();
        Toast.makeText(this, "this is onPause Function", Toast.LENGTH_SHORT).show();
    }

    protected void onStop()
    {
        Toast.makeText(this, "this is onStop Function", Toast.LENGTH_SHORT).show();
        super.onStop();
    }

    protected void onDestroy()
    {
        Toast.makeText(this, "this is onDestroy Function", Toast.LENGTH_SHORT).show();
        super.onDestroy();
    }
}