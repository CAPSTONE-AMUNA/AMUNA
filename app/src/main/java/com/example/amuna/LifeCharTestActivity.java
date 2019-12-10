package com.example.amuna;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.amuna.RegisterActivity.MyEmail;

public class LifeCharTestActivity extends AppCompatActivity {

    private RadioGroup rg_q1, rg_q2, rg_q3, rg_q4, rg_q5, rg_q6, rg_q7, rg_q8, rg_q9;
    private CheckBox cb_q1, cb_q2, cb_q3, cb_q4, cb_q5, cb_q6, cb_q7, cb_q8, cb_q9;
    private Button btn_end_lifechartest, btn_cancel_register;
    int my_q[];
    int ot_q[];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_char_test);

        btn_cancel_register = findViewById(R.id.btn_cancel_register);

        btn_cancel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LifeCharTestActivity.this, LoginActivity.class);
                LifeCharTestActivity.this.startActivity(intent);
            }
        });

        my_q = new int[9];
        ot_q = new int[9];

        for(int i=0;i<my_q.length;i++) {
            my_q[i]=3;
        }

        btn_end_lifechartest = findViewById(R.id.btn_end_lifechartest);

        rg_q1 = findViewById(R.id.rg_q1);

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

        cb_q1 = findViewById(R.id.cb_q1);

        rg_q2 = findViewById(R.id.rg_q2);

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

        cb_q2 = findViewById(R.id.cb_q2);

        rg_q3 = findViewById(R.id.rg_q3);

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

        cb_q3 = findViewById(R.id.cb_q3);

        rg_q4 = findViewById(R.id.rg_q4);

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

        cb_q4 = findViewById(R.id.cb_q4);

        rg_q5 = findViewById(R.id.rg_q5);

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

        cb_q5 = findViewById(R.id.cb_q5);

        rg_q6 = findViewById(R.id.rg_q6);

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

        cb_q6 = findViewById(R.id.cb_q6);

        rg_q7 = findViewById(R.id.rg_q7);

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

        cb_q7 = findViewById(R.id.cb_q7);

        rg_q8 = findViewById(R.id.rg_q8);

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

        cb_q8 = findViewById(R.id.cb_q8);

        rg_q9 = findViewById(R.id.rg_q9);

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

        cb_q9 = findViewById(R.id.cb_q9);

        btn_end_lifechartest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int j=0;
                for(int i=0;i<9;i++)
                    if (my_q[i] == 3) j=1;


                if(j==1){
//                    AlertDialog.Builder builder = new AlertDialog.Builder(LifeCharTestActivity.this);
//                    builder.setMessage("선택되지 않은 문항이 있습니다\n선택을 완료해주세요");
//                    builder.setPositiveButton("확인", null);
//                    AlertDialog dialog = builder.show();
//                    TextView messageText = dialog.findViewById(android.R.id.message);
//                    messageText.setGravity(Gravity.CENTER);
//                    dialog.show();
                    Toast.makeText(LifeCharTestActivity.this, "선택되지 않은 문항이 있습니다", Toast.LENGTH_LONG).show();
                }else{
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LifeCharTestActivity.this);
                                    builder.setMessage("회원가입완료")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                    Intent intent = new Intent(LifeCharTestActivity.this, imageUpload.class);
                                    LifeCharTestActivity.this.startActivity(intent);
                                } else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LifeCharTestActivity.this);
                                    builder.setMessage("성향테스트완료")
                                            .setNegativeButton("다시시도", null)
                                            .create()
                                            .show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    };

                    LifeCharTestRequest registerRequest = new LifeCharTestRequest(MyEmail, my_q[0], my_q[1], my_q[2], my_q[3], my_q[4], my_q[5], my_q[6], my_q[7], my_q[8],
                            ot_q[0], ot_q[1], ot_q[2], ot_q[3], ot_q[4], ot_q[5], ot_q[6], ot_q[7], ot_q[8], responseListener);
                    RequestQueue queue = Volley.newRequestQueue(LifeCharTestActivity.this);
                    queue.add(registerRequest);
                }

            }
        });
    }

    @Override
    public void onBackPressed() {

    }
}