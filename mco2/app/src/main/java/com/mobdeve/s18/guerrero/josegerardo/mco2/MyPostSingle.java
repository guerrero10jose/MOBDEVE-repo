package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;

import de.hdodenhof.circleimageview.CircleImageView;

public class MyPostSingle extends AppCompatActivity {

    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private String keys;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_post);

        EditText caption;
        Button btn_save;

        caption = findViewById(R.id.et_cap);
        btn_save = findViewById(R.id.btn_save);

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle extras = getIntent().getExtras();

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("posts");


                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for(DataSnapshot snapshot : dataSnapshot.getChildren()) {

                            if(snapshot.child("postid").getValue().toString().equals(extras.getString("postid"))) {

                                keys = snapshot.getKey();
                                //Toast.makeText(MyPostSingle.this, keys, Toast.LENGTH_SHORT).show();

                                reference.child(keys).child("caption").setValue(caption.getText().toString());
                                break;
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {

                    }
                });

                finish();
            }
        });
    }


}
