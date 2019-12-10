package com.example.amuna;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.example.amuna.LoginActivity.MMMMMMMyEmail;
import static com.example.amuna.LoginSplash.where;
import static com.example.amuna.RegisterProfileActivity.isValidCellPhoneNumber;

public class EditBasicTestActivity extends AppCompatActivity {

    private EditText et_nickname, et_phoneNum, et_etc;
    public static EditText et_univ2;
    private TextView tv_univ, tv_etc;
    private Button btn_registerPf1;
    private Spinner sp_job;
    private String n_nickname,n_phoneNum,n_univ_name, n_job;
    String mKey,mNickname,mPhoneNum,mJob,mUniv_Name,mPhotoURL;
    private String jikjang="직장인",daehack="대학(원)생",etc="기타";
    int setjob, if_first=0;
    public Button selectImage;

    private static final int RESULT_SELECT_IMAGE = 1;
    public String SERVER2 = "http://matehunter.cafe24.com/upload.php?Email="+MMMMMMMyEmail,
            timestamp;


    Main2Activity main2Activity = (Main2Activity) Main2Activity.main2activity;
    LoginSplashFav loginSplashFav = (LoginSplashFav) LoginSplashFav.loginsplashfav;
    LoginSplashID loginSplashID = (LoginSplashID) LoginSplashID.loginsplashID;
    LoginSplash loginSplash = (LoginSplash) LoginSplash.loginsplash;
    EditBasicSplash editBasicSplash = (EditBasicSplash) EditBasicSplash.editbasicsplash;

    android.graphics.Bitmap Bitmap2;
    ImageView UserImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_basic_test);

        Intent intent = getIntent();
        mKey = intent.getExtras().getString("Key");
        mNickname = intent.getExtras().getString("Nickname");
        mPhoneNum = intent.getExtras().getString("PhoneNum");
        mJob = intent.getExtras().getString("Job");
        mUniv_Name = intent.getExtras().getString("Univ_Name");
        mPhotoURL = intent.getExtras().getString("PhotoURL");

        //layout 연결
        et_nickname = findViewById(R.id.et_nickname);
        et_phoneNum = findViewById(R.id.et_phoneNum);
        et_etc = findViewById(R.id.et_etc);
        et_univ2 = findViewById(R.id.et_univ);
        tv_univ = findViewById(R.id.tv_univ);
        tv_etc = findViewById(R.id.tv_etc);
        btn_registerPf1 = findViewById(R.id.btn_registerPf1);
        sp_job = findViewById(R.id.sp_job);
        UserImage = (ImageView) findViewById(R.id.UserImage);
        selectImage = (Button) findViewById(R.id.selectImage);

        //intent로 받아온 값 기본 설정
        //사진
        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL(mPhotoURL);

                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    conn1.setDoInput(true);
                    conn1.connect();
                    InputStream is1 = conn1.getInputStream();

                    Bitmap2 = BitmapFactory.decodeStream(is1);
                    Log.e("사진 선택전","=>"+Bitmap2);


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
            UserImage.setImageBitmap(Bitmap2);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        et_nickname.setText(mNickname);
        et_phoneNum.setText(mPhoneNum);


        if(!mUniv_Name.equals("무대학"))
            et_univ2.setText(mUniv_Name);

        if(mJob.equals(jikjang)){
            setjob=1;
        }else if(mJob.equals(daehack)){
            setjob=2;
        }else{
            setjob=3;
            et_etc.setText(mJob);
        }
        sp_job.setSelection(setjob);

        //새로 선택
        sp_job.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if_first++;
                if(if_first!=1){
                    et_univ2.setText("");
                    et_etc.setText("");
                }
                if(adapterView.getItemAtPosition(i).toString().equals(jikjang)){
                    tv_univ.setVisibility(View.INVISIBLE);
                    et_univ2.setVisibility(View.INVISIBLE);
                    tv_etc.setVisibility(View.INVISIBLE);
                    et_etc.setVisibility(View.INVISIBLE);
                }else if(adapterView.getItemAtPosition(i).toString().equals(daehack)){
                    tv_univ.setVisibility(View.VISIBLE);
                    et_univ2.setVisibility(View.VISIBLE);
                    tv_etc.setVisibility(View.INVISIBLE);
                    et_etc.setVisibility(View.INVISIBLE);
                }else if(adapterView.getItemAtPosition(i).toString().equals(etc)){
                    tv_etc.setVisibility(View.VISIBLE);
                    et_etc.setVisibility(View.VISIBLE);
                    tv_univ.setVisibility(View.INVISIBLE);
                    et_univ2.setVisibility(View.INVISIBLE);
                }else{
                    tv_univ.setVisibility(View.INVISIBLE);
                    et_univ2.setVisibility(View.INVISIBLE);
                    tv_etc.setVisibility(View.INVISIBLE);
                    et_etc.setVisibility(View.INVISIBLE);
                }
                n_job = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        et_univ2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction()){
                    case MotionEvent.ACTION_DOWN:{
                        Intent intent = new Intent(view.getContext(), SearchUniversityActivity.class);
                        intent.putExtra("hoho",1);
                        startActivity(intent);
                        break;
                    }
                }
                return false;
            }
        });

        //새로 사진
        selectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //call the function to select image from album
                selectImage();
            }
        });

        //다음 버튼 클릭
        btn_registerPf1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //사진
                android.graphics.Bitmap image = ((BitmapDrawable) UserImage.getDrawable()).getBitmap();
                Log.e("새로 선택","=>"+image);
                //execute the async task and upload the image to server
                new Upload(image,"IMG_"+timestamp).execute();

                //닉네임 받아오기
                n_nickname = et_nickname.getText().toString();

                //핸드폰 번호 유효성 검사
                if(isValidCellPhoneNumber(et_phoneNum.getText().toString())){
                    n_phoneNum = et_phoneNum.getText().toString();
                }else{
                    Toast.makeText(EditBasicTestActivity.this, "핸드폰 번호를 다시 입력해주세요.", Toast.LENGTH_SHORT).show();
                    et_phoneNum.setText("");
                    et_phoneNum.requestFocus();
                    return;
                }

                //직업 받아오기
                if(n_job.equals("직업")){
                    Toast.makeText(getApplicationContext(), "직업을 다시 선택해주세요.", Toast.LENGTH_LONG).show();
                    return;
                }else if(n_job.equals("기타")){
                    n_job = et_etc.getText().toString();
                    n_univ_name = "무대학";
                }else if(n_job.equals("대학(원)생")){
                    n_job = daehack;
                    n_univ_name = et_univ2.getText().toString();
                }else{
                    n_job = jikjang;
                    n_univ_name = "무대학";
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try
                        {
                            JSONObject jsonResponse = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                            boolean success = jsonResponse.getBoolean("success");
                            if(success){
                                where=4;
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditBasicTestActivity.this);
                                builder.setMessage("기본정보 수정 완료")
                                        .setPositiveButton("확인",null)
                                        .create()
                                        .show();
                                finish();
                                editBasicSplash.finish();
                                main2Activity.finish();
                                loginSplashFav.finish();
                                loginSplashID.finish();
                                loginSplash.finish();
                                Intent intent = new Intent(EditBasicTestActivity.this, LoginSplash.class);
                                startActivity(intent);
                            }
                            else
                            {
                                AlertDialog.Builder builder = new AlertDialog.Builder(EditBasicTestActivity.this);
                                builder.setMessage("기본정보 변경 실패")
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
                Log.e("수정것",""+mKey+" "+n_nickname+" "+n_phoneNum+"/"+n_job+"/"+n_univ_name);
                EditBasicRequest registerRequest = new EditBasicRequest(mKey, n_nickname, n_phoneNum, n_job, n_univ_name, responseListener);
                RequestQueue queue = Volley.newRequestQueue(EditBasicTestActivity.this);
                queue.add(registerRequest);
            }


        });
    }

    private void selectImage(){
        //open album to select image
        Intent gallaryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(gallaryIntent, RESULT_SELECT_IMAGE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_SELECT_IMAGE && resultCode == RESULT_OK && data != null){
            //set the selected image to image variable
            Uri image = data.getData();
            UserImage.setImageURI(image);

            //get the current timeStamp and strore that in the time Variable
            Long tsLong = System.currentTimeMillis() / 1000;
            timestamp = tsLong.toString();

            Toast.makeText(getApplicationContext(),timestamp,Toast.LENGTH_SHORT).show();}

        else if (data == null){
            UserImage.setImageResource(R.drawable.profile);
        }
    }

    //async task to upload image
    private class Upload extends AsyncTask<Void,Void,String> {
        private Bitmap image;
        private String name;

        public Upload(Bitmap image,String name){
            this.image = image;
            this.name = name;
        }

        @Override
        protected String doInBackground(Void... params) {
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //compress the image to jpg format
            image.compress(Bitmap.CompressFormat.JPEG,10,byteArrayOutputStream);
            /*
             * encode image to base64 so that it can be picked by saveImage.php file
             * */
            String encodeImage = Base64.encodeToString(byteArrayOutputStream.toByteArray(),Base64.DEFAULT);

            //generate hashMap to store encodedImage and the name
            HashMap<String,String> detail = new HashMap<>();
            detail.put("name", name);
            detail.put("image", encodeImage);

            try{
                //convert this HashMap to encodedUrl to send to php file
                String dataToSend = hashMapToUrl(detail);
                //make a Http request and send data to saveImage.php file
                String response = imageRequest.post(SERVER2,dataToSend);

                //return the response
                return response;

            }catch (Exception e){
                e.printStackTrace();
                //Log.e(TAG,"ERROR  "+e);
                return null;
            }
        }



        @Override
        protected void onPostExecute(String s) {
            //show image uploaded
            Toast.makeText(getApplicationContext(),"Image Uploaded",Toast.LENGTH_SHORT).show();
        }
    }

    private String hashMapToUrl(HashMap<String, String> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for(Map.Entry<String, String> entry : params.entrySet()){
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }

        return result.toString();
    }
}
