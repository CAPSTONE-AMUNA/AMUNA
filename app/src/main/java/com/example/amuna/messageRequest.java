package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class messageRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/sendMessage.php";
    private Map<String, String> parameters;

    public messageRequest(String Email, String O_Key, String Content, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email",Email);
        parameters.put("O_Key",O_Key);
        parameters.put("Content",Content);

    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
