package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class UpdateBudgetRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/updateBudget_test.php";
    private Map<String, String> parameters;

    public UpdateBudgetRequest(String Key, String Sub_Name, String Date, String intro, String Mtv, String Mtv2, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Key",Key);
        parameters.put("Sub_Name",Sub_Name);
        parameters.put("Date",Date);
        System.out.println("@@@@@@@@@@@@"+intro);
        parameters.put("intro",intro);
        parameters.put("Mtv",Mtv);
        parameters.put("Mtv2",Mtv2);

    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
