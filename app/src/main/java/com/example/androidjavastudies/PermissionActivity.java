package com.example.androidjavastudies;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.SimpleDateFormat;
import java.util.Date;

public class PermissionActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST_CODE = 100;
    private Button btnVoltar;
    private Button pedirPermissaoCamera;
    private ImageView fotoTirada;
    private Uri fotoUri;

    private ActivityResultLauncher<Uri> cameraLauncher;

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
        pedirPermissaoCamera = findViewById(R.id.btnPermissao);
        fotoTirada = findViewById(R.id.imageViewFoto);

        // Launcher moderno para tirar foto direto no URI da galeria
        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.TakePicture(),
                isSuccess -> {
                    if (isSuccess) {
                        fotoTirada.setImageURI(fotoUri);
                        Toast.makeText(this, "Foto salva na galeria!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(this, "Falha ao tirar foto", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        pedirPermissaoCamera.setOnClickListener(v -> {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                abrirCameraESalvarNaGaleria();
            } else {
                ActivityCompat.requestPermissions(
                        this,
                        new String[]{Manifest.permission.CAMERA},
                        CAMERA_REQUEST_CODE
                );
            }
        });

        btnVoltar.setOnClickListener(v -> {
            Intent intent = new Intent(PermissionActivity.this, SegundaActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                abrirCameraESalvarNaGaleria();
            } else {
                Toast.makeText(this, "Permissão Negada!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void abrirCameraESalvarNaGaleria() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String nomeArquivo = "IMG_" + timeStamp + ".jpg";

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.DISPLAY_NAME, nomeArquivo);
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        values.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Camera");

        fotoUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

        if (fotoUri != null) {
            cameraLauncher.launch(fotoUri);
        } else {
            Toast.makeText(this, "Erro ao criar URI para salvar imagem", Toast.LENGTH_SHORT).show();
        }
    }
}
