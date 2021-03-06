package com.example.amuna;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    private TextView et_email;
    private EditText et_pass,et_repass,et_name;
    private Button btn_register1, btn_cancel_register;
    private RadioGroup rg_gender;
    private int Gender=2;
    public static String MyEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Intent intent = getIntent();
        MyEmail = intent.getExtras().getString("Email");
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_pass = findViewById(R.id.et_pass);
        et_repass = findViewById(R.id.et_repass);
        rg_gender = findViewById(R.id.rg_gender);

        btn_cancel_register = findViewById(R.id.btn_cancel_register);

        btn_cancel_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
            }
        });

        et_email.setText(MyEmail);
        et_email.setTextSize(TypedValue.COMPLEX_UNIT_SP,20);
        rg_gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_gender_m://남자->0
                        Gender = 0;
                        break;
                    case R.id.rb_gender_w://여자->1
                        Gender = 1;
                        break;
                }
            }
        });

        btn_register1 = findViewById(R.id.btn_register1);

        btn_register1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = et_email.getText().toString();
                final String Name = et_name.getText().toString();
                final String Password = et_pass.getText().toString();
                final String RePassword = et_repass.getText().toString();

                if(Name == null || Name.equals("") ||Password == null || Password.equals("") ||RePassword == null || RePassword.equals("") || Gender == 2){
                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    builder.setMessage("빈 항목이 있습니다\n입력을 완료해주세요");
                    builder.setPositiveButton("확인", null);
                    AlertDialog dialog = builder.show();
                    dialog.show();
                }else{
                    PasswordEncryption passwordEncryption = new PasswordEncryption();
                    String pass_hash = passwordEncryption.encrypt(Password);

                    if(!Password.equals(RePassword)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                        builder.setMessage("비밀번호가 일치하지 않습니다");
                        builder.setPositiveButton("확인", null);
                        AlertDialog dialog = builder.show();
                        dialog.show();
                        et_pass.setText("");
                        et_repass.setText("");
                        et_pass.requestFocus();
                        return;
                    }

                    Response.Listener<String> responseListener = new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            try
                            {
                                JSONObject jsonResponse = new JSONObject(response);
                                boolean success = jsonResponse.getBoolean("success");
                                if(success){
                                    Intent intent = new Intent(RegisterActivity.this, RegisterProfileActivity.class);
                                    RegisterActivity.this.startActivity(intent);
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                    builder.setMessage("회원가입 실패")
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
                    RegisterRequest registerRequest = new RegisterRequest(Email, pass_hash, Name, Gender, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

    }
}