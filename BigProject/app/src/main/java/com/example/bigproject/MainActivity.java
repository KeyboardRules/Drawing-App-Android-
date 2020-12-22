package com.example.bigproject;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import android.provider.MediaStore;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ZoomButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity<OnActivityResult> extends AppCompatActivity implements View.OnClickListener,SearchView.OnQueryTextListener,
        BackgroundTask.AsyncResponse,LoadPictureTask.loadResponse {
    SearchView txtSearch;
    GridView listImage;
    FloatingActionButton addButton;
    private ArrayList<Pictures> listPics=new ArrayList<Pictures>();
    private ArrayList<Pictures> data=new ArrayList<Pictures>();
    private int Selected=-1;
    private boolean sort=true;
    BackgroundTask backgroundTask;
    LoadPictureTask loadPictureTask;
    private MyAdapter myAdapter;
    private String currID;
    private Pictures TranPics;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Display();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater=new MenuInflater(this);
        menuInflater.inflate(R.menu.menu_main,menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        if(requestCode==100&&resultCode==200){
            Bundle bundle=data.getExtras();
            String id=currID+bundle.getString("id");
            String Name=bundle.getString("name");
            TranPics=new Pictures(id,Name,DbBitmapUtility.getImage(bundle.getByteArray("image")), GetDate());

            String method="insert";
            backgroundTask=new BackgroundTask(this,this);
            backgroundTask.execute(method,currID,id,Name,
                    DbBitmapUtility.getStrings(bundle.getByteArray("image")),"2019-12-9");
        }
        if(requestCode==200&&resultCode==200){
            Bundle bundle=data.getExtras();
            String Name=bundle.getString("name");
            this.data.remove(TranPics);
            TranPics.setName(Name);
            TranPics.setBm(DbBitmapUtility.getImage(bundle.getByteArray("image")));

            String method="update";
            backgroundTask=new BackgroundTask(this,this);
            backgroundTask.execute(method,TranPics.getId(),TranPics.getName(),
                    DbBitmapUtility.getStrings(bundle.getByteArray("image")));


        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.log_out_btn){
            final AlertDialog.Builder LogoutDialog = new AlertDialog.Builder(this);
            LogoutDialog.setTitle("Log out");
            LogoutDialog.setMessage("Are you sure you want to log out?");
            LogoutDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    MainActivity.this.startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    finish();
                }
            });
            LogoutDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            LogoutDialog.show();
        }
        else if(v.getId()==R.id.sort_btn){
            TextView txt=(TextView)findViewById(v.getId());
            if(sort){
                sort=false;
                txt.setText("Sort by oldest");
                Collections.sort(listPics);
                Collections.sort(data);
                myAdapter.notifyDataSetChanged();
            }
            else{
                sort=true;
                txt.setText("Sort by newest");
                Collections.sort(listPics,new DecDate());
                Collections.sort(data,new DecDate());
                myAdapter.notifyDataSetChanged();
            }
        }
    }
    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item){
        Intent intent;
        switch (item.getItemId()){
            case R.id.menu_delete:
                if(Selected<0)
                {Toast.makeText(MainActivity.this,"Try again:Error happe",Toast.LENGTH_SHORT).show();break;}

                final AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
                newDialog.setTitle("Delete");
                newDialog.setMessage("Are you sure to Delete this image");
                newDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String method="delete";
                        backgroundTask=new BackgroundTask(MainActivity.this,MainActivity.this);
                        TranPics=listPics.get(Selected);
                        backgroundTask.execute(method,TranPics.getId());
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
                break;
            case R.id.menu_edit:
                intent = new Intent(MainActivity.this, DrawActivity.class);
                if(Selected<0)
                {Toast.makeText(MainActivity.this,"Try again:Error happen",Toast.LENGTH_SHORT).show();break;}

                TranPics=listPics.get(Selected);
                Pictures pic = TranPics;

                Bundle bundle = new Bundle();
                bundle.putString("name", pic.getName());
                //chuyen bitmap->bytearray
                //cho vao bundle
                bundle.putByteArray("image", DbBitmapUtility.getBytes(pic.getBm()));
                intent.putExtras(bundle);
                MainActivity.this.startActivityForResult(intent, 200);
                break;
            case R.id.menu_download:
                if(Selected<0)
                {Toast.makeText(MainActivity.this,"Try again:Error happen",Toast.LENGTH_SHORT).show();break;}

                Pictures pic2 = listPics.get(Selected);
                String imgSaved = MediaStore.Images.Media.insertImage(getContentResolver(),
                        pic2.getBm(),
                        pic2.getName() + ".png", "pics");
                if (imgSaved != null) {
                    Toast savedToast = Toast.makeText(getApplicationContext(),
                            "Drawing saved to gallery", Toast.LENGTH_SHORT);
                    savedToast.show();
                } else {
                    Toast unsaved = Toast.makeText(getApplicationContext(),
                            "Could not saved", Toast.LENGTH_SHORT);
                    unsaved.show();
                }
                break;
        }
        return super.onContextItemSelected(item);
    }
    private void Display(){
        txtSearch=(SearchView)findViewById(R.id.search_name);
        listImage=(GridView)findViewById(R.id.list_image);
        addButton=(FloatingActionButton)findViewById(R.id.addbutton);
        //db=new PicturesDatabase(this);

        registerForContextMenu(listImage);

        final Intent intent=getIntent();
        Bundle bundle=intent.getExtras();
        if(bundle!=null) currID=bundle.getString("id");

        loadPictureTask=new LoadPictureTask(this,this);
        loadPictureTask.execute(currID);

       // listPics=db.GetAllPics();
        txtSearch.setOnQueryTextListener(this);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, DrawActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
        listImage.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainActivity.this.Selected=position;
                Toast.makeText(MainActivity.this,
                        "Select item "+Integer.toString(Selected),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String text){
        String txt=text.toLowerCase();
        listPics.clear();
        if(txt.length()==0){
            listPics.addAll(data);
        }
        else{
            for (Pictures pic:data
                 ) {
                if(pic.getName().toLowerCase(Locale.getDefault()).contains(txt)){
                    listPics.add(pic);
                }
            }
        }
        if(!listPics.isEmpty()){
            myAdapter.notifyDataSetChanged();
        }
        return false;
    }
    public static String GetDate(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        String Datetime=sdf.format(date);
        return Datetime;
    }
    @Override
    public void processFinish(String output) {
        if(output.equals(null)){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Something wrong...");
            newDialog.show();
        }
        else if(output.equals("insert_success")){
            Toast.makeText(this,"Insert success",Toast.LENGTH_LONG).show();
            listPics.add(TranPics);
            data.add(TranPics);
            myAdapter.notifyDataSetChanged();
        }
        else if(output.equals("insert_fail")){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Fail to insert...");
            newDialog.show();
        }
        else if(output.equals("delete_success")){
            Toast.makeText(this,"Delete success",Toast.LENGTH_LONG).show();
            listPics.remove(TranPics);
            data.remove(TranPics);
            myAdapter.notifyDataSetChanged();
        }
        else if(output.equals("delete_fail")){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Fail to delete...");
            newDialog.show();
        }
        else if(output.equals("update_success")){
            Toast.makeText(this,"Update success",Toast.LENGTH_LONG).show();
            listPics.set(Selected,TranPics);
            data.add(TranPics);
            myAdapter.notifyDataSetChanged();
        }
        else if(output.equals("update_fail")){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Fail to update...");
            newDialog.show();
        }
        else{
            Toast.makeText(this,output,Toast.LENGTH_LONG).show();
        }
        backgroundTask.cancel(true);
    }
    @Override
    public void loadFinish(String output) {
        if(output.equals(null)){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Fail to load image...");
            newDialog.show();
        }
        else{
            try{
                JSONObject jsonObject=new JSONObject(output);
                int success=jsonObject.getInt("success");
                if(success==1){
                    JSONArray jsonArray=jsonObject.getJSONArray("pics");
                    for(int i=0;i<jsonArray.length();i++){
                        JSONObject pic=jsonArray.getJSONObject(i);
                        String image_id=pic.getString("image_id");
                        String image_name=pic.getString("image_name");
                        Bitmap image_data=DbBitmapUtility.StringToBitmap(pic.getString("image_data"));
                        String image_date=pic.getString("image_date");

                        Pictures pictures=new Pictures(image_id,image_name,image_data,image_date);
                        listPics.add(pictures);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        data.addAll(listPics);
        myAdapter=new MyAdapter(this,listPics);
        listImage.setAdapter(myAdapter);
    }
}
