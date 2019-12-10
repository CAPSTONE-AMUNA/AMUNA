package com.example.amuna;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import static com.example.amuna.LoginSplash.userList;

public class HomeTab1Fragment extends Fragment {

    View layout;

    public HomeTab1Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        layout = inflater.inflate(R.layout.fragment_home_tab1, container, false);

        ListView listView = (ListView) layout.findViewById(R.id.listview2);
        final ListAdapter adapter = new ListAdapter(getActivity(), userList);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ProfileSplash.class);
                intent.putExtra("_Key", userList.get(position).getMember_id());
                intent.putExtra("Nickname", userList.get(position).getMember_Nickname());
                intent.putExtra("Job", userList.get(position).getMember_Job());
                intent.putExtra("Age", userList.get(position).getMember_Age());
                intent.putExtra("LimitBudget", userList.get(position).getMember_LimitBudget());
                intent.putExtra("Foreigner", userList.get(position).getMember_Foreigner());
                intent.putExtra("Intro", userList.get(position).getMember_Intro());
                intent.putExtra("PhotoURL", userList.get(position).getMember_PhotoURL());
                intent.putExtra("jjim", "0");
                startActivity(intent);

            }
        });
        return layout;
    }


}
