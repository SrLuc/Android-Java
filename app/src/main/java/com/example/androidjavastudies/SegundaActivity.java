package com.example.androidjavastudies;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SegundaActivity extends AppCompatActivity {

    private Button btnProxima;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_segunda);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        Button button = findViewById(R.id.prevPage);
        button.setOnClickListener(v -> {
            Intent intent = new Intent(SegundaActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnProxima = findViewById(R.id.NextPage);
        btnProxima.setOnClickListener(v -> {
            Intent intent = new Intent(SegundaActivity.this,PermissionActivity.class);
            startActivity(intent);
        });

    }
}