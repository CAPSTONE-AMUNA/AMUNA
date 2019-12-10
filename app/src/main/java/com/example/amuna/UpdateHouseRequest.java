package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateHouseRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/updateHouse.php";
    private Map<String, String> parameters;

    public UpdateHouseRequest(String Key, int h, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Key",Key);
        parameters.put("h", String.valueOf(h));
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
