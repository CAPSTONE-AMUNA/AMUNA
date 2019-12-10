package com.example.amuna;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.amuna.LoginSplashID.MyKey;
import static com.example.amuna.LoginSplash.where;

public class EditHouseActivity extends AppCompatActivity {

    String mKey,mHouse,dbMyJob;
    Button domitory, myself, share, btn_next2;

    boolean domitoryPressed , myselfPressed, sharePressed;
    int h;

    Main2Activity main2Activity = (Main2Activity) Main2Activity.main2activity;
    LoginSplashFav loginSplashFav = (LoginSplashFav) LoginSplashFav.loginsplashfav;
    LoginSplashID loginSplashID = (LoginSplashID) LoginSplashID.loginsplashID;
    LoginSplash loginSplash = (LoginSplash) LoginSplash.loginsplash;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_house);



        domitory = (Button) findViewById(R.id.domitory);
        myself = (Button) findViewById(R.id.myself);
        share = (Button) findViewById(R.id.share);
        btn_next2=(Button)findViewById(R.id.btn_next2);

        new BackgroundTask().execute();

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://matehunter.cafe24.com/getHouse_test.php?_Key=" + MyKey;
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
                    mKey = object.getString("_Key");
                    mHouse = object.getString("h");
                    dbMyJob = object.getString("MyJob");

                    System.out.println("db 가져오기 테스트 "+mKey+"/"+mHouse);

                    count++;
                }
                //직업이 대학(원)생이 아니면 기숙사 버튼 비활성화
                if(!dbMyJob.equals("대학(원)생")){
                    domitory.setEnabled(false);
                }

                if(mHouse.equals("1")){
                    domitory.setBackgroundResource(R.drawable.button2);
                }else if(mHouse.equals("2")){
                    myself.setBackgroundResource(R.drawable.button2);
                }else if(mHouse.equals("3")){
                    share.setBackgroundResource(R.drawable.button2);
                }
                NewSelect();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private void NewSelect(){
        domitory.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (!myselfPressed && !sharePressed) {

                            domitory.setBackgroundResource(R.drawable.button);
                            v.setPressed(true);
                        }
                        domitoryPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        domitory.setBackgroundResource(R.drawable.button2);
                        domitoryPressed = false;
                        myself.setBackgroundResource(R.drawable.button);
                        share.setBackgroundResource(R.drawable.button);
                        h=1;
                        break;
                }
                return true;


            }
        });

        myself.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (!domitoryPressed && !sharePressed) {

                            myself.setBackgroundResource(R.drawable.button);
                            v.setPressed(true);
                        }
                        myselfPressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        myself.setBackgroundResource(R.drawable.button2);
                        v.setPressed(false);
                        myselfPressed = false;
                        domitory.setBackgroundResource(R.drawable.button);
                        share.setBackgroundResource(R.drawable.button);
                        h=2;
                        break;

                }
                return true;


            }
        });

        share.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:

                        if (!domitoryPressed && !myselfPressed) {

                            share.setBackgroundResource(R.drawable.button);
                            v.setPressed(true);
                        }
                        sharePressed = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        share.setBackgroundResource(R.drawable.button2);
                        v.setPressed(false);
                        sharePressed = false;
                        domitory.setBackgroundResource(R.drawable.button);
                        myself.setBackgroundResource(R.drawable.button);
                        h=3;
                        break;

                }
                return true;


            }
        });

        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                where=4;
                                finish();
                                main2Activity.finish();
                                loginSplashFav.finish();
                                loginSplashID.finish();
                                loginSplash.finish();
                                Intent intent = new Intent(EditHouseActivity.this, LoginSplash.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditHouseActivity.this);
                                builder.setMessage("주거유형 수정 완료")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                Log.e("request전"," regisEmail"+MyKey+" h"+h);
                UpdateHouseRequest houseRequest = new UpdateHouseRequest(MyKey,h, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditHouseActivity.this);
                queue.add(houseRequest);

            }
        });
    }
}