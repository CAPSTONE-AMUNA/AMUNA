package com.example.amuna;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filterable;
import android.widget.Filter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static com.example.amuna.EditBudgetActivity.et_subway2;
import static com.example.amuna.EditBudgetActivity.listView2;
import static com.example.amuna.EditBudgetActivity.saved2;
import static com.example.amuna.EditBudgetActivity.setListViewHeightBasedOnChildren2;
import static com.example.amuna.SubwayBudget.et_subway;
import static com.example.amuna.SubwayBudget.listView;
import static com.example.amuna.SubwayBudget.saved;
import static com.example.amuna.SearchSubwayActivity._SearchSubway_Activity;

import static com.example.amuna.SubwayBudget.setListViewHeightBasedOnChildren;
import static com.example.amuna.SubwayBudget.sub_where;

public class SearchSubwayAdapter extends RecyclerView.Adapter<SearchSubwayAdapter.ViewHolder> implements Filterable {

    ArrayList<Subway> mData = null;
    ArrayList<Subway> unmData = null;

    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sub_name;
        TextView tv_sub_line;
        TextView tv_sub_line_color;
        ImageView delete;


        ViewHolder(View itemView) {
            super(itemView);

            // 뷰 객체에 대한 참조. (hold strong reference)
            tv_sub_name = itemView.findViewById(R.id.tv_sub_name);
            tv_sub_line = itemView.findViewById(R.id.tv_sub_line);
            tv_sub_line_color = itemView.findViewById(R.id.tv_sub_line_color);
            delete=itemView.findViewById(R.id.deleteButton);
            delete.setVisibility(View.GONE);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION) {
                        if (sub_where==0){
                            Subway select_sub = mData.get(pos);
                            et_subway.setText(select_sub.getSub_name());
                        }else{
                            Subway select_sub2 = mData.get(pos);
                            et_subway2.setText(select_sub2.getSub_name());
                        }
                    }
                    if(sub_where==0){
                        Subway select_sub = mData.get(pos);
                        saved.add(select_sub);
                        setListViewHeightBasedOnChildren(listView);
                    }else{
                        Subway select_sub2 = mData.get(pos);
                        saved2.add(select_sub2);
                        setListViewHeightBasedOnChildren2(listView2);
                    }
                    _SearchSubway_Activity.finish();

                }
            });
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    SearchSubwayAdapter(ArrayList<Subway> list) {
        mData = list;
        unmData = list;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SearchSubwayAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View view = inflater.inflate(R.layout.recyclerview_subway_item, parent, false);
        SearchSubwayAdapter.ViewHolder vh = new SearchSubwayAdapter.ViewHolder(view);

        return vh;
    }

    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SearchSubwayAdapter.ViewHolder holder, int position) {
        String text_sub_name = mData.get(position).getSub_name();
        holder.tv_sub_name.setText(text_sub_name);
        String text_sub_line = mData.get(position).getSub_line();
        holder.tv_sub_line.setText(text_sub_line);
        if(text_sub_line.equals("서울특별시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(0,93,170));
        }else if(text_sub_line.equals("부산광역시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(0,164,74));
        }else if(text_sub_line.equals("대구광역시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(244,125,48));
        }else if(text_sub_line.equals("인천광역시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(0,169,220));
        }else if(text_sub_line.equals("광주광역시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(147,111,177));
        }else if(text_sub_line.equals("대전광역시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(199,117,57));
        }else if(text_sub_line.equals("울산광역시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(103,119,24));
        }else if(text_sub_line.equals("세종특별자치시")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(234,84,93));
        }else if(text_sub_line.equals("충청북도")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(198,177,130));
        }else if(text_sub_line.equals("충청남도")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(115,199,166));
        }else if(text_sub_line.equals("전라북도") || text_sub_line.equals("수인선")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(249,157,28));
        }else if(text_sub_line.equals("전라남도")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(23,140,114));
        }else if(text_sub_line.equals("경상북도")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(0,84,166));
        }else if(text_sub_line.equals("경상남도")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(111,160,206));
        }else if(text_sub_line.equals("제주특별자치도")){
            holder.tv_sub_line_color.setBackgroundColor(Color.rgb(237,128,0));
        }
    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        return mData.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String charString = constraint.toString();
                if(charString.isEmpty()) {
                    mData = unmData;
                } else {
                    ArrayList<Subway> filteringList = new ArrayList<>();
                    for(Subway i : unmData) {
                        if(i.getSub_name().contains(charString.toLowerCase())) {
                            filteringList.add(i);
                        }
                    }
                    mData = filteringList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = mData;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mData = (ArrayList<Subway>)results.values;
                notifyDataSetChanged();
            }
        };
    }
}