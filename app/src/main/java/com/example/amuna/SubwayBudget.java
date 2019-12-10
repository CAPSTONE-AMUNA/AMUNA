package com.example.amuna;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;
import org.florescu.android.rangeseekbar.RangeSeekBar;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import static com.example.amuna.EditBudgetActivity.setListViewHeightBasedOnChildren2;
import static com.example.amuna.RegisterActivity.MyEmail;


public class SubwayBudget extends AppCompatActivity {

    private EditText date, intro;
    public static EditText et_subway;
    public static List<Subway> saved;
    public static ListView listView;
    public static locationAdapter adapter;
    private Button btn_next;

    RangeSeekBar rangeSeekBar;
    private TextView mtv, mtv2;


    public static int sub_where;

    //임시메일

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subway_budget);

        sub_where=0;//

        Intent getintent = getIntent();
        final String job_regisPro = getintent.getStringExtra("job");

        rangeSeekBar = (RangeSeekBar)findViewById(R.id.rangeseekbar);
        rangeSeekBar.setSelectedMaxValue(10);
        rangeSeekBar.setSelectedMinValue(0);
        mtv = (TextView)findViewById(R.id.tv);
        mtv2 = (TextView)findViewById(R.id.tv2);

        date = findViewById(R.id.date);
        intro = findViewById(R.id.intro);
        btn_next = findViewById(R.id.btn_next);
        et_subway = findViewById(R.id.et_subway);

        saved = new ArrayList<Subway>();
        listView = (ListView) findViewById(R.id.addList);

        adapter= new locationAdapter(getApplicationContext(),saved);

        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();



        date.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        new DatePickerDialog(SubwayBudget.this, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                        break;
                    }
                }
                return false;
            }

        });

        et_subway.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        Intent intent = new Intent(view.getContext(), SearchSubwayActivity.class);
                        startActivity(intent);
                        setListViewHeightBasedOnChildren(listView);
                        break;

                    }
                }
                adapter.notifyDataSetChanged();
                return false;

            }
        });



        rangeSeekBar.setOnRangeSeekBarChangeListener(new RangeSeekBar.OnRangeSeekBarChangeListener() {
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


        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text="";

                for(int i=0;i<saved.size();i++){
                    String tmp=saved.get(i).getSub_ID();
                    text= text.concat(tmp+" ");

                }

                // 받아오기
                String Date = date.getText().toString();  ///기간
                String Intro = intro.getText().toString();///소개글
                String Mtv = mtv.getText().toString();   ///최소예산
                String Mtv2 = mtv2.getText().toString();  ///최대예산

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                Intent intent = new Intent(SubwayBudget.this, HouseType.class);
                                intent.putExtra("job",job_regisPro);
                                SubwayBudget.this.startActivity(intent);
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(SubwayBudget.this);
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

                //원래는 MyEmailTest 대신 MyEmail로 해야함
                locationRequest locationRequest = new locationRequest(MyEmail, text, Date, Intro, Mtv, Mtv2, responseListener);
                RequestQueue queue = Volley.newRequestQueue(SubwayBudget.this);
                queue.add(locationRequest);



            }
        });

    }
    private void updateLabel() {
        String myFormat = "yyyy/MM/dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);

        EditText et_date = (EditText) findViewById(R.id.date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        if (adapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;

        int desiredWidth = View.MeasureSpec.makeMeasureSpec(listView.getWidth(), View.MeasureSpec.AT_MOST);
        for (int i = 0; i < adapter.getCount(); i++) {
            View listItem = adapter.getView(i, null, listView);
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