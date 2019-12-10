package com.example.amuna;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import org.angmarch.views.NiceSpinner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static com.example.amuna.LoginSplash.userList;
import static com.example.amuna.Main2Activity.userList2;

public class HomeTab2Fragment extends Fragment {

    View layout;
    private Button btn_toFilteringSet;
    public static ListView listView;
    public static ListAdapter adapter;
    public static TextView tv_condition;

    public HomeTab2Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_home_tab2, container, false);


        listView = layout.findViewById(R.id.listview3);
        adapter = new ListAdapter(getActivity(), userList2);
        listView.setAdapter(adapter);

        tv_condition = layout.findViewById(R.id.tv_condition);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ProfileSplash.class);
                intent.putExtra("_Key", userList2.get(position).getMember_id());
                intent.putExtra("Nickname", userList2.get(position).getMember_Nickname());
                intent.putExtra("Job", userList2.get(position).getMember_Job());
                intent.putExtra("Age", userList2.get(position).getMember_Age());
                intent.putExtra("LimitBudget", userList2.get(position).getMember_LimitBudget());
                intent.putExtra("Foreigner", userList2.get(position).getMember_Foreigner());
                intent.putExtra("Intro", userList2.get(position).getMember_Intro());
                intent.putExtra("PhotoURL", userList2.get(position).getMember_PhotoURL());
                intent.putExtra("jjim", "0");
                startActivity(intent);
            }
        });

        btn_toFilteringSet = layout.findViewById(R.id.btn_toFilteringSet);
        btn_toFilteringSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(view.getContext(), HomeFilteringSetActivity.class);
                startActivityForResult(intent, 3000);
            }
        });
        return layout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 3000:
                    listView = layout.findViewById(R.id.listview3);
                    adapter = new ListAdapter(getActivity(), userList2);
                    listView.setAdapter(adapter);
                    break;
            }
        }
    }
}