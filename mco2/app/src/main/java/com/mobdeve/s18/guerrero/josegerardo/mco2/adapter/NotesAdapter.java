package com.mobdeve.s18.guerrero.josegerardo.mco2.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobdeve.s18.guerrero.josegerardo.mco2.R;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Notes;
import com.mobdeve.s18.guerrero.josegerardo.mco2.models.Task;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private Notes notesArrayList;
    private Context context;

    public NotesAdapter(Notes notesArrayList, Context context) {
        this.notesArrayList = notesArrayList;
        this.context = context;
    }

    @Override
    public NotesAdapter.NotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notes, parent, false);
        NotesAdapter.NotesViewHolder notesViewHolder = new NotesAdapter.NotesViewHolder(view);
        return notesViewHolder;
    }

    @Override
    public void onBindViewHolder(NotesAdapter.NotesViewHolder holder, int position) {
        String note = notesArrayList.getNotesArrayList().get(position);
        holder.notes.setText("- " + note);
    }

    @Override
    public int getItemCount() {
        return notesArrayList.size();
    }

    protected class NotesViewHolder extends RecyclerView.ViewHolder {
        TextView notes;

        public NotesViewHolder(View itemView) {
            super(itemView);
            notes = itemView.findViewById(R.id.tv_notes);
        }

    }
}
