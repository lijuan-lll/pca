package com.example.pca_demo529_2;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class TestActivity extends AppCompatActivity {
    private final static String TAG = "TestActivity";

    private TextView textView;
    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        textView = findViewById(R.id.title_2);
        imageView = findViewById(R.id.image_result);

        Intent intent = getIntent();
        String resultImage_filename_all =intent.getStringExtra("resultImage_filename_all");
        int K = intent.getIntExtra("K_value",0);

        textView.setText("K="+String.valueOf(K));


        if (resultImage_filename_all != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(resultImage_filename_all);
            imageView.setImageBitmap(bitmap);
        }
        else {
            Toast.makeText(this, "failed to get image", Toast.LENGTH_SHORT).show();
        }



    }

}
