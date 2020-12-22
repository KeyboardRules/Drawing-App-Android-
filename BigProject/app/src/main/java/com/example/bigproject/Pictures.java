package com.example.bigproject;

import android.graphics.Bitmap;
import android.media.Image;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Pictures implements Comparable<Pictures>, Serializable {
    private String id;
    private String Name;
    private Bitmap bm;
    private String dateTime;
    public Pictures(String id,String Name,Bitmap bm,String dateTime){
        this.id=id;
        this.Name=Name;
        this.bm=bm;
        this.dateTime=dateTime;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setBm(Bitmap bm) {
        this.bm=bm;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return Name;
    }

    public Bitmap getBm() {
        return bm;
    }

    public String getDateTime() {
        return dateTime;
    }

    public Date getdate()  {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try{
            Date Datetime=sdf.parse(dateTime);
            return Datetime;
        }
        catch (ParseException e) {
            return null;
        }
    }
    @Override
    public int compareTo(Pictures o) {
        if(getdate().compareTo(o.getdate())==0) return 0;
        else if(getdate().compareTo(o.getdate())<0) return -1;
        else return 1;
    }
}
