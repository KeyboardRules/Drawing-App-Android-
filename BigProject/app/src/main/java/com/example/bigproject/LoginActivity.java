package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, BackgroundTask.AsyncResponse {
    EditText login_pass,login_name;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Display();
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_btn_login){
            if(CheckRequired()){
                BackgroundTask backgroundTask=new BackgroundTask(this,this);
                String method="login";
                backgroundTask.execute(method,login_name.getText().toString(),login_pass.getText().toString());
            }
        }
        else if(v.getId()==R.id.register_btn_login){
            LoginActivity.this.startActivity(new Intent(this,RegisterActivity.class));
        }
    }
    public void Display(){
        login_name=(EditText)findViewById(R.id.login_name);
        login_pass=(EditText)findViewById(R.id.login_pass);
    }
    private boolean CheckRequired(){
        if(login_name.getText().toString().length()==0){
            login_name.setError("Please type your name");
            return false;
        }
        if(login_pass.getText().toString().length()==0){
            login_pass.setError("Please type your password");
            return false;
        }
        return true;
    }
    @Override
    public void processFinish(String output) {
        if(output.equals(null)){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Connect Error...");
            newDialog.show();
        }
        else if(output.equals("non_exist")){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Username or password is not correct!");
            newDialog.setPositiveButton("Ok",null);
            newDialog.show();
        }
        else{
            Intent intent=new Intent(this,MainActivity.class);
            Bundle bundle=new Bundle();

            bundle.putString("id",output);
            intent.putExtras(bundle);
            this.startActivity(intent);
            Toast.makeText(this,"Login Success....",Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}
