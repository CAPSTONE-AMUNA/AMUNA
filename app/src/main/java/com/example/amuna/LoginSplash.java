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

import static com.example.amuna.LoginActivity.MMMMMMMyEmail;

public class LoginSplash extends AppCompatActivity {

    public static String Email,PhotoURL,Nickname;
    public static List<Data> userList;
    public static int where=0;
    public static Activity loginsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);
        loginsplash = LoginSplash.this;
        //Email = getIntent().getStringExtra("Email");
        Email = MMMMMMMyEmail;
        PhotoURL = getIntent().getStringExtra("PhotoURL");
        Nickname = getIntent().getStringExtra("Nickname");



        userList=new ArrayList<Data>();
        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/algorithmList2_test.php?Email="+Email;
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
                String id,name,Gender,rating,Nickname,Foreigner,Age,PhoneNum,Job,PhotoURL, Univ_Name,Intro,LimitBudget;
                while(count< jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    id = object.getString("_Key");
                    name = object.getString("Name");
                    Gender = object.getString("Gender");
                    rating = object.getString("Rating");
                    Nickname = object.getString("Nickname");
                    Foreigner = object.getString("Foreigner");
                    Age = object.getString("Age");
                    PhoneNum = object.getString("PhoneNum");
                    Job = object.getString("Job");
                    LimitBudget= object.getString("LimitBudget");
                    PhotoURL = object.getString("PhotoURL");
                    Univ_Name = object.getString("Univ_Name");
                    Intro = object.getString("Intro");


                    Data data = new Data(id,name,Gender,rating,Nickname,Foreigner,Age,PhoneNum,Job,LimitBudget,PhotoURL,Univ_Name,Intro );
                    userList.add(data);
                    System.out.println(id+" "+name+" "+rating + " " +PhotoURL+Intro);

                    count++;
                }
                Intent intent = new Intent(LoginSplash.this, LoginSplashID.class);

                LoginSplash.this.startActivity(intent);
                overridePendingTransition(0, 0);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
