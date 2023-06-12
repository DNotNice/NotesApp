package com.example.notesapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

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
                Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.long_press_options);
                TextView edit= dialog.findViewById(R.id.NoteEdit);
                TextView Delete = dialog.findViewById(R.id.NoteDelete);
                dialog.show();
                edit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialog updateDialog = new Dialog(context);
                        updateDialog.setContentView(R.layout.update_note_layout);
                        EditText updateTitle , updateContent ;
                        Button updateButton;
                        updateTitle  = updateDialog.findViewById(R.id.updateTitle);
                        updateContent = updateDialog.findViewById(R.id.updateContent);
                        updateButton = updateDialog.findViewById(R.id.updateAdd);
                        Note note = arrNotes.get(holder.getAdapterPosition());
                        updateTitle.setText(note.getTitle());
                        updateContent.setText(note.getContent());
                        updateButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                databaseHelper.noteDao().update(new Note(arrNotes.get(holder.getAdapterPosition()).getId(),
                                         updateTitle.getText().toString(),
                                        updateContent.getText().toString()));
                                updateDialog.dismiss();
                                ((MainActivity)context).showNotes();

                            }
                        });
                        dialog.dismiss();
                        updateDialog.show();

                    }
                });
                Delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        deleteItem(holder.getAdapterPosition());
                    }
                });


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
