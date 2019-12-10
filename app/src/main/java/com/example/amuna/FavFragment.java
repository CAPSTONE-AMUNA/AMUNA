package com.example.amuna;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import static com.example.amuna.LoginSplash.Email;
import static com.example.amuna.LoginSplash.userList;
import static com.example.amuna.LoginSplashFav.favList;
import static com.example.amuna.LoginSplashID.MyKey;

public class FavFragment extends Fragment {
    String MyListw="";
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_fav, container, false);


        final ListView listView = (ListView) layout.findViewById(R.id.listview2);
        final ListAdapter adapter = new ListAdapter(getActivity(), favList);

        listView.setAdapter(adapter);


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ProfileSplash.class);
                intent.putExtra("jjim", "1");
                intent.putExtra("_Key", favList.get(position).getMember_id());
                intent.putExtra("Nickname", favList.get(position).getMember_Nickname());
                intent.putExtra("Job", favList.get(position).getMember_Job());
                intent.putExtra("Age", favList.get(position).getMember_Age());
                intent.putExtra("LimitBudget", favList.get(position).getMember_LimitBudget());
                intent.putExtra("Foreigner", favList.get(position).getMember_Foreigner());
                intent.putExtra("Intro", favList.get(position).getMember_Intro());
                intent.putExtra("PhotoURL", favList.get(position).getMember_PhotoURL());
                startActivity(intent);

            }
        });

        final String Mylist = getActivity().getIntent().getStringExtra("_Key");

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                MyListw=favList.get(position).getMember_id();
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("해당 리스트 삭제");
                builder.setMessage("해당 리스트를 삭제합니다.");
                builder.setPositiveButton("확인",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                favList.remove(position);
                                adapter.notifyDataSetChanged();
                                new BackgroundTask().execute();//favorite삭제

                                Toast.makeText(getActivity().getApplicationContext(), "삭제되었습니다.", Toast.LENGTH_LONG).show();
                            }
                        });
                builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getActivity().getApplicationContext(), "삭제가 취소되었습니다.", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();
                return true;
            }
        });
        return layout;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute(){
            target = "http://matehunter.cafe24.com/delete.php?MyKey="+MyKey+"&Mylist="+MyListw;
            System.out.println(target);
        }

        @Override
        protected String doInBackground(Void... voids){
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            } catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public void onProgressUpdate(Void... values){
            super.onProgressUpdate(values);
        }


        @Override
        //php 연결되면 ManagementActivity 로 전환
        public void onPostExecute(String result){

            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String id;
                while(count< jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    // MyKey = object.getString("_Key");

                    count++;
                }

            }catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}