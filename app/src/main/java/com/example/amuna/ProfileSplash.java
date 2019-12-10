package com.example.amuna;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.amuna.R;
import com.example.amuna.Uprofile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.amuna.ChattingProfileSplash.chattingprofilesplash;
import static com.example.amuna.LoginSplash.Email;
import static com.example.amuna.LoginSplash.userList;
import static com.example.amuna.LoginSplashFav.favList;
import static com.example.amuna.LoginSplashID.MyKey;

public class ProfileSplash extends AppCompatActivity {
    ArrayList<String> area = new ArrayList<>();
    String YourKey;
    String id,Nickname,Foreigner,Age,Job,PhotoURL,Intro,LimitBudget;
    String ff;
    public static int wherer;
    public static Activity profileSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_splash);
        ChattingProfileSplash chattingprofilesplash = (ChattingProfileSplash) ChattingProfileSplash.chattingprofilesplash;

        wherer=2;

        profileSplash=this;
        id =getIntent().getStringExtra("_Key");
        Nickname= getIntent().getStringExtra("Nickname");
        Job = getIntent().getStringExtra("Job");
        Age = getIntent().getStringExtra("Age");
        LimitBudget = getIntent().getStringExtra("LimitBudget");
        Foreigner = getIntent().getStringExtra("Foreigner");
        Intro = getIntent().getStringExtra("Intro");
        PhotoURL=getIntent().getStringExtra("PhotoURL");
        ff =getIntent().getStringExtra("jjim");

        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/Area.php?Key="+id;
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
        public void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;

                String sub_name, sub_line;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    sub_line = object.getString("Sub_Line");
                    sub_name = object.getString("Sub_Name");

                    area.add(sub_line + " " + sub_name);

                    count++;

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            Intent intent = new Intent(ProfileSplash.this, Uprofile.class);

            intent.putExtra("_Key",id);
            intent.putExtra("Nickname",Nickname);
            intent.putExtra("Job", Job );
            intent.putExtra("Age", Age);
            intent.putExtra("LimitBudget",LimitBudget );
            intent.putExtra("Foreigner",  Foreigner);
            intent.putExtra("Intro", Intro);
            intent.putExtra("PhotoURL",PhotoURL);
            intent.putExtra("Sub",area);
            intent.putExtra("jjim", ff);




            ProfileSplash.this.startActivity(intent);


        }

    }


}