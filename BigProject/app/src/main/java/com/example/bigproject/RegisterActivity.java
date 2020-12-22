package com.example.bigproject;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, BackgroundTask.AsyncResponse {
    EditText res_name,res_pass,res_confirm_pass;
    public boolean result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Display();
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login_btn_register){
            RegisterActivity.this.startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
            finish();
        }
        else if(v.getId()==R.id.register_btn_register){
            if(CheckRequired()){
                String method="register";
                BackgroundTask backgroundTask=new BackgroundTask(this,this);
                backgroundTask.execute(method,res_name.getText().toString(),res_pass.getText().toString());
            }
        }
    }
    public void Display(){
        res_name=(EditText)findViewById(R.id.register_username);
        res_pass=(EditText)findViewById(R.id.register_pass);
        res_confirm_pass=(EditText)findViewById(R.id.register_confirm_pass);
    }
    private boolean CheckRequired(){
        if(res_name.getText().toString().length()==0){
            res_name.setError("Please type your name");
            return false;
        }
        if(res_pass.getText().toString().length()==0){
            res_pass.setError("Please type your password");
            return false;
        }
        if(!res_confirm_pass.getText().toString().equals(res_pass.getText().toString())){
            res_confirm_pass.setError("Confirm password doesn't match password");
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
        else if(output.equals("exist")){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Already exist this user");
            newDialog.show();
        }
        else if(output.equals("register_fail")){
            AlertDialog.Builder newDialog = new AlertDialog.Builder(this);
            newDialog.setTitle("Error");
            newDialog.setMessage("Register Fail....");
            newDialog.show();
        }
        else{
            Toast.makeText(this,"Register success...",Toast.LENGTH_SHORT);
            finish();
        }
    }
}
