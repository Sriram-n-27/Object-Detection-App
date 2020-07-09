package com.sriram_n.pytorchandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;


public class MainActivity extends AppCompatActivity {

    int cameraRequestCode = 1;

    Classifier classifier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        classifier = new Classifier(Utils.assetFilePath(this, "mobilenet-v2.pt"));

        Button capture = findViewById(R.id.capture);

        capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                startActivityForResult(cameraIntent, cameraRequestCode);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == cameraRequestCode && resultCode == RESULT_OK) {

            Intent resultView = new Intent(this, Result.class);

            resultView.putExtra("imagedata", data.getExtras());

            Bitmap imageBitmap = (Bitmap) data.getExtras().get("data");

            String pred = classifier.predict(imageBitmap);
            resultView.putExtra("pred", pred);

            startActivity(resultView);

        }
    }
}