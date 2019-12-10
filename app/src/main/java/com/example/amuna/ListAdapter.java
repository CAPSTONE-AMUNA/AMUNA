package com.example.amuna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ListAdapter extends BaseAdapter {
    private Context context;
    private List<Data> dataList;
    LayoutInflater inflater = null;
    Bitmap Bitmap;



    public ListAdapter(Context context, List<Data> dataList) {
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
        final String PhotoURL = dataList.get(position).getMember_PhotoURL();

        if (convertView == null) {
            final Context context = parent.getContext();
            if (inflater == null) {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.listview_item, parent, false);


        }
////////////////////////////////////////////////////////////////////////////수정


        TextView Nickname = (TextView) convertView.findViewById(R.id.textNick);
        TextView Job = (TextView) convertView.findViewById(R.id.textJob);
        TextView Age = (TextView) convertView.findViewById(R.id.textAge);
        ImageView imageView = (ImageView) convertView.findViewById(R.id.imageView2);


        Nickname.setText(dataList.get(position).getMember_Nickname());
        Job.setText(dataList.get(position).getMember_Job());
        Age.setText(dataList.get(position).getMember_Age());
////////////////////////////////////////////////////////////////////////////////////////////
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
                    ;
                }

            }
        };

        mThread.start();

        try {
            mThread.join();
            imageView.setImageBitmap(Bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        convertView.setTag(dataList.get(position).getMember_id());
        return convertView;

    }


}

