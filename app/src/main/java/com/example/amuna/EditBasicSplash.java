package com.example.amuna;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.amuna.LoginSplashID.MyKey;

public class EditBasicSplash extends AppCompatActivity {

    public static String job, Key, Nickname,PhoneNum,Job, Univ_Name, PhotoURL;
    public static Activity editbasicsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_basic_splash);

        editbasicsplash = EditBasicSplash.this;

        new BackgroundTask().execute();
    }
    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://matehunter.cafe24.com/editBasic.php?_Key=" + MyKey;
            System.out.println(target);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }


        @Override
        //php 연결되면 ManagementActivity 로 전환
        public void onPostExecute(String result) {

            try {
                JSONObject jsonObject = new JSONObject(result.substring(result.indexOf("{"),result.lastIndexOf("}")+1));
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    Key = object.getString("_Key");
                    Nickname = object.getString("Nickname");
                    PhoneNum = object.getString("PhoneNum");
                    Job = object.getString("Job");
                    Univ_Name = object.getString("Univ_N");
                    PhotoURL = object.getString("PhotoURL");

                    System.out.println("db 가져오기 테스트 "+Key+"/"+Nickname+"/"+PhoneNum+"/"+Job+"/"+Univ_Name);

                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(EditBasicSplash.this, EditBasicTestActivity.class);
            intent.putExtra("Key",Key);
            intent.putExtra("Nickname",Nickname);
            intent.putExtra("PhoneNum",PhoneNum);
            intent.putExtra("Job",Job);
            intent.putExtra("Univ_Name",Univ_Name);
            intent.putExtra("PhotoURL",PhotoURL);
            EditBasicSplash.this.startActivity(intent);
            finish();
        }
    }
}
