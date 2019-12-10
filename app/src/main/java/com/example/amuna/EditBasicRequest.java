package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditBasicRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/updateBasic.php";
    private Map<String, String> parameters;

    public EditBasicRequest(String Key, String nickname, String phoneNum, String Job, String Univ_Name,  Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Key",Key);
        parameters.put("Nickname",nickname);
        parameters.put("PhoneNum",phoneNum);
        parameters.put("Job",Job);
        parameters.put("Univ_Name",Univ_Name);
    }

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}
