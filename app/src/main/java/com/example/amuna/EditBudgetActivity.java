package com.example.amuna;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.amuna.LoginActivity.MMMMMMMyEmail;
import static com.example.amuna.LoginSplashID.MyKey;
import static com.example.amuna.LoginSplash.where;
import static com.example.amuna.SubwayBudget.adapter;
import static com.example.amuna.SubwayBudget.sub_where;

public class EditBudgetActivity extends AppCompatActivity {

    private EditText date2, intro2;
    public static EditText et_subway2;
    public static List<Subway> saved2;
    public static ListView listView2;
    private static locationAdapter adapter2;
    private Button btn_next2;

    RangeSeekBar rangeSeekBar2;
    private TextView mtv, mtv2;

    private String Sub_ID, LimitBudget, LimitDate, MyIntro;

    Main2Activity main2Activity = (Main2Activity) Main2Activity.main2activity;
    LoginSplashFav loginSplashFav = (LoginSplashFav) LoginSplashFav.loginsplashfav;
    LoginSplashID loginSplashID = (LoginSplashID) LoginSplashID.loginsplashID;
    LoginSplash loginSplash = (LoginSplash) LoginSplash.loginsplash;

    Calendar myCalendar2 = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_budget);

        sub_where=1;//

        rangeSeekBar2 = (RangeSeekBar)findViewById(R.id.rangeseekbar);
        rangeSeekBar2.setSelectedMaxValue(100);
        rangeSeekBar2.setSelectedMinValue(0);
        mtv = (TextView)findViewById(R.id.tv);
        mtv2 = (TextView)findViewById(R.id.tv2);

        date2 = findViewById(R.id.date);
        intro2 = findViewById(R.id.intro);
        btn_next2 = findViewById(R.id.btn_next);
        et_subway2 = findViewById(R.id.et_subway);
        listView2 = (ListView) findViewById(R.id.addList);

        saved2 = new ArrayList<Subway>();

        new BackgroundTask().execute();
        new BackgroundTask2().execute();
        setListViewHeightBasedOnChildren2(listView2);

    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://matehunter.cafe24.com/getBudInfo.php?_Key=" + MyKey;
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
//                JSONArray jsonArray = jsonObject.getJSONArray("MyLoca");
                JSONArray jsonArray2 = jsonObject.getJSONArray("MyElse");
                int count = 0;
//                while (count < jsonArray.length()) {
//                    JSONObject object = jsonArray.getJSONObject(count);
//                    Sub_ID = object.getString("Sub_ID");
//                    System.out.println("1~~~~~~~~~"+Sub_ID+"~~~~~~~~~~");
//
//                    count++;
//                }
//                count=0;
                while (count < jsonArray2.length()) {
                    JSONObject object = jsonArray2.getJSONObject(count);
                    LimitBudget = object.getString("LimitBudget");
                    LimitDate = object.getString("LimitDate");
                    MyIntro = object.getString("Intro");

                    System.out.println("2~~~~~~~~~ "+LimitBudget+" "+LimitDate+" "+MyIntro);

                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            //기본 설정
            date2.setText(LimitDate);
            intro2.setText(MyIntro);
            String[] splitBudget = LimitBudget.split("-");
            String Smtv = splitBudget[0];
            String Smtv2 = splitBudget[1];
            mtv.setText(Smtv);
            mtv2.setText(Smtv2);
            rangeSeekBar2.setSelectedMinValue(Integer.parseInt(Smtv));
            rangeSeekBar2.setSelectedMaxValue(Integer.parseInt(Smtv2));
            NewSelect();
        }
    }

    private void NewSelect(){

        date2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        new DatePickerDialog(EditBudgetActivity.this, myDatePicker, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                    }
                }
                return false;
            }

        });

        et_subway2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Intent intent = new Intent(view.getContext(), SearchSubwayActivity.class);
                        startActivity(intent);
                        setListViewHeightBasedOnChildren2(listView2);
                        break;
                    }
                }
                adapter2.notifyDataSetChanged();
                return false;
            }
        });



        rangeSeekBar2.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                int min = (int)min_value;
                int max = (int)max_value;

                //     Toast.makeText(getApplicationContext(),"Min="+min+ "\n"+"Max="+max, Toast.LENGTH_LONG).show();

                mtv.setText(String.valueOf(min));
                mtv2.setText(String.valueOf(max));
            }
        });

        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="";

                for(int i=0;i<saved2.size();i++){
                    String tmp=saved2.get(i).getSub_ID();
                    text= text.concat(tmp+" ");

                }

                // 받아오기
                String Date = date2.getText().toString();  ///기간
                String Intro = intro2.getText().toString();///소개글
                String Mtv = mtv.getText().toString();   ///최소예산
                String Mtv2 = mtv2.getText().toString();  ///최대예산

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                where=4;
                                finish();
                                main2Activity.finish();
                                loginSplashFav.finish();
                                loginSplashID.finish();
                                loginSplash.finish();
                                Intent intent = new Intent(EditBudgetActivity.this, LoginSplash.class);
                                startActivity(intent);
                                overridePendingTransition(0, 0);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditBudgetActivity.this);
                                builder.setMessage("추가정보 수정 완료")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                //지역X
                Log.e("************", "MyKey"+MyKey+"/Date"+Date+"/Intro"+Intro+"/Mtv"+Mtv+"/Mtv2"+Mtv2);
                UpdateBudgetRequest request = new UpdateBudgetRequest(MyKey, text, Date, Intro, Mtv, Mtv2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditBudgetActivity.this);
                queue.add(request);

            }
        });
    }

    private void updateLabel2() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.date);
        et_date.setText(sdf.format(myCalendar2.getTime()));
    }

    class BackgroundTask2 extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/Areas.php?Key="+MyKey;
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
                String subID,subLine,subName;
                while(count< jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    subID=object.getString("Sub_ID");
                    subLine=object.getString("Sub_Line");
                    subName=object.getString("Sub_Name");

                    Subway data= new Subway(subID,subName,subLine);
                    saved2.add(data);
                    System.out.println(subID+"  "+subLine+"  "+subName);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }

            adapter2= new locationAdapter(getApplicationContext(),saved2);
            adapter2.notifyDataSetChanged();
            int Width=View.MeasureSpec.makeMeasureSpec(listView2.getWidth(),View.MeasureSpec.AT_MOST);
            System.out.println(saved2.size()+"pppppppppppppppppppppppppppppppppp");
            listView2.setAdapter(adapter2);
            setListViewHeightBasedOnChildren2(listView2);

        }




    }

    public static void setListViewHeightBasedOnChildren2(ListView listView) {
        if (adapter2 == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < adapter2.getCount(); i++) {
            View listItem = adapter2.getView(i, null, listView);
            //listItem.measure(0, 0);
            listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();

        params.height = totalHeight;
        listView.setLayoutParams(params);

        listView.requestLayout();
    }


}
