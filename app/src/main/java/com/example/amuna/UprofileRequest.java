package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UprofileRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/Favorite.php";
    private Map<String, String> parameters;

    public UprofileRequest(String Email, String Mylist, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email", Email);
        parameters.put("Mylist", Mylist);

//
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
