package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class HomeFilteringSetRequest extends StringRequest{
    final static private String URL = "http://matehunter.cafe24.com/filteringList.php";
    private Map<String, String> parameters;

    public HomeFilteringSetRequest(String Email, int House, int Foreigner, String MinAge, String MaxAge, String MinBudget, String MaxBudget, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email", Email);
        parameters.put("House", String.valueOf(House));
        parameters.put("Foreigner", String.valueOf(Foreigner));
        parameters.put("MinAge",MinAge);
        parameters.put("MaxAge",MaxAge);
        parameters.put("MinBudget", MinBudget);
        parameters.put("MaxBudget", MaxBudget);
//
    }

    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
