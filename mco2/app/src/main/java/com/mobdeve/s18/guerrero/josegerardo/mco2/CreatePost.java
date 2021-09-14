package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.ContentResolver;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Post;

import java.util.ArrayList;
import java.util.UUID;

public class CreatePost extends AppCompatActivity {

    private ArrayList<String> taskNames = new ArrayList<>();
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String selectedText;
    private Button btn_save, btn_share;
    private ImageView upload;
    private ActivityResultLauncher<Intent> results;
    private Uri ImageUri;
    private static String PassUri;
    private StorageReference storageReference;
    private EditText in_cap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.post_create);

        // initialize buttons
        btn_save = findViewById(R.id.btn_save);
        btn_share = findViewById(R.id.btn_share);
        upload = findViewById(R.id.upload);
        in_cap = findViewById(R.id.input_cap);

        results = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if(result.getResultCode() == RESULT_OK && result.getData() != null) {
                    ImageUri = result.getData().getData();
                    upload.setImageURI(ImageUri);
                }

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(ImageUri != null) {
                    //Toast.makeText(getApplicationContext(), PassUri, Toast.LENGTH_LONG).show();
                    //uploadimage();
                    uploadtoFirebase(ImageUri);
                    finish();
                }
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                results.launch(intent);
            }
        });

        // session needed to get username
        SessionManage sessionManage = new SessionManage(getApplicationContext());

        // retrieve tasks for dropdown from firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tasks").child(sessionManage.getSession());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                taskNames.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                    String string = new String(snapshot.child("task").getValue().toString());

                    taskNames.add(string);
                }

                Spinner spinner = findViewById(R.id.task_dropdown);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(CreatePost.this, android.R.layout.simple_list_item_1, taskNames);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                selectedText = String.valueOf(spinner.getSelectedItem());

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedText = parent.getItemAtPosition(position).toString();

                        //Toast.makeText(getApplicationContext(), selectedText, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }

    private void uploadimage() {

        // session needed to get username
        SessionManage sessionManage = new SessionManage(getApplicationContext());

        String username = sessionManage.getSession();

        storageReference = FirebaseStorage.getInstance().getReference("images/" + username + "/" + selectedText);

        storageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        PassUri = uri.toString();
                        Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadtoFirebase(Uri uri) {
        storageReference = FirebaseStorage.getInstance().getReference().child(System.currentTimeMillis() + "." + getFileExtension(uri));
                //("images/" + username + "/" + selectedText);

        storageReference.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                StorageMetadata pic = taskSnapshot.getMetadata();
                Task<Uri> downloadUrl = storageReference.getDownloadUrl();
                downloadUrl.addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        String caption = in_cap.getText().toString();
                        SessionManage sessionManage = new SessionManage(getApplicationContext());

                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("posts");

                        String id = reference.push().getKey();

                        Post post = new Post(uri.toString(), 0, 0, caption, selectedText, false,
                                sessionManage.getSession(), 0, id);

                        reference.child(id).setValue(post);

                        //Toast.makeText(getApplicationContext(), uri.toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(Exception e) {
                Toast.makeText(getApplicationContext(), "Failed!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private String getFileExtension(Uri mUri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

}
