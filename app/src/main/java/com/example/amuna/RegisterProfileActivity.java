package com.example.amuna;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.amuna.RegisterActivity.MyEmail;

public class RegisterProfileActivity extends AppCompatActivity {

    private EditText et_nickname,et_birth,et_phoneNum, et_etc;
    public static EditText et_univ;
    private TextView tv_univ, tv_etc;
    private Button btn_registerPf1, btn_cancel_register;
    private RadioGroup rg_korf;
    private Spinner sp_job;
    private int Nationality=2;
    private int age=0;
    private String phoneNum, print_birth="", univ_name, job;

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth){
            Log.d("BirthPickerTest", "year = " + year + ", month = " + monthOfYear + ", day = " + dayOfMonth);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_profile);

        et_nickname = findViewById(R.id.et_nickname);
        et_birth = findViewById(R.id.et_birth);
        et_phoneNum = findViewById(R.id.et_phoneNum);
        tv_univ = findViewById(R.id.tv_univ);
        et_univ = findViewById(R.id.et_univ);
        tv_etc = findViewById(R.id.tv_etc);
        et_etc = findViewById(R.id.et_etc);

        rg_korf = findViewById(R.id.rg_korf);
        btn_registerPf1 = findViewById(R.id.btn_registerPf1);
        sp_job = findViewById(R.id.sp_job);

        btn_cancel_register = findViewById(R.id.btn_cancel_register);

        btn_cancel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterProfileActivity.this, LoginActivity.class);
                RegisterProfileActivity.this.startActivity(intent);
            }
        });

        //내/외국인 여부 int형으로
        rg_korf.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_korean://내국인->0
                        Nationality = 0;
                        break;
                    case R.id.rb_foreigner://외국인->1
                        Nationality = 1;
                        break;
                }
            }
        });

        //생년월일 dialog에서 선택
        et_birth.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        BirthPickerDialog pd = new BirthPickerDialog();
                        pd.setListener(d);
                        pd.show(getSupportFragmentManager(), "BirthPickerTest");
                        pd.setDialogResult(new BirthPickerDialog.OnMyDialogResult() {
                            @Override
                            public void finish(String result) {
                                print_birth = result.substring(0,4) + "-" + result.substring(4,6) + "-" + result.substring(6);
                                et_birth.setText(print_birth);
                            }
                        });
                        break;
                    }
                }
                return false;
            }
        });

        //직업 선택
        sp_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if(adapterView.getItemAtPosition(i).toString().equals("직장인")){
                    tv_univ.setVisibility(View.INVISIBLE);
                    et_univ.setVisibility(View.INVISIBLE);
                    tv_etc.setVisibility(View.INVISIBLE);
                    et_etc.setVisibility(View.INVISIBLE);
                }else if(adapterView.getItemAtPosition(i).toString().equals("대학(원)생")){
                    tv_univ.setVisibility(View.VISIBLE);
                    et_univ.setVisibility(View.VISIBLE);
                    tv_etc.setVisibility(View.INVISIBLE);
                    et_etc.setVisibility(View.INVISIBLE);
                    et_univ.setText("");
                    et_univ.requestFocus();
                }else if(adapterView.getItemAtPosition(i).toString().equals("기타")){
                    tv_etc.setVisibility(View.VISIBLE);
                    et_etc.setVisibility(View.VISIBLE);
                    tv_univ.setVisibility(View.INVISIBLE);
                    et_univ.setVisibility(View.INVISIBLE);
                    et_etc.setText("");
                    et_etc.requestFocus();
                }else{
                    tv_univ.setVisibility(View.INVISIBLE);
                    et_univ.setVisibility(View.INVISIBLE);
                    tv_etc.setVisibility(View.INVISIBLE);
                    et_etc.setVisibility(View.INVISIBLE);
                }
                job = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_univ.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        Intent intent = new Intent(view.getContext(), SearchUniversityActivity.class);
                        intent.putExtra("hoho",0);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });


        //다음 버튼 클릭 시
        btn_registerPf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //닉네임 받아오기
                String nickname = et_nickname.getText().toString();

                //나이 계산
                if(!print_birth.equals(""))
                    age = getAge(Integer.parseInt(print_birth.substring(0,4)));

                /*
                //핸드폰 번호 유효성 검사
                if(isValidCellPhoneNumber(et_phoneNum.getText().toString())){
                    phoneNum = et_phoneNum.getText().toString();
                }else{
                    Toast.makeText(RegisterProfileActivity.this, "핸드폰 번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_phoneNum.setText("");
                    et_phoneNum.requestFocus();
                    return;
                }
                 */

                //직업 받아오기
                if(job.equals("선택")){
                    Toast.makeText(getApplicationContext(), "직업을 다시 선택해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }else if(job.equals("기타")){
                    job = et_etc.getText().toString();
                    univ_name = "무대학";
                }else if(job.equals("대학(원)생")){
                    univ_name = et_univ.getText().toString();
                }else{
                    univ_name = "무대학";
                }

                if(nickname == null || nickname.equals("") || Nationality == 2 || print_birth == null || print_birth.equals("")  || job == null || job.equals("") || job.equals("기타")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProfileActivity.this);
                    builder.setMessage("빈 항목이 있습니다\n입력을 완료해주세요");
                    builder.setPositiveButton("확인", null);
                    AlertDialog dialog = builder.show();
                    dialog.show();
                }else{
                    if(job.equals("대학(원)생")) {
                        if(univ_name == null || univ_name.equals("")){
                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProfileActivity.this);
                            builder.setMessage("빈 항목이 있습니다\n입력을 완료해주세요");
                            builder.setPositiveButton("확인", null);
                            AlertDialog dialog = builder.show();
                            dialog.show();
                        }
                    }
                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Intent intent = new Intent(RegisterProfileActivity.this, SubwayBudget.class);
                                    intent.putExtra("job",job);
                                    RegisterProfileActivity.this.startActivity(intent);
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterProfileActivity.this);
                                    builder.setMessage("프로필가입실패")
                                            .setNegativeButton("다시시도",null)
                                            .create()
                                            .show();
                                }
                            }
                            catch (JSONException e)
                            {
                                e.printStackTrace();
                            }
                        }

                    };
                    Log.e("request전"," regisEmail"+MyEmail+" nickname"+nickname+" Nationality"+Nationality+" age"+age+" phoneNum"+phoneNum+" job"+job+" univ_name"+univ_name);
                    RegisterProfileRequest registerRequest = new RegisterProfileRequest(MyEmail, nickname, Nationality, age, "01000000000", job, univ_name, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterProfileActivity.this);
                    queue.add(registerRequest);
                }

            }
        });

    }


    //핸드폰 유효성 검사 함수
    public static boolean isValidCellPhoneNumber(String cellphoneNumber) {

        boolean returnValue = false;
        try {
            String regex = "^01(?:0|1|[6-9])(\\d{3}|\\d{4})(\\d{4})$";

            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(cellphoneNumber);
            if (m.matches()) {
                returnValue = true;
            }

            if (returnValue && cellphoneNumber != null
                    && cellphoneNumber.length() > 0
                    && cellphoneNumber.startsWith("010")) {
                cellphoneNumber = cellphoneNumber.replace("-", "");
                if (cellphoneNumber.length() != 11) {
                    returnValue = false;
                }
            }
            return returnValue;
        } catch (Exception e) {
            return false;
        }

    }

    //나이 계산 함수
    public int getAge(int birthYear)
    {
        Calendar current = Calendar.getInstance();
        int currentYear  = current.get(Calendar.YEAR);

        int age = currentYear - birthYear + 1;

        return age;
    }

    @Override
    public void onBackPressed() {

    }
}