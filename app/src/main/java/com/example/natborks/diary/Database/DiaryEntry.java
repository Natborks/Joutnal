package com.example.natborks.diary.Database;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "Entry")
public class DiaryEntry {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String body;
    //private Date updated_at;
    private Date created_at;

    @Ignore
    public DiaryEntry(String title, String body, Date created_at){
        this.title = title;
        this.body = body;
        this.created_at = created_at;
    }

    public DiaryEntry(int id, String title, String body,Date created_at){
        this.id = id;
        this.title = title;
        this.body = body;
        this.created_at = created_at;
    }

    public int getId(){return id;}

    public void setId(int id){this.id = id;}

    public String getTitle(){ return title;}

    public void setTitle(String title){this.title = title;}

    public String getBody(){return body;}

    public void setBody(String body){this.body = body;}

    public Date getCreated_at(){return created_at;}

    public void setCreated_at(Date date){this.created_at = date;}

    /*public Date getUpdated_at(){return updated_at;}

    public void setUpdated_at(Date updated_at){this.updated_at = updated_at;}*/
}
