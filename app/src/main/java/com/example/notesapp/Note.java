package com.example.notesapp;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {
    @PrimaryKey(autoGenerate = true)
    private  int id ;
    @ColumnInfo(name = "title")
    private  String title;
    @ColumnInfo(name = "content" )
    private String content;

    public Note(int id , String title , String content){
        this.id = id ;
        this.title = title;
        this.content = content;
    }
    @Ignore
    public Note(String title , String content){
        this.title = title;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
