package com.example.madjava;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class ActivityLifecycle extends AppCompatActivity {

    Spinner sp;
    ArrayList<String> datalist;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_lifecycle);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sp = findViewById(R.id.sp1);

        datalist = new ArrayList<>();
        datalist.add("Activity LifeCycle");
        datalist.add("Layouts");
        datalist.add("Intent");
        datalist.add("Notification");
        ArrayAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,datalist);
        sp.setAdapter(adapter);

    }
}