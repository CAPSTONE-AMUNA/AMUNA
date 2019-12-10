package com.example.amuna;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import static com.example.amuna.EditBudgetActivity.listView2;
import static com.example.amuna.EditBudgetActivity.setListViewHeightBasedOnChildren2;
import static com.example.amuna.SubwayBudget.listView;
import static com.example.amuna.SubwayBudget.setListViewHeightBasedOnChildren;
import static com.example.amuna.SubwayBudget.sub_where;

public class locationAdapter extends BaseAdapter {
    private Context context;
    private List<Subway> add;

    public locationAdapter(Context context, List<Subway> add) {
        this.context = context;
        this.add = add;
    }
    @Override
    public int getCount() {
        return add.size();
    }

    @Override
    public Object getItem(int position) {
        return add.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.recyclerview_subway_item,null);
        TextView NAME = (TextView) v.findViewById(R.id.tv_sub_name);
        TextView LOCATION = (TextView) v.findViewById(R.id.tv_sub_line);
        ImageView delete = (ImageView)v.findViewById(R.id.deleteButton);
        NAME.setText(add.get(position).getSub_name());
        LOCATION.setText(add.get(position).getSub_line());
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add.remove(position);
                if(sub_where==0){
                    setListViewHeightBasedOnChildren(listView);
                }else{
                    setListViewHeightBasedOnChildren2(listView2);
                }

                notifyDataSetChanged();

            }
        });

        return v;
    }
}
