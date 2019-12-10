package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class DeleteAccRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/deleteAcc.php";
    private Map<String, String> parameters;

    public DeleteAccRequest(String Key, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Key",Key);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
