package com.example.madjava;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        super.onStop();
        Toast.makeText(this, "this is onStop Function", Toast.LENGTH_SHORT).show();
    }

    protected void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "this is onDestroy Function", Toast.LENGTH_SHORT).show();
    }

    protected void onRestart()
    {
        super.onRestart();
        Toast.makeText(this, "this is onRestart Function", Toast.LENGTH_SHORT).show();
    }
}

