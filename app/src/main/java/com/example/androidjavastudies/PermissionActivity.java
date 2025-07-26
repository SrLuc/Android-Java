package com.example.androidjavastudies;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class PermissionActivity extends AppCompatActivity {

    private TextView msg;
    private Button btnProxima;
    private Button btnVoltar;
    private Button pedirPermissaoCamera;
    private int CAMERA_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        btnVoltar = findViewById(R.id.btnAnterior);
        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(PermissionActivity.this, SegundaActivity.class);
            startActivity(intent);
        });

        pedirPermissaoCamera = findViewById(R.id.btnPermissao);
        pedirPermissaoCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "Solicitando permissão...", Toast.LENGTH_SHORT).show();
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE
                );
                Toast.makeText(this, "Permissão Concedida", Toast.LENGTH_SHORT).show();
            }
        });


    }
}