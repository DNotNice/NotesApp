package com.example.notesapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerNotesAdapter extends RecyclerView.Adapter<RecyclerNotesAdapter.ViewHolder> {
    Context context;
    ArrayList<Note> arrNotes = new ArrayList<>();
    DatabaseHelper databaseHelper;

    RecyclerNotesAdapter(Context context , ArrayList<Note> arrNotes , DatabaseHelper databaseHelper){
        this.context = context;
        this.arrNotes = arrNotes;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.notes_row ,parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtTile.setText(arrNotes.get(position).getTitle());
        holder.txtContent.setText(arrNotes.get(position).getContent());
        holder.llRow.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
            deleteItem(holder.getAdapterPosition());
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrNotes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView txtTile , txtContent;
        LinearLayout llRow ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTile = itemView.findViewById(R.id.txtTitle);
            txtContent =itemView.findViewById(R.id.txtContent);
            llRow = itemView.findViewById(R.id.llRow);


        }
    }
    public void deleteItem(int position){
        AlertDialog dialog = new AlertDialog.Builder(context)
                .setTitle("Delete")
                .setMessage("Are you sure , you want to delete")
                .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        databaseHelper.noteDao().deleteNote(new Note(arrNotes.get(position).getId(),
                                arrNotes.get(position).getTitle() , arrNotes.get(position).getContent()));
                        ((MainActivity)context).showNotes();
                    }
                })
                .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                }).show();

    }
}
