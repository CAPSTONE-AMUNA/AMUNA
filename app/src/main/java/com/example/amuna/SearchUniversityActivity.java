package com.example.amuna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.amuna.EditBasicTestActivity.et_univ2;
import static com.example.amuna.RegisterProfileActivity.et_univ;

public class SearchUniversityActivity extends AppCompatActivity {

    private static String TAG = "phptest_SearchUniversityActivity";

    private TextView mTextViewResult;
    private EditText et_search_univ;
    private static final String TAG_JSON="univList";
    private static final String TAG_NAME = "Univ_Name";
    private ArrayList<HashMap<String, String>> mArrayList;
    private List<String> list;
    private ArrayList<String> arraylist;
    private ListView mlistView;
    private String mJsonString;
    private ListAdapter adapter;

    public static String tt;
    public EditText getEtUniv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_university);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_register_profile, null);

        Intent intent = getIntent();
        final int fromActivity = intent.getExtras().getInt("hoho");

        getEtUniv = view.findViewById(R.id.et_univ);

        list = new ArrayList<String>();

        arraylist = new ArrayList<String>();

        mTextViewResult = findViewById(R.id.textView_main_result);
        mlistView = findViewById(R.id.univListView);
        mArrayList = new ArrayList<>();
        et_search_univ = findViewById(R.id.et_search_univ);

        GetData task = new GetData();
        task.execute("http://matehunter.cafe24.com/univListTest.php");


        et_search_univ.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String text = et_search_univ.getText().toString();
                search(text);
            }
        });

        getEtUniv.setText(tt);

        mlistView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(SearchUniversityActivity.this, list.get(i), Toast.LENGTH_SHORT).show();
                    if(fromActivity==0){
                        et_univ.setText(list.get(i));
                    }else if(fromActivity==1){
                        et_univ2.setText(list.get(i));
                }

                finish();
            }
        });
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        adapter = new SearchUniversityAdapter(list, this);
        mlistView.setAdapter(adapter);

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {
//            list.addAll(arraylist);
        }
        // 문자 입력을 할때..
        else
        {
            // 리스트의 모든 데이터를 검색한다.
            for(int i = 0;i < arraylist.size(); i++)
            {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText))
                {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        ((BaseAdapter)adapter).notifyDataSetChanged();
    }

    private class GetData extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SearchUniversityActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            mTextViewResult.setText(result);
            Log.d(TAG, "response  - " + result);

            if (result == null){

                mTextViewResult.setText(errorString);
            }
            else {

                mJsonString = result;
                showResult();
            }
        }


        @Override
        protected String doInBackground(String... params) {

            String serverURL = params[0];


            try {

                URL url = new URL(serverURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                httpURLConnection.setReadTimeout(5000);
                httpURLConnection.setConnectTimeout(5000);
                httpURLConnection.connect();


                int responseStatusCode = httpURLConnection.getResponseCode();
                Log.d(TAG, "response code - " + responseStatusCode);

                InputStream inputStream;
                if(responseStatusCode == HttpURLConnection.HTTP_OK) {
                    inputStream = httpURLConnection.getInputStream();
                }
                else{
                    inputStream = httpURLConnection.getErrorStream();
                }


                InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                StringBuilder sb = new StringBuilder();
                String line;

                while((line = bufferedReader.readLine()) != null){
                    sb.append(line);
                }


                bufferedReader.close();


                return sb.toString().trim();


            } catch (Exception e) {

                Log.d(TAG, "InsertData: Error ", e);
                errorString = e.toString();

                return null;
            }

        }
    }


    private void showResult(){

        HashMap<String,String> hashMap;

        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String name = item.getString(TAG_NAME);

                hashMap = new HashMap<>();

                hashMap.put(TAG_NAME, name);

                if(!name.equals("무대학")) {
                    list.add(name);
                }

                if(!name.equals("무대학")) {
                    mArrayList.add(hashMap);
                }

            }

            Log.e("리스트","리스트 출력"+list);
            arraylist.addAll(list);

            adapter = new SimpleAdapter(
                    SearchUniversityActivity.this, mArrayList, R.layout.listview_univ_item,
                    new String[]{TAG_NAME},
                    new int[]{R.id.univName}
            );

//            mlistView.setAdapter(adapter);

        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}
