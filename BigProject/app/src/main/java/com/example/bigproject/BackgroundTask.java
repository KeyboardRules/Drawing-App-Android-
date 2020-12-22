package com.example.bigproject;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.net.URLEncoder;

import javax.xml.transform.Result;

public class BackgroundTask extends AsyncTask<String,Void,String> {
    Context context;
    public AsyncResponse delegate = null;
    public interface AsyncResponse {
        void processFinish(String output);
    }
    BackgroundTask(Context context,AsyncResponse delegate){
        this.context=context;
        this.delegate=delegate;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected String doInBackground(String... strings) {
        String reg_url="http://10.0.3.2/BTLAndroid/register.php";
        String login_url="http://10.0.3.2/BTLAndroid/login.php";
        String insert_url="http://10.0.3.2/BTLAndroid/insert.php";
        String delete_url="http://10.0.3.2/BTLAndroid/delete.php";
        String update_url="http://10.0.3.2/BTLAndroid/update.php";
        String method=strings[0];
        if(method.equals("register")){
            String user_name=strings[1];
            String user_password=strings[2];
            try{
                URL url=new URL(reg_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection)url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("user_name","UTF-8")+"="+URLEncoder.encode(user_name,"UTF-8")+"&"
                        +URLEncoder.encode("user_password","UTF-8")+"="+URLEncoder.encode(user_password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String res="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    res+=line;
                }
                is.close();
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else if(method.equals("login")){
            String login_name=strings[1];
            String login_password=strings[2];
            try{
                URL url=new URL(login_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));
                String data= URLEncoder.encode("login_name","UTF-8")+"="+URLEncoder.encode(login_name,"UTF-8")+"&"
                        +URLEncoder.encode("login_password","UTF-8")+"="+URLEncoder.encode(login_password,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String res="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    res+=line;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else if(method.equals("insert")){
            String user_id=strings[1];
            String image_id=strings[2];
            String image_name=strings[3];
            String image_data=strings[4];
            String image_date=strings[5];
            try{
                URL url=new URL(insert_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("user_id","UTF-8")+"="+URLEncoder.encode(user_id,"UTF-8")+"&"
                        +URLEncoder.encode("image_id","UTF-8")+"="+URLEncoder.encode(image_id,"UTF-8")+"&"
                        +URLEncoder.encode("image_name","UTF-8")+"="+URLEncoder.encode(image_name,"UTF-8")+"&"
                        +URLEncoder.encode("image_data","UTF-8")+"="+URLEncoder.encode(image_data,"UTF-8")+"&"
                        +URLEncoder.encode("image_date","UTF-8")+"="+URLEncoder.encode(image_date,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String res="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    res+=line;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                 e.printStackTrace();
                return null;
            }
        }
        else if(method.equals("delete")){
            String image_id=strings[1];
            try{
                URL url=new URL(delete_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("image_id","UTF-8")+"="+URLEncoder.encode(image_id,"UTF-8");

                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String res="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    res+=line;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        else if(method.equals("update")){
            String image_id=strings[1];
            String image_name=strings[2];
            String image_data=strings[3];
            try{
                URL url=new URL(update_url);
                HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream os=httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(os,"UTF-8"));

                String data= URLEncoder.encode("image_id","UTF-8")+"="+URLEncoder.encode(image_id,"UTF-8")+"&"
                        +URLEncoder.encode("image_name","UTF-8")+"="+URLEncoder.encode(image_name,"UTF-8")+"&"
                        +URLEncoder.encode("image_data","UTF-8")+"="+URLEncoder.encode(image_data,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                os.close();

                InputStream is=httpURLConnection.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(is,"iso-8859-1"));
                String res="";
                String line="";
                while((line=bufferedReader.readLine())!=null){
                    res+=line;
                }
                bufferedReader.close();
                is.close();
                httpURLConnection.disconnect();
                return res;
            } catch (MalformedURLException e) {
                e.printStackTrace();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {
        delegate.processFinish(result);
    }
}
