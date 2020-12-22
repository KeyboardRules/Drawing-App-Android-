package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.media.Image;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

public class DrawActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageButton currpaint,drawbtn,newbtn,erase,save,cancer;
    private DrawingView drawView;
    private EditText editName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);

        Display();
    }
    private void Display(){
        drawView=(DrawingView)findViewById(R.id.drawing);
        drawbtn=(ImageButton)findViewById(R.id.draw_btn);
        newbtn=(ImageButton)findViewById(R.id.new_btn);
        erase=(ImageButton)findViewById(R.id.erase_btn);
        save=(ImageButton)findViewById(R.id.save_btn);
        cancer=(ImageButton)findViewById(R.id.cancer_btn);
        editName=(EditText)findViewById(R.id.editName);
        LinearLayout paintLayout=(LinearLayout)findViewById(R.id.paint_color);
        currpaint=(ImageButton)paintLayout.getChildAt(0);

        currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));

        drawbtn.setOnClickListener(this);
        newbtn.setOnClickListener(this);
        erase.setOnClickListener(this);
        save.setOnClickListener(this);
        cancer.setOnClickListener(this);

        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();

        if(bundle!=null){
            editName.setText(bundle.getString("name"));
            byte[] byteArray =bundle.getByteArray("image");
            Bitmap bm= DbBitmapUtility.getImage(byteArray);
            drawView.LoadDraw(bm);
        }
    }
    public void paintClicked(View view){
        if(view !=currpaint){
            ImageButton imageButton=(ImageButton)view;
            String color=view.getTag().toString();
            drawView.setColor(color);
            imageButton.setImageDrawable(getResources().getDrawable(R.drawable.paint_pressed));
            currpaint.setImageDrawable(getResources().getDrawable(R.drawable.paint));
            currpaint=(ImageButton)view;
        }
    }
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.draw_btn) {
            Toast.makeText(DrawActivity.this,"Selected Draw",Toast.LENGTH_SHORT).show();
            drawView.setupDrawing();
        }
        if (v.getId() == R.id.erase_btn) {
            Toast.makeText(DrawActivity.this,"Selected Eraser",Toast.LENGTH_SHORT).show();
            drawView.setErase(true);
            drawView.setBrushSize(drawView.getBrushSize());
        }
        if (v.getId() == R.id.new_btn) {
            final AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("New Drawing");
            newDialog.setMessage("Are you sure to Start a new Drawing");
            newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    drawView.startNew();
                    dialog.dismiss();
                }
            });
            newDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            newDialog.show();
        }
        if(v.getId()==R.id.cancer_btn){
            final AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Cancel New Image");
            saveDialog.setMessage("Are you sure you want to cancel?");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    onBackPressed();
                }
            });
            saveDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
        if (v.getId() == R.id.save_btn) {
            final AlertDialog.Builder saveDialog = new AlertDialog.Builder(this);
            saveDialog.setTitle("Save Drawing");
            saveDialog.setMessage("Are you sure to save Drawing");
            saveDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(editName.getText().toString().isEmpty()){
                        Toast.makeText(DrawActivity.this,"Please type name of the picture",Toast.LENGTH_LONG).show();
                        return;
                    }
                    else{
                        Intent intent1=new Intent();
                        Bundle bundle1=new Bundle();
                        String id=randomId();

                        bundle1.putString("name",editName.getText().toString());
                        bundle1.putString("id",id);
                        //
                        drawView.setDrawingCacheEnabled(true);
                        Bitmap bmp=drawView.getDrawingCache();
                        //
                        bundle1.putByteArray("image",DbBitmapUtility.getBytes(bmp));
                        intent1.putExtras(bundle1);
                        setResult(200,intent1);
                        drawView.destroyDrawingCache();
                        finish();
                    }
                }
            });
            saveDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            saveDialog.show();
        }
    }
    public static String randomId() {
        final String DATA = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random RANDOM = new Random();
        StringBuilder sb = new StringBuilder(5);

        for (int i = 0; i < 5; i++) {
            sb.append(DATA.charAt(RANDOM.nextInt(DATA.length())));
        }

        return sb.toString();
    }
}
/*drawView.setDrawingCacheEnabled(true);
                    String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(),
                            drawView.getDrawingCache(),
                            UUID.randomUUID().toString() + ".png", "pics");
                    if (imgSaved != null) {
                        Toast savedToast = Toast.makeText(getApplicationContext(),
                                "Drawing saved to gallery", Toast.LENGTH_SHORT);
                        savedToast.show();
                    } else {
                        Toast unsaved = Toast.makeText(getApplicationContext(),
                                "Could not saved", Toast.LENGTH_SHORT);
                        unsaved.show();
                    }
                    drawView.destroyDrawingCache();*/
