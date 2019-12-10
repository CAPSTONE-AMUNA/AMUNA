package com.example.amuna;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

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

import static com.example.amuna.LoginSplash.where;
import static com.example.amuna.LoginSplashID.MyKey;

public class EditLifeCharActivity extends AppCompatActivity {

    private String mKey;
    public static String O_q[] = new String[9];
    public static String M_q[] = new String[9];
    private RadioGroup rg_q1, rg_q2, rg_q3, rg_q4, rg_q5, rg_q6, rg_q7, rg_q8, rg_q9;
    private CheckBox cb_q1, cb_q2, cb_q3, cb_q4, cb_q5, cb_q6, cb_q7, cb_q8, cb_q9;
    private Button btn_end_lifechartest;
    public static RadioButton[] btn_y = new RadioButton[9];
    public static RadioButton[] btn_n = new RadioButton[9];
    public static CheckBox[] cb = new CheckBox[9];
    int my_q[];
    int ot_q[];
    Main2Activity main2Activity = (Main2Activity) Main2Activity.main2activity;
    LoginSplashFav loginSplashFav = (LoginSplashFav) LoginSplashFav.loginsplashfav;
    LoginSplashID loginSplashID = (LoginSplashID) LoginSplashID.loginsplashID;
    LoginSplash loginSplash = (LoginSplash) LoginSplash.loginsplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_life_char);


        new BackgroundTask().execute();
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://matehunter.cafe24.com/getLifeChar.php?_Key=" + MyKey;
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

                    for(int i=0;i<9;i++){
                        O_q[i] = object.getString("q"+(i+1));
                        System.out.println("남 성향"+O_q[i]);
                        M_q[i] = object.getString("M_q"+(i+1));
                        System.out.println("내 성향"+M_q[i]);
                    }

                    System.out.println("db 가져오기 테스트 키값"+mKey);

                    count++;
                }
                NewSelect(M_q, O_q);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void NewSelect(String[] m, String[] o){

        //기존 정보 set
        for(int i=0;i<9;i++) {
            switch (m[i]) {
                case "0":
                    int getResourceId = getResources().getIdentifier("rb_q" + (i + 1) + "_y", "id", this.getPackageName());
                    btn_y[i] = findViewById(getResourceId);
                    btn_y[i].performClick();
                    break;
                case "2":
                    int getResourceId2 = getResources().getIdentifier("rb_q" + (i + 1) + "_n", "id", this.getPackageName());
                    btn_n[i] = findViewById(getResourceId2);
                    btn_n[i].performClick();
                    break;
                default:
                    break;
            }

            switch (o[i]) {
                case "0":
                case "2":
                    int getResourceId = getResources().getIdentifier("cb_q" + (i + 1), "id", this.getPackageName());
                    cb[i] = findViewById(getResourceId);
                    cb[i].setChecked(true);
                    break;
                default:
                    break;
            }
        }

        //새로 선택
        rg_q1 = findViewById(R.id.rg_q1);
        rg_q2 = findViewById(R.id.rg_q2);
        rg_q3 = findViewById(R.id.rg_q3);
        rg_q4 = findViewById(R.id.rg_q4);
        rg_q5 = findViewById(R.id.rg_q5);
        rg_q6 = findViewById(R.id.rg_q6);
        rg_q7 = findViewById(R.id.rg_q7);
        rg_q8 = findViewById(R.id.rg_q8);
        rg_q9 = findViewById(R.id.rg_q9);


        cb_q1 = findViewById(R.id.cb_q1);
        cb_q2 = findViewById(R.id.cb_q2);
        cb_q3 = findViewById(R.id.cb_q3);
        cb_q4 = findViewById(R.id.cb_q4);
        cb_q5 = findViewById(R.id.cb_q5);
        cb_q6 = findViewById(R.id.cb_q6);
        cb_q7 = findViewById(R.id.cb_q7);
        cb_q8 = findViewById(R.id.cb_q8);
        cb_q9 = findViewById(R.id.cb_q9);

        my_q = new int[9];
        ot_q = new int[9];

        for(int i=0;i<my_q.length;i++) {
            my_q[i]=3;
        }

        btn_end_lifechartest = findViewById(R.id.btn_end_lifechartest);

        rg_q1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q1_y://y->0
                        my_q[0] = 0;
                        break;
                    case R.id.rb_q1_n://n->2
                        my_q[0] = 2;
                        break;
                }
            }
        });
        rg_q2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q2_y://y->0
                        my_q[1] = 0;
                        break;
                    case R.id.rb_q2_n://n->2
                        my_q[1] = 2;
                        break;
                }
            }
        });
        rg_q3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q3_y://y->0
                        my_q[2] = 0;
                        break;
                    case R.id.rb_q3_n://n->2
                        my_q[2] = 2;
                        break;
                }
            }
        });
        rg_q4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q4_y://y->0
                        my_q[3] = 0;
                        break;
                    case R.id.rb_q4_n://n->2
                        my_q[3] = 2;
                        break;
                }
            }
        });
        rg_q5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q5_y://y->0
                        my_q[4] = 0;
                        break;
                    case R.id.rb_q5_n://n->2
                        my_q[4] = 2;
                        break;
                }
            }
        });
        rg_q6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q6_y://y->0
                        my_q[5] = 0;
                        break;
                    case R.id.rb_q6_n://n->2
                        my_q[5] = 2;
                        break;
                }
            }
        });
        rg_q7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q7_y://y->0
                        my_q[6] = 0;
                        break;
                    case R.id.rb_q7_n://n->2
                        my_q[6] = 2;
                        break;
                }
            }
        });
        rg_q8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q8_y://y->0
                        my_q[7] = 0;
                        break;
                    case R.id.rb_q8_n://n->2
                        my_q[7] = 2;
                        break;
                }
            }
        });
        rg_q9.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i) {
                    case R.id.rb_q9_y://y->0
                        my_q[8] = 0;
                        break;
                    case R.id.rb_q9_n://n->2
                        my_q[8] = 2;
                        break;
                }
            }
        });
        btn_end_lifechartest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                int j=0;
                for(int i=0;i<9;i++)
                    if (my_q[i] == 3) {
//                        j = 1;
                        my_q[i]=Integer.parseInt(M_q[i]);
                    }


//                if(j==1){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(EditLifeCharActivity.this);
//                    builder.setMessage("선택되지 않은 문항이 있습니다\n선택을 완료해주세요");
//                    builder.setPositiveButton("확인", null);
//                    AlertDialog dialog = builder.show();
//                    TextView messageText = dialog.findViewById(android.R.id.message);
//                    messageText.setGravity(Gravity.CENTER);
//                    dialog.show();
//                    Toast.makeText(EditLifeCharActivity.this, "선택되지 않은 문항이 있습니다", Toast.LENGTH_LONG).show();
//                }else{
                    if (cb_q1.isChecked() == true) ot_q[0] = my_q[0];
                    else ot_q[0] = 1;
                    if (cb_q2.isChecked() == true) ot_q[1] = my_q[1];
                    else ot_q[1] = 1;
                    if (cb_q3.isChecked() == true) ot_q[2] = my_q[2];
                    else ot_q[2] = 1;
                    if (cb_q4.isChecked() == true) ot_q[3] = my_q[3];
                    else ot_q[3] = 1;
                    if (cb_q5.isChecked() == true) ot_q[4] = my_q[4];
                    else ot_q[4] = 1;
                    if (cb_q6.isChecked() == true) ot_q[5] = my_q[5];
                    else ot_q[5] = 1;
                    if (cb_q7.isChecked() == true) ot_q[6] = my_q[6];
                    else ot_q[6] = 1;
                    if (cb_q8.isChecked() == true) ot_q[7] = my_q[7];
                    else ot_q[7] = 1;
                    if (cb_q9.isChecked() == true) ot_q[8] = my_q[8];
                    else ot_q[8] = 1;

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if (success) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditLifeCharActivity.this);
                                    builder.setMessage("수정 완료")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    where=4;
                                    finish();
                                    main2Activity.finish();
                                    loginSplashFav.finish();
                                    loginSplashID.finish();
                                    loginSplash.finish();
                                    Intent intent = new Intent(EditLifeCharActivity.this, LoginSplash.class);
                                    startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditLifeCharActivity.this);
                                    builder.setMessage("수정 실패")
                                            .setNegativeButton("다시시도", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    };

                    EditLifeCharRequest registerRequest = new EditLifeCharRequest(mKey, my_q[0], my_q[1], my_q[2], my_q[3], my_q[4], my_q[5], my_q[6], my_q[7], my_q[8],
                            ot_q[0], ot_q[1], ot_q[2], ot_q[3], ot_q[4], ot_q[5], ot_q[6], ot_q[7], ot_q[8], responseListener);
                    RequestQueue queue = Volley.newRequestQueue(EditLifeCharActivity.this);
                    queue.add(registerRequest);
                }

//            }
        });
    }
}
