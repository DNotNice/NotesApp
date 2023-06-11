package com.example.notesapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button btnCreate;
    FloatingActionButton fabAdd;
    RecyclerView recyclerView;
    DatabaseHelper databaseHelper;
    LinearLayout llNoNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initVar();
        showNotes();


        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(MainActivity.this);
                    dialog.setContentView(R.layout.add_note_layout);
                EditText edtTitle , edtContent;
                Button btnAdd ;
                edtTitle = dialog.findViewById(R.id.edTitle);
                edtContent = dialog.findViewById(R.id.edContent);
                btnAdd = dialog.findViewById(R.id.btnAdd);
                btnAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String titleM = edtTitle.getText().toString();
                        String contentM = edtContent.getText().toString();

                        if(!contentM.equals("")){
                            databaseHelper.noteDao().addNote(new Note(titleM, contentM));
                            showNotes();
                            dialog.dismiss();


                        }
                        else Toast.makeText(MainActivity.this ,"Please enter Something", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });
        btnCreate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                fabAdd.performClick();
            }
        });
    }

    private void showNotes() {
        ArrayList<Note> arrNote = (ArrayList<Note>) databaseHelper.noteDao().getNotes();
        if(arrNote.size() > 0){
            recyclerView.setVisibility(View.VISIBLE);
            llNoNotes.setVisibility(View.GONE);
            recyclerView.setAdapter(new RecyclerNotesAdapter(this ,arrNote));

        }else{
            llNoNotes.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }

    private void initVar(){
        btnCreate = findViewById(R.id.btnCreate);
        fabAdd = findViewById(R.id.fabAdd);
        recyclerView = findViewById(R.id.recyclerNotes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        databaseHelper =  DatabaseHelper.getInstance(this);
        llNoNotes = findViewById(R.id.llNoNotes);
    }
}