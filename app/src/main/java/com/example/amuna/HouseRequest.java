package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HouseRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/HouseTest.php";
    private Map<String, String> parameters;

    public HouseRequest(String Email, int h, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email", Email);
        parameters.put("h", String.valueOf(h));

//
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
