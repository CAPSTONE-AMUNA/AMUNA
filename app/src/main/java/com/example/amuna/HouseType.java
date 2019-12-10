package com.example.amuna;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.amuna.RegisterActivity.MyEmail;

public class HouseType extends AppCompatActivity {

    Button domitory, myself, share, btn_next2;

    boolean domitoryPressed , myselfPressed, sharePressed;
    int h;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_type);

        Intent getintent = getIntent();
        final String job_SB = getintent.getStringExtra("job");


        domitory = (Button) findViewById(R.id.domitory);
        myself = (Button) findViewById(R.id.myself);
        share = (Button) findViewById(R.id.share);
        btn_next2=(Button)findViewById(R.id.btn_next2);


        System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%"+job_SB);
        //직업이 대학(원)생이 아니면 기숙사 버튼 비활성화
        if(!job_SB.equals("대학(원)생")){
            domitory.setEnabled(false);
        }

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
                                Intent intent = new Intent(HouseType.this,LifeCharTestActivity.class);
                                HouseType.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(HouseType.this);
                                builder.setMessage("등록실패")
                                        .setNegativeButton("다시시도", null)
                                        .create()
                                        .show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                };
                Log.e("request전"," regisEmail"+MyEmail+" h"+h);
                HouseRequest houseRequest = new HouseRequest(MyEmail,h, responseListener);
                RequestQueue queue = Volley.newRequestQueue(HouseType.this);
                queue.add(houseRequest);

            }
        });


    }

}