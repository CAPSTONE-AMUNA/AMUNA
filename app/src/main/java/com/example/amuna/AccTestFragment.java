package com.example.amuna;


import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SwitchCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.support.v7.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.rey.material.widget.Switch;

import static com.example.amuna.ChatFragment.Prime;
import static com.example.amuna.LoginSplash.where;
import static com.example.amuna.LoginSplashID.MyKey;


public class AccTestFragment extends Fragment {

    View layout;
    android.graphics.Bitmap Bitmap;
    ImageView MyImage;
    TextView MyNickname;
    String Key,Nickname,PhotoURL;
    String DateLen;
    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = firebaseDatabase.getReference();
    private List listA;

    LoginSplashFav loginSplashFav = (LoginSplashFav) LoginSplashFav.loginsplashfav;
    LoginSplashID loginSplashID = (LoginSplashID) LoginSplashID.loginsplashID;
    LoginSplash loginSplash = (LoginSplash) LoginSplash.loginsplash;
    LoginSplashChatR loginsplashChatR = (LoginSplashChatR) LoginSplashChatR.loginsplashChatR;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layout = inflater.inflate(R.layout.fragment_acc_test, container, false);

        listA = new ArrayList();
        getChatList();
        new BackgroundTask().execute();
        return layout;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://matehunter.cafe24.com/accSplash.php?_Key=" + MyKey;
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
                    Key = object.getString("_Key");
                    Nickname = object.getString("Nickname");
                    PhotoURL = object.getString("PhotoURL");
                    DateLen = object.getString("Length");

                    count++;
                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            MyImage = layout.findViewById(R.id.iv_myProImg);
            MyNickname = layout.findViewById(R.id.tv_myNickname);


            MyNickname.setText(Nickname);

            SwitchCompat sw = layout.findViewById(R.id.s);
            ConstraintLayout clay1 = layout.findViewById(R.id.constraintLayout4);
            ConstraintLayout clay2 = layout.findViewById(R.id.constraintLayout5);
            ConstraintLayout clay3 = layout.findViewById(R.id.constraintLayout6);
            ConstraintLayout clay4 = layout.findViewById(R.id.constraintLayout7);
            ConstraintLayout clay5 = layout.findViewById(R.id.constraintLayout_jugeo);
            ConstraintLayout clay6 = layout.findViewById(R.id.constraintLayout9);
            ConstraintLayout clay7 = layout.findViewById(R.id.constraintLayout10);
            //스위치
            if(DateLen.equals("9")){

                sw.setChecked(true);
            }
            else{
                sw.setChecked(false);

            }
            sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    // 스위치 버튼이 체크되었는지 검사하여 텍스트뷰에 각 경우에 맞게 출력합니다.
                    if (isChecked){
                        Log.e("췍","활성");
                        Thread mThread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL("http://matehunter.cafe24.com/nomatching.php?Key="+MyKey);

                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                    InputStream inputStream = httpURLConnection.getInputStream();
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                    String temp;
                                    while((temp = bufferedReader.readLine()) != null)
                                    {
                                    }
                                    bufferedReader.close();
                                    inputStream.close();
                                    httpURLConnection.disconnect();


                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        mThread.start();
                        try {
                            mThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        where=4;
                        getActivity().finish();
                        loginsplashChatR.finish();
                        loginSplashFav.finish();
                        loginSplashID.finish();
                        loginSplash.finish();

                        Intent intent = new Intent(getActivity(), LoginSplash.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(0, 0);

                    }else{
                        Log.e("췍","비활성");
                        Thread mThread = new Thread() {
                            @Override
                            public void run() {
                                try {
                                    URL url = new URL("http://matehunter.cafe24.com/nomatching.php?Key="+MyKey);

                                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                                    InputStream inputStream = httpURLConnection.getInputStream();
                                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                    String temp;
                                    while((temp = bufferedReader.readLine()) != null)
                                    {
                                    }
                                    bufferedReader.close();
                                    inputStream.close();
                                    httpURLConnection.disconnect();


                                } catch (MalformedURLException e) {
                                    e.printStackTrace();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }

                            }
                        };

                        mThread.start();
                        try {
                            mThread.join();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        where=4;
                        getActivity().finish();
                        loginsplashChatR.finish();
                        loginSplashFav.finish();
                        loginSplashID.finish();
                        loginSplash.finish();
                        Intent intent = new Intent(getActivity(), LoginSplash.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(0, 0);
                    }
                }
            });

            View.OnClickListener clickListener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (v.getId()){
                        case R.id.constraintLayout4 ://비번 수정
                            Intent intent = new Intent(getActivity(), EditPassActivity.class);
                            AccTestFragment.this.startActivity(intent);
                            break;
                        case R.id.constraintLayout5 ://기본 수정
                            Intent intent2 = new Intent(getActivity(), EditBasicSplash.class);
                            AccTestFragment.this.startActivity(intent2);
                            break;
                        case R.id.constraintLayout6 ://추가 수정(지역,예산,,,)
                            Intent intent3 = new Intent(getActivity(), EditBudgetActivity.class);
                            AccTestFragment.this.startActivity(intent3);
                            break;
                        case R.id.constraintLayout_jugeo ://주거 유형 수정
                            Intent intent4 = new Intent(getActivity(), EditHouseActivity.class);
                            AccTestFragment.this.startActivity(intent4);
//                            getActivity().overridePendingTransition(0, 0);
                            break;
                        case R.id.constraintLayout7 ://성향 패턴 수정
                            Intent intent5 = new Intent(getActivity(), EditLifeCharActivity.class);
                            AccTestFragment.this.startActivity(intent5);
                            break;
                        case R.id.constraintLayout9 ://회원 탈퇴
                            AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                            dialog  .setMessage("정말로 계정을 삭제하시겠습니까?")
                                    .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                            for(int i=0;i<listA.size();i++){
                                                databaseReference.child("chat").child(""+listA.get(i)).setValue(null);
                                                databaseReference.child("chatroom").child(""+listA.get(i)).setValue(null);
                                            }

                                            Response.Listener<String> responseListener = new Response.Listener<String>() {
                                                @Override
                                                public void onResponse(String response) {
                                                    try
                                                    {
                                                        JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                                                        boolean success = jsonResponse.getBoolean("success");
                                                        if(success){
                                                            Toast.makeText(getActivity(), "탈퇴 처리되었습니다", Toast.LENGTH_LONG).show();
                                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                                            getActivity().startActivity(intent);
                                                        }
                                                    }
                                                    catch (JSONException e)
                                                    {
                                                        e.printStackTrace();
                                                    }
                                                }

                                            };
                                            Log.e("수정것",""+Key);
                                            DeleteAccRequest registerRequest = new DeleteAccRequest(Key, responseListener);
                                            RequestQueue queue = Volley.newRequestQueue(getActivity());
                                            queue.add(registerRequest);
                                        }
                                    })
                                    .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                        }
                                    })
                                    .create().show();
                            break;
                        case R.id.constraintLayout10 ://로그아웃
                            Intent Logout = new Intent(getActivity(), LoginActivity.class);
                            getActivity().startActivity(Logout);
                            break;
                    }
                }
            };

            clay1.setOnClickListener(clickListener);
            clay2.setOnClickListener(clickListener);
            clay3.setOnClickListener(clickListener);
            clay4.setOnClickListener(clickListener);
            clay5.setOnClickListener(clickListener);
            clay6.setOnClickListener(clickListener);
            clay7.setOnClickListener(clickListener);

            Thread mThread = new Thread() {
                @Override
                public void run() {
                    try {
                        URL url = new URL(PhotoURL);

                        HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                        conn1.setDoInput(true);
                        conn1.connect();
                        InputStream is1 = conn1.getInputStream();

                        Bitmap = BitmapFactory.decodeStream(is1);


                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();

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



        }
    }
    private void getChatList() {
        databaseReference.child("chatroom").orderByChild("member_Date").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                listA.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    ChatListData chatl = snapshot.getValue(ChatListData.class);
                    if(Integer.parseInt(chatl.getMember_RoomKey())%Prime[Integer.parseInt(MyKey)]==0) {
                        listA.add(chatl.getMember_RoomKey());
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}