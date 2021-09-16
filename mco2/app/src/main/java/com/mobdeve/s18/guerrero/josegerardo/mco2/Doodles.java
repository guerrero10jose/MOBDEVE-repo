package com.mobdeve.s18.guerrero.josegerardo.mco2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.mobdeve.s18.guerrero.josegerardo.mco2.adapter.DoodlesAdapter;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Doodle;

import java.util.ArrayList;

public class Doodles extends AppCompatActivity {
    private Button btn_add;
    private DoodlesAdapter doodlesAdapter;
    private FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.doodles);

        Intent OldIntent = getIntent();
        String TaskId = OldIntent.getStringExtra("TaskId");

        ArrayList<Doodle> doodleArrayList = new ArrayList<>();


        // session
        SessionManage sessionManage = new SessionManage(getApplicationContext());
        // firebase
        rootNode = FirebaseDatabase.getInstance();
        reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(TaskId).child("doodles");
        Query doodleQuery = reference.orderByChild("title");
        doodleQuery.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                doodleArrayList.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    if(snapshot.child("title").exists())
                    {
                        String tempTitle = snapshot.child("title").getValue().toString();
                        String tempDoodleId = snapshot.child("doodleid").getValue().toString();
                        String tempDoodleUrl = snapshot.child("doodleurl").getValue().toString();


                        Doodle temp = new Doodle(tempTitle, tempDoodleId, tempDoodleUrl);
                        doodleArrayList.add(temp);
                    }
                }
                doodlesAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });

        btn_add = findViewById(R.id.btn_add);

        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddDoodleActivity.class);
                intent.putExtra("TaskId", TaskId);
                startActivity(intent);
            }
        });

        recyclerView = findViewById(R.id.rv_doodlelist);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        doodlesAdapter = new DoodlesAdapter(getApplicationContext(), TaskId, doodleArrayList);
        recyclerView.setAdapter(doodlesAdapter);

    }
}
