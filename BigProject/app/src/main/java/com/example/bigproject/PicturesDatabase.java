package com.example.bigproject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class PicturesDatabase extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Pictures_Manager";

    // Table name: Note.
    private static final String TABLE_NAME = "Pictures";

    private static final String PICTURE_ID ="Pics_id";
    private static final String PICTURE_NAME="Pics_name";
    private static final String PICTURE_DATA = "Pics_data";
    private static final String PICTURE_DATE = "Pics_date";

    private static final String CREATE_TABLE="Create Table "+TABLE_NAME+"("+PICTURE_ID+" TEXT,"//PRIMARY KEY
            +PICTURE_NAME+" TEXT,"+PICTURE_DATA+" BLOB,"+PICTURE_DATE+" TEXT"+")";

    public PicturesDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        // create new table
        onCreate(db);
    }
    public void addPic(Pictures pic) throws SQLException {
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();

        contentValues.put(PICTURE_ID,pic.getId());
        contentValues.put(PICTURE_NAME,pic.getName());
        contentValues.put(PICTURE_DATA,DbBitmapUtility.getBytes(pic.getBm()));
        contentValues.put(PICTURE_DATE,pic.getDateTime());

        db.insert(TABLE_NAME,null,contentValues);
       /* String insert="INSERT INTO " + TABLE_NAME + " ("
               + PICTURE_ID + ", " + PICTURE_NAME + ", "
               + PICTURE_DATA + ", " + PICTURE_DATE + ")  Values ('"+pic.getId()+"','"+pic.getName()+"', " +
               "'"+DbBitmapUtility.getBytes(pic.getBm())+"','"+pic.getDateTime()+"')";
       db.execSQL(insert);*/
       db.close();
    }
    public ArrayList<Pictures> GetAllPics(){
        ArrayList<Pictures> arrayList=new ArrayList<Pictures>();

        String selectQuery= "SELECT  * FROM "+TABLE_NAME;
        SQLiteDatabase db=this.getReadableDatabase();
        Cursor cursor=db.rawQuery(selectQuery,null);
        if(cursor.moveToFirst()){
            do{
                Pictures pictures=new Pictures(cursor.getString(0),
                        cursor.getString(1),DbBitmapUtility.getImage(cursor.getBlob(2)),
                        cursor.getString(3));
                arrayList.add(pictures);
            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return arrayList;
    }
    public  void deletePic(Pictures picture){
        SQLiteDatabase db=this.getWritableDatabase();

        db.delete(TABLE_NAME,PICTURE_ID+" =?",new String[]{picture.getId()});
        db.close();
    }
    public void updatePic(Pictures picture){
        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues contentValues=new ContentValues();
        contentValues.put(PICTURE_NAME,picture.getName());
        contentValues.put(PICTURE_DATE,picture.getDateTime());
        contentValues.put(PICTURE_DATA,DbBitmapUtility.getBytes(picture.getBm()));

        db.update(TABLE_NAME,contentValues,
                PICTURE_ID+" =?",new String[]{picture.getId()});
        db.close();
    }

}
