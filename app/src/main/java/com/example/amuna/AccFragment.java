package com.example.amuna;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.example.amuna.LoginSplashID.MyImg;
import static com.example.amuna.LoginSplashID.MyNick;


public class AccFragment extends Fragment {

    View layout;
    android.graphics.Bitmap Bitmap;
    ImageView MyImage;
    TextView MyNickname;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_acc, container, false);

        MyImage = layout.findViewById(R.id.iv_myProImg);
        MyNickname = layout.findViewById(R.id.tv_myNickname);

        MyNickname.setText(MyNick);

        ConstraintLayout clay1 = layout.findViewById(R.id.constraintLayout4);
        ConstraintLayout clay2 = layout.findViewById(R.id.constraintLayout5);
        ConstraintLayout clay3 = layout.findViewById(R.id.constraintLayout6);
        ConstraintLayout clay4 = layout.findViewById(R.id.constraintLayout7);

        View.OnClickListener clickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.constraintLayout4 :
                        Log.d("log01","click layout01");
                        Intent intent = new Intent(getActivity(), EditPassActivity.class);
                        AccFragment.this.startActivity(intent);
                        break;
                    case R.id.constraintLayout5 :
                        Log.d("log01","click layout02");
                        Intent intent2 = new Intent(getActivity(), EditBasicSplash.class);
                        AccFragment.this.startActivity(intent2);
                        break;
                    case R.id.constraintLayout6 :
                        Log.d("log01","click layout03");
                        break;
                    case R.id.constraintLayout7 :
                        Log.d("log01","click layout04");
                        break;
                }
            }
        };

        clay1.setOnClickListener(clickListener);
        clay2.setOnClickListener(clickListener);
        clay3.setOnClickListener(clickListener);
        clay4.setOnClickListener(clickListener);

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(MyImg);

                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    conn1.setDoInput(true);
                    conn1.connect();
                    InputStream is1 = conn1.getInputStream();

                    Bitmap = BitmapFactory.decodeStream(is1);


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                    ;
                }

            }
        };

        mThread.start();

        try {
            mThread.join();
            MyImage.setImageBitmap(Bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return layout;
    }

}