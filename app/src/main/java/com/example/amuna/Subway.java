package com.example.amuna;

import android.widget.TextView;

public class Subway {
    String sub_ID;
    String sub_name;
    String sub_line;

    public String getSub_name() {
        return sub_name;
    }

    public String getSub_line() {
        return sub_line;
    }

    public String getSub_ID() {
        return sub_ID;
    }

    public Subway(String sub_ID, String sub_name, String sub_line){
        this.sub_ID = sub_ID;
        this.sub_name = sub_name;
        this.sub_line = sub_line;
    }
}