package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Doodle;

import java.io.ByteArrayOutputStream;

public class AddDoodleActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btn_save;
    private Button btn_open;
    private TouchEventView drawing_pad;
    private EditText et_title;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;
    private float windowWidth;
    private float windowHeight;
    private Uri ImageUri;
    private Bitmap alteredImage;
    private Matrix matrix;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_doodle);

        et_title = findViewById(R.id.et_title);
        btn_save = (Button) findViewById(R.id.btn_save);
        drawing_pad = (TouchEventView) findViewById(R.id.drawing_pad);



        btn_save.setOnClickListener(this);

        DisplayMetrics currentDisplay = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(currentDisplay);
        windowHeight = currentDisplay.heightPixels * 2;
        windowWidth = currentDisplay.widthPixels * 2;

        WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        final Display display2 = windowManager.getDefaultDisplay();
        Point outPoint = new Point();

        display2.getSize(outPoint);

        int width = 0;
        int height = 0;

        if (outPoint.y > outPoint.x) {
            height = outPoint.y;
            width = outPoint.x;
        } else {
            height = outPoint.x;
            width = outPoint.y;
        }

        bitmap = Bitmap.createBitmap(width,
                (height),
                Bitmap.Config.ARGB_8888);

        canvas = new Canvas(bitmap);
        paint = new Paint();
    }

    @Override
    public void onClick(View v) {

        Intent OldIntent = getIntent();
        String taskId = OldIntent.getStringExtra("TaskId");

        SessionManage sessionManage = new SessionManage(getApplicationContext());

        switch ((v.getId())){
            case R.id.btn_save:
                StorageReference storageReference = FirebaseStorage.getInstance().getReference("images/").child(sessionManage.getSession() + "/").child("doodles/").child(System.currentTimeMillis() + ".jpg");


                drawing_pad.setDrawingCacheEnabled(true);
                drawing_pad.buildDrawingCache();
                alteredImage = drawing_pad.getDrawingCache();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                alteredImage.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = storageReference.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                        Task<Uri> downloadUrl = storageReference.getDownloadUrl();
                        downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Log.d("here", uri.toString());
                                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                                DatabaseReference reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(taskId).child("doodles");

                                Log.d("here", reference.toString());
                                String id = reference.push().getKey();

                                Log.d("here", id);
                                String tempTitle = et_title.getText().toString();
                                if(tempTitle.equals("")) {
                                    tempTitle = "Untitled";
                                }
                                Doodle doodle = new Doodle(tempTitle, id, uri.toString());
                                reference.child(id).setValue(doodle);
                                finish();
                            }
                        });



                    }
                });
                break;
        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
