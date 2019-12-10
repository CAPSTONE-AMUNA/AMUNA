package com.example.amuna;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SuccessRegisterActivity extends AppCompatActivity {

    private Button btn_toLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success_register);

        btn_toLogin = findViewById(R.id.btn_toLogin);

        btn_toLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SuccessRegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
