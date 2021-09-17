package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.management.SessionManage;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Doodle;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DoodlesAdapter extends RecyclerView.Adapter<DoodlesAdapter.DoodlesViewHolder> {

    private ArrayList<Doodle> doodleArrayList;
    private Context context;
    private String TaskId;

    public DoodlesAdapter(Context context, String TaskId, ArrayList<Doodle> doodleArrayList) {
        this.doodleArrayList = doodleArrayList;
        this.TaskId = TaskId;
        this.context = context;
    }

    @Override
    public DoodlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_doodles, parent, false);

        DoodlesAdapter.DoodlesViewHolder doodlesViewHolder = new DoodlesAdapter.DoodlesViewHolder(view);

        return doodlesViewHolder;
    }

    @Override
    public void onBindViewHolder(DoodlesAdapter.DoodlesViewHolder holder, int position) {
        holder.tv_title.setText(doodleArrayList.get(position).getTitle());
        Picasso.get().load(doodleArrayList.get(position).getDoodleurl()).into(holder.iv_doodle);


        holder.fab_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // session
                SessionManage sessionManage = new SessionManage(context);
                // to db
                FirebaseDatabase rootNode = FirebaseDatabase.getInstance();
                DatabaseReference reference = rootNode.getReference("tasks").child(sessionManage.getSession()).child(TaskId).child("doodles").child(doodleArrayList.get(position).getDoodleid());
                //delete
                reference.removeValue();
                FirebaseStorage firebaseDatabase = FirebaseStorage.getInstance();
                StorageReference storageReference = firebaseDatabase.getReferenceFromUrl(doodleArrayList.get(position).getDoodleurl());
                storageReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(v.getContext(), "Doodle Removed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    @Override
    public int getItemCount() {
        return doodleArrayList.size();
    }

    protected class DoodlesViewHolder extends RecyclerView.ViewHolder{

        TextView tv_title;
        ImageView iv_doodle;
        FloatingActionButton fab_delete;
        public DoodlesViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_doodle = itemView.findViewById(R.id.iv_doodle);
            fab_delete = itemView.findViewById(R.id.fab_delete);
        }
    }
}
