package com.example.amuna;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import static com.example.amuna.LoginSplashID.MyKey;

public class ChattingAdapter extends BaseAdapter {
    private Context context;
    private List<ChatData> dataList;
    private Bitmap Bitmap;
    LayoutInflater inflater;
    private String Nick;
    ViewHolder viewHolder;


    public ChattingAdapter(Context context, List<ChatData> dataList) {
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
        ViewHolder holder;

        if (convertView == null)
        {
            final Context context = parent.getContext();
            if (inflater == null)
            {
                inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            }
            convertView = inflater.inflate(R.layout.chat_user_list, parent, false);

            viewHolder = new ViewHolder();
            holder = new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.imageView2);
            holder.msg = (TextView) convertView.findViewById(R.id.content);
            holder.Nickname = (TextView) convertView.findViewById(R.id.textNick);
            convertView.setTag(holder);

            RelativeLayout.LayoutParams mParam = new RelativeLayout.LayoutParams((int)(ViewGroup.LayoutParams.MATCH_PARENT),(int)( ViewGroup.LayoutParams.WRAP_CONTENT));
            convertView.setLayoutParams(mParam);
        } else {
            holder = (ViewHolder) convertView.getTag();
            System.out.println("getview:"+position+" "+convertView);
        }
////////////////////////////////////////////////////////////////////////////수정
        // View v = View.inflate(context, R.layout.user,null);


        //TextView Nickname = (TextView) convertView.findViewById(R.id.textNick);
        Thread mThread2 = new Thread() {
            @Override
            public void run() {
                try{
                    URL url = new URL("http://matehunter.cafe24.com/getyournickname.php?Key="+dataList.get(position).getMember_MyID());
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    String temp;
                    while((temp = bufferedReader.readLine()) != null)
                    {
                        Nick=temp;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                } catch(Exception e) {
                    e.printStackTrace();
                }

            }
        };
        mThread2.start();

        try {
            mThread2.join();
            holder.Nickname.setText(Nick);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    URL url = new URL("http://matehunter.cafe24.com/upload/"+dataList.get(position).getMember_MyID()+".jpg");

                    HttpURLConnection conn1 = (HttpURLConnection) url.openConnection();
                    conn1.setDoInput(true);
                    conn1.connect();
                    InputStream is1 = conn1.getInputStream();

                    Bitmap = BitmapFactory.decodeStream(is1);
                    is1.close();
                    conn1.disconnect();


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
            holder.img.setImageBitmap(Bitmap);

        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        holder.msg.setText(dataList.get(position).getMember_ChattingContent());

        //TextView Content = (TextView) convertView.findViewById(R.id.content);
        //Content.setText(dataList.get(position).getMember_ChattingContent());
        holder.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Key=dataList.get(position).getMember_MyID();
                Intent intent = new Intent(context, ChattingProfileSplash.class);
                intent.putExtra("YKey", Key);
                context.startActivity(intent);
            }
        });



//        convertView.setTag(dataList.get(position).getMember_MyID());

        if (dataList.get(position).getMember_MyID().equals(MyKey)) {
            holder.Nickname.setVisibility(View.GONE);
            holder.msg.setGravity(Gravity.RIGHT);
            holder.img.setVisibility(View.GONE);
            holder.msg.setVisibility(View.VISIBLE);
            // holder.msg.setText(dataList.get(position).getMember_ChattingContent());
        } else {
            holder.Nickname.setVisibility(View.VISIBLE);
            holder.msg.setVisibility(View.VISIBLE);
            holder.img.setVisibility(View.VISIBLE);
            holder.msg.setGravity(Gravity.LEFT);
            //holder.msg.setText(dataList.get(position).getMember_ChattingContent());
        }
        return convertView;
    }

    class ViewHolder {

        ImageView img;
        TextView Nickname;
        TextView msg;
    }
}