package com.nicolas.canivete_suico;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import java.io.ByteArrayOutputStream;


public class CameraActivity extends AppCompatActivity {
    Button openCameraButton, viewPhotosButton, deleteAllPicturesButton;
    ImageView photoPreview;

    String base64Image = "";

    ActivityResultLauncher<Intent> cameraLauncher;

    DatabaseManager databaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.picture);
        initComponents();

        cameraLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Bitmap photo = (Bitmap) result.getData().getExtras().get("data");
                        photoPreview.setImageBitmap(photo);
                        base64Image = encodeImageToBase64(photo);

                        Picture picture = new Picture(base64Image);
                        databaseManager.insertPicture(picture);

                        Toast.makeText(this, "Imagem salva em Base64!", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void initComponents() {
        databaseManager = new DatabaseManager(this);

        photoPreview = findViewById(R.id.photo_preview);

        openCameraButton = findViewById(R.id.open_camera_button);
        openCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent();
                    intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                    cameraLauncher.launch(intent);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });

        viewPhotosButton = findViewById(R.id.view_photos_button);
        viewPhotosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent picturesIntent = new Intent(CameraActivity.this, PicturesActivity.class);
                startActivity(picturesIntent);
            }
        });

        deleteAllPicturesButton = findViewById(R.id.delete_all_pictures_button);
        deleteAllPicturesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                databaseManager.cleanData();
            }
        });
    }

    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos); // qualidade top
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.NO_WRAP);
    }

}
