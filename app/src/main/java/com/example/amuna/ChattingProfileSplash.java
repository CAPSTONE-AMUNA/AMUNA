package com.example.amuna;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ChattingProfileSplash extends AppCompatActivity {
    String id,name,Gender,rating,Nickname,Foreigner,Age,PhoneNum,Job,PhotoURL, Univ_Name,Intro,LimitBudget;

    private String YKey;
    public static Activity chattingprofilesplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatting_profile_splash);
        chattingprofilesplash=this;
        Intent intent = getIntent();
        YKey = intent.getStringExtra("YKey");
        new BackgroundTask().execute();
    }


    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/getfuckingprofile.php?Key="+YKey;
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

                while(count< jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    id = object.getString("_Key");
                    name = object.getString("Name");
                    Gender = object.getString("Gender");
                    Nickname = object.getString("Nickname");
                    Foreigner = object.getString("Foreigner");
                    Age = object.getString("Age");
                    PhoneNum = object.getString("PhoneNum");
                    Job = object.getString("Job");
                    LimitBudget= object.getString("LimitBudget");
                    PhotoURL = object.getString("PhotoURL");
                    Univ_Name = object.getString("Univ_Name");
                    Intro = object.getString("Intro");

                    count++;
                }
                Intent intent = new Intent(ChattingProfileSplash.this, ProfileSplash.class);
                intent.putExtra("_Key", id);
                intent.putExtra("Nickname", Nickname);
                intent.putExtra("Job", Job);
                intent.putExtra("Age", Age);
                intent.putExtra("LimitBudget", LimitBudget);
                intent.putExtra("Foreigner", Foreigner);
                intent.putExtra("Intro", Intro);
                intent.putExtra("PhotoURL", PhotoURL);
                intent.putExtra("jjim", "2");
                startActivity(intent);

                ChattingProfileSplash.this.startActivity(intent);
            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }
}
