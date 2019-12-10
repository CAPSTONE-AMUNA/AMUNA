package com.example.amuna;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static com.example.amuna.EditBudgetActivity.setListViewHeightBasedOnChildren2;
import static com.example.amuna.SubwayBudget.listView;

public class SearchSubwayActivity extends AppCompatActivity {

    public EditText getEtSub;

    private static String TAG = "phptest_SearchSubwayActivity";

    private static final String TAG_JSON="subList";
    private static final String TAG_SUB_ID = "Sub_ID";
    private static final String TAG_SUB_NAME = "Sub_Name";
    private static final String TAG_SUB_LINE = "Sub_Line";
    private TextView mTextViewResult;
    public static RecyclerView recyclerView;
    String mJsonString;
    ArrayList<Subway> subList;
    EditText et_search_subway;
    SearchSubwayAdapter adapter;
    public static Activity _SearchSubway_Activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_subway);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.activity_subway_budget, null);
        getEtSub = view.findViewById(R.id.et_subway);

        subList=new ArrayList<>();

        et_search_subway  = findViewById(R.id.et_search_subway);
        mTextViewResult = findViewById(R.id.textView_main_result);
        recyclerView = findViewById(R.id.recycler_sub_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        _SearchSubway_Activity = SearchSubwayActivity.this;

        GetData task = new GetData();
        task.execute("http://matehunter.cafe24.com/subList.php");

        recyclerView.setVisibility(View.INVISIBLE);

        et_search_subway.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = et_search_subway.getText().toString();
                if(text.equals("")){
                    recyclerView.setVisibility(View.INVISIBLE);
                }else{
                    recyclerView.setVisibility(View.VISIBLE);
                    adapter.getFilter().filter(text);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }


    private class GetData extends AsyncTask<String, Void, String> {
        ProgressDialog progressDialog;
        String errorString = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = ProgressDialog.show(SearchSubwayActivity.this,
                    "Please Wait", null, true, true);
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            progressDialog.dismiss();
            //  mTextViewResult.setText(result);
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
        try {
            JSONObject jsonObject = new JSONObject(mJsonString);
            JSONArray jsonArray = jsonObject.getJSONArray(TAG_JSON);

            for(int i=0;i<jsonArray.length();i++){

                JSONObject item = jsonArray.getJSONObject(i);

                String sub_ID =item.getString(TAG_SUB_ID);

                String sub_name = item.getString(TAG_SUB_NAME);
                String sub_line = item.getString(TAG_SUB_LINE);

                Subway sub = new Subway(sub_ID,sub_name,sub_line);
                subList.add(sub);
                setListViewHeightBasedOnChildren2(listView);

            }

            adapter = new SearchSubwayAdapter(subList);

            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        } catch (JSONException e) {

            Log.d(TAG, "showResult : ", e);
        }

    }

}