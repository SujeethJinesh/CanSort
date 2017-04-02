package com.example.admin.cansort;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.clarifai.api.ClarifaiClient;
import com.clarifai.api.RecognitionRequest;
import com.clarifai.api.RecognitionResult;
import com.clarifai.api.Tag;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    Button analyzeButton;
    ImageView destinationImageView;
    static final int CAM_REQUEST = 0;
    private final ClarifaiClient clarifaiClient = new ClarifaiClient(Credentials.CLIENT_ID,
            Credentials.CLIENT_SECRET);
    ArrayList<String> items;
    TextView outputTextView;

    Set<String> recyclables;
    Set<String> compostables;
    Set<String> landfill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        analyzeButton = (Button) findViewById(R.id.analyze_button);
        destinationImageView = (ImageView) findViewById(R.id.destination_image_view);
        outputTextView = (TextView) findViewById(R.id.output_text_view);

        recyclables = new HashSet<>(Arrays.asList("bottle", "plastic"));
        compostables = new HashSet<>(Arrays.asList("apple", "clementine", "orange", "fruit", "tangerine", "mandarin", "food"));
        landfill = new HashSet<>(Arrays.asList("phone", "wrapper"));

        items = new ArrayList<>();
        if (checkSelfPermission(Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.CAMERA},
                    CAM_REQUEST);
        }

        final Context context = this.getApplicationContext();
        analyzeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //File file = getFile();
                //cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, FileProvider.getUriForFile(context, getApplicationContext().getPackageName() + ".provider", file));
                startActivityForResult(cameraIntent, CAM_REQUEST);
            }
        });
    }

    private File getFile() {
        File folder = new File("sdcard/camera_app");

        if (!folder.exists()) {
            folder.mkdir();
        }
        File image_file = new File(folder, "cam_image.jpg");
        return image_file;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            destinationImageView.setImageBitmap(bitmap);

            // Run recognition on a background thread.
            new AsyncTask<Bitmap, Void, RecognitionResult>() {
                @Override
                protected RecognitionResult doInBackground(Bitmap... bitmaps) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmaps[0].compress(Bitmap.CompressFormat.JPEG, 90, stream);
                    byte[] byteArray = stream.toByteArray();
                    return clarifaiClient.recognize(new RecognitionRequest(byteArray).setModel("general-v1.3")).get(0);
                }

                @Override
                protected void onPostExecute(RecognitionResult result) {
                    String foundText = "Not yet identifiable!";
                    for (Tag tag : result.getTags()) {
                        if (recyclables.contains(tag.getName())) {
                            foundText = "Recycle!";
                        } else if (compostables.contains(tag.getName())) {
                            foundText = "Compost!";
                        } else if (landfill.contains(tag.getName())) {
                            foundText = "Trash!";
                        }
                    }
                    outputTextView.setText(foundText);
                }
            }.execute(bitmap);
        }
    }
}
