package com.example.madjava;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AndroidServices extends AppCompatActivity {
    Button playBtn, pauseBtn, stopBtn;
    TextView statusText;
    String status = "STOPPED";

    private BroadcastReceiver mediaStatusReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            status = intent.getStringExtra(MediaService.MEDIA_STATUS);
            statusText.setText("Status: "+status);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_android_services);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        playBtn = findViewById(R.id.play_btn);
        pauseBtn = findViewById(R.id.pause_btn);
        stopBtn = findViewById(R.id.stop_btn);
        statusText = findViewById(R.id.status_text);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AndroidServices.this, MediaService.class);
                intent.setAction(MediaService.ACTION_PLAY);
                startService(intent);
            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AndroidServices.this, MediaService.class);
                intent.setAction(MediaService.ACTION_PAUSE);
                startService(intent);
            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AndroidServices.this, MediaService.class);
                intent.setAction(MediaService.ACTION_STOP);
                startService(intent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        statusText.setText("Status: "+MediaService.CURRENT_STATUS);
        registerReceiver(mediaStatusReceiver, new IntentFilter(MediaService.BROADCAST_ACTION), RECEIVER_NOT_EXPORTED);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mediaStatusReceiver);
    }
}