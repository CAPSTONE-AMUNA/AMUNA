package com.example.amuna;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.amuna.LoginSplash.Email;

public class LoginSplashID extends AppCompatActivity {

    public static String MyKey,MyImg,MyNick;
    public static Activity loginsplashID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);
        loginsplashID = LoginSplashID.this;

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/getmyid.php?Email="+Email;
            System.out.println(target);
        }

        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }


        @Override
        //php 연결되면 ManagementActivity 로 전환
        public void onPostExecute(String result){

            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String id;
                while(count< jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    MyKey = object.getString("_Key");
                    MyImg = object.getString("PhotoURL");
                    MyNick = object.getString("Nickname");

                    System.out.println(MyKey+"aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");

                    count++;
                }
                System.out.println(MyImg+"    "+MyNick);
            }catch (Exception e){
                e.printStackTrace();
            }

            Intent intent = new Intent(LoginSplashID.this, LoginSplashFav.class);

            LoginSplashID.this.startActivity(intent);
            overridePendingTransition(0, 0);
        }

    }
}