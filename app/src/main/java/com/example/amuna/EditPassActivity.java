package com.example.amuna;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.example.amuna.LoginSplash.Email;

public class EditPassActivity extends AppCompatActivity {

    private EditText et_pass,et_repass;
    private Button btn_editpass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pass);

        et_pass = findViewById(R.id.et_pass);
        et_repass = findViewById(R.id.et_repass);
        btn_editpass = findViewById(R.id.btn_editpass);

        btn_editpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Password = et_pass.getText().toString();
                final String RePassword = et_repass.getText().toString();

                if(Password == null || Password.equals("") ||RePassword == null || RePassword.equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPassActivity.this);
                    builder.setMessage("빈 항목이 있습니다\n입력을 완료해주세요");
                    builder.setPositiveButton("확인", null);
                    AlertDialog dialog = builder.show();
                    dialog.show();
                }else{
                    PasswordEncryption passwordEncryption = new PasswordEncryption();
                    String pass_hash = passwordEncryption.encrypt(Password);

                    if(!Password.equals(RePassword)){
                        AlertDialog.Builder builder = new AlertDialog.Builder(EditPassActivity.this);
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
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPassActivity.this);
                                    builder.setMessage("비밀번호가 변경되었습니다.")
                                            .setPositiveButton("확인", null)
                                            .create()
                                            .show();
                                }
                                else
                                {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(EditPassActivity.this);
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
                    EditPassRequest registerRequest = new EditPassRequest(Email, pass_hash, responseListener);
                    RequestQueue queue = Volley.newRequestQueue(EditPassActivity.this);
                    queue.add(registerRequest);
                }
            }
        });
    }
}
