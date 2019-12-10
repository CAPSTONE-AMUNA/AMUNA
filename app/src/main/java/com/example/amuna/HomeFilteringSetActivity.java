package com.example.amuna;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.florescu.android.rangeseekbar.RangeSeekBar;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static com.example.amuna.LoginSplash.Email;
import static com.example.amuna.Main2Activity.userList2;
import static com.example.amuna.HomeTab2Fragment.tv_condition;

public class HomeFilteringSetActivity extends AppCompatActivity {


    private Spinner sp_house;
    private EditText et_minBudget, et_maxBudget;
    private RadioGroup rg_korf;
    private int Nationality=2;
    private Button btnConfirm;
    private Button btnCancel;
    public static String setHouse, setMinBudget, setMaxBudget;
    public int min, max, h;
    private RangeSeekBar rangeSeekBar;
    private TextView mtv, mtv2;
    String f,total;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_filtering_set);

        btnConfirm = findViewById(R.id.btn_confirm);
        btnCancel = findViewById(R.id.btn_cancel);

        sp_house = findViewById(R.id.sp_house);
        rg_korf = findViewById(R.id.rg_korf);

        et_minBudget = findViewById(R.id.et_minBudget);
        et_maxBudget = findViewById(R.id.et_maxBudget);

        rangeSeekBar = (RangeSeekBar)findViewById(R.id.rangeseekbar);
        rangeSeekBar.setSelectedMaxValue(100);
        rangeSeekBar.setSelectedMinValue(0);
        mtv = (TextView)findViewById(R.id.tv);
        mtv2 = (TextView)findViewById(R.id.tv2);

        //주거 유형 선택
        sp_house.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                setHouse = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        //내/외국인 여부 int형으로
        rg_korf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_korean://내국인->0
                        Nationality = 0;
                        f="내국인";
                        break;
                    case R.id.rb_foreigner://외국인->1
                        Nationality = 1;
                        f="외국인";
                        break;
                }
            }
        });

        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
            @Override
            public void onRangeSeekBarValuesChanged(RangeSeekBar bar, Object minValue, Object maxValue) {
                Number min_value = bar.getSelectedMinValue();
                Number max_value = bar.getSelectedMaxValue();

                min = (int)min_value;
                max = (int)max_value;

//                     Toast.makeText(getApplicationContext(),"Min="+min+ "\n"+"Max="+max, Toast.LENGTH_LONG).show();

                mtv.setText(String.valueOf(min));
                mtv2.setText(String.valueOf(max));
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnConfirm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                setMinBudget = et_minBudget.getText().toString();
                setMaxBudget = et_maxBudget.getText().toString();


                if(setHouse.equals("기숙사")){
                    h=1;
                }else if(setHouse.equals("자취")){
                    h=2;
                }else if(setHouse.equals("쉐어하우스")){
                    h=3;
                }else{
                    h=0;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            JSONArray jsonArray = jsonResponse.getJSONArray("response");
                            int count = 0;
                            String id,name,Gender,rating,Nickname,Foreigner,Age,PhoneNum,Job,PhotoURL, Univ_Name,Intro,LimitBudget;
                            userList2.clear();
                            while(count< jsonArray.length()){
                                JSONObject object = jsonArray.getJSONObject(count);
                                id = object.getString("_Key");
                                name = object.getString("Name");
                                Gender = object.getString("Gender");
//                                rating = object.getString("Rating");
                                Nickname = object.getString("Nickname");
                                Foreigner = object.getString("Foreigner");
                                Age = object.getString("Age");
                                PhoneNum = object.getString("PhoneNum");
                                Job = object.getString("Job");
                                LimitBudget= object.getString("LimitBudget");
                                PhotoURL = object.getString("PhotoURL");
                                Univ_Name = object.getString("Univ_Name");
                                Intro = object.getString("Intro");

                                Log.e("확이니ㅣ이ㅣㄴ",id+" "+name+" "+Gender+" "+Nickname);

                                Data data = new Data(id,name,Gender,"0",Nickname,Foreigner,Age,PhoneNum,Job,LimitBudget,PhotoURL,Univ_Name,Intro );
                                userList2.add(data);
                                count++;

                            }
                            Intent resultIntent = new Intent();
                            setResult(RESULT_OK,resultIntent);
                            finish();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                    }

                };
                Log.e("request전","Email"+Email+" setHouse"+setHouse+" Nationality"+Nationality+" min"+min+" max"+max+"setMinBudget"+setMinBudget+"setMaxBudget"+setMaxBudget);
                HomeFilteringSetRequest registerRequest = new HomeFilteringSetRequest(Email, h, Nationality, setMinBudget , setMaxBudget, Integer.toString(min),  Integer.toString(max), responseListener);
                RequestQueue queue = Volley.newRequestQueue(HomeFilteringSetActivity.this);
                queue.add(registerRequest);
                tv_condition.setText("");
//                total = setHouse+"/"+f+"/"+min+"~"+max+"세/"+setMinBudget+"~"+setMaxBudget+"만원";
                total = setHouse+"/"+f+"/"+setMinBudget+"~"+setMaxBudget+"세/"+min+"~"+max+"만원";
                tv_condition.setText(total);
            }
        });
    }
}
