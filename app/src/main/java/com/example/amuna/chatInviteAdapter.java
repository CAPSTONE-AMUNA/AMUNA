package com.example.amuna;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static com.example.amuna.LoginSplashID.MyKey;
import static com.example.amuna.ChatInviteActivity.YourKey;

public class chatInviteAdapter extends BaseAdapter {
    private Context context;
    private List<Data> dataList;
    String OKeyList;
    LayoutInflater inflater = null;

    public chatInviteAdapter(Context context, List<Data> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        Thread mThread2 = new Thread() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://matehunter.cafe24.com/overlapList.php?MyKey="+MyKey+"&YourKey="+YourKey);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String temp;
                    while((temp = bufferedReader.readLine()) != null)
                    {
                        OKeyList = temp;
                    }
                    Log.e("OKey 리스트",OKeyList);
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        };
        mThread2.start();

        return convertView;
    }


}