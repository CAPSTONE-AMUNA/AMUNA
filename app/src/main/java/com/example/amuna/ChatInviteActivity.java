package com.example.amuna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.amuna.LoginSplashID.MyKey;

public class ChatInviteActivity extends AppCompatActivity {

    public static String YourKey;
    private List<Data> overlapList;
    private ListView overlap_list;
    private  ListAdapter adapter;
    public static String OKeyList;
    String[] FinalOKeyList;
    String mynick,yournick;
    private TextView tv_mynick,tv_yournick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_invite);

        Intent intent = getIntent();
        YourKey = intent.getStringExtra("YourKey");

        overlapList=new ArrayList<Data>();

        overlap_list = findViewById(R.id.listview_overlap);

        new BackgroundTask().execute();

        tv_mynick = findViewById(R.id.myNick);
        tv_yournick = findViewById(R.id.yourNick);


//        overlap_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                parent.getItemAtPosition(position);
//
//                Intent intent = new Intent(ChatInviteActivity.this, ProfileSplash.class);
//                intent.putExtra("_Key", overlapList.get(position).getMember_id());
//                intent.putExtra("Nickname", overlapList.get(position).getMember_Nickname());
//                intent.putExtra("Job", overlapList.get(position).getMember_Job());
//                intent.putExtra("Age", overlapList.get(position).getMember_Age());
//                intent.putExtra("LimitBudget", overlapList.get(position).getMember_LimitBudget());
//                intent.putExtra("Foreigner", overlapList.get(position).getMember_Foreigner());
//                intent.putExtra("Intro", overlapList.get(position).getMember_Intro());
//                intent.putExtra("PhotoURL", overlapList.get(position).getMember_PhotoURL());
//                intent.putExtra("jjim", "0");
//
//                startActivity(intent);
//            }
//        });
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/overlapList.php?MyKey="+MyKey+"&YourKey="+YourKey;
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
                JSONObject jsonObject = new JSONObject(result.substring(result.indexOf("{"),result.lastIndexOf("}")+1));
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String id,name,Gender,rating,Nickname,Foreigner,Age,PhoneNum,Job,PhotoURL, Univ_Name,Intro,LimitBudget;
                while(count< jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    id = object.getString("_Key");
                    name = object.getString("Name");
                    Gender = object.getString("Gender");
//                    rating = object.getString("Rating");
                    Nickname = object.getString("Nickname");
                    Foreigner = object.getString("Foreigner");
                    Age = object.getString("Age");
                    PhoneNum = object.getString("PhoneNum");
                    Job = object.getString("Job");
                    LimitBudget= object.getString("LimitBudget");
                    PhotoURL = object.getString("PhotoURL");
                    Univ_Name = object.getString("Univ_Name");
                    Intro = object.getString("Intro");
                    mynick = object.getString("mynick");
                    yournick = object.getString("yournick");


                    Data data = new Data(id,name,Gender,"0",Nickname,Foreigner,Age,PhoneNum,Job,LimitBudget,PhotoURL,Univ_Name,Intro );
                    overlapList.add(data);
                    System.out.println(id+" "+name+" " +PhotoURL+Intro);

                    System.out.println("mynick="+mynick);
                    System.out.println("yournick="+yournick);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
            tv_mynick.setText(mynick);
            tv_yournick.setText(yournick);


            adapter = new ListAdapter(ChatInviteActivity.this, overlapList);
            overlap_list.setAdapter(adapter);

            overlap_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    parent.getItemAtPosition(position);

                    Intent intent = new Intent(ChatInviteActivity.this, ProfileSplash.class);
                    intent.putExtra("_Key", overlapList.get(position).getMember_id());
                    intent.putExtra("Nickname", overlapList.get(position).getMember_Nickname());
                    intent.putExtra("Job", overlapList.get(position).getMember_Job());
                    intent.putExtra("Age", overlapList.get(position).getMember_Age());
                    intent.putExtra("LimitBudget", overlapList.get(position).getMember_LimitBudget());
                    intent.putExtra("Foreigner", overlapList.get(position).getMember_Foreigner());
                    intent.putExtra("Intro", overlapList.get(position).getMember_Intro());
                    intent.putExtra("PhotoURL", overlapList.get(position).getMember_PhotoURL());
                    intent.putExtra("jjim", "0");
                    startActivity(intent);

                }
            });

        }

    }
}
