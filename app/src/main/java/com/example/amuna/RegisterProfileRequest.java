package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class RegisterProfileRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/RegisterProTest.php";
    private Map<String, String> parameters;

    public RegisterProfileRequest(String Email, String Nickname, int Foreigner, int Age, String PhoneNum, String Job, String UnivName, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Email", Email);
        parameters.put("Nickname", Nickname);
        parameters.put("Foreigner", String.valueOf(Foreigner));
        parameters.put("Age", String.valueOf(Age));
        parameters.put("PhoneNum", PhoneNum);
        parameters.put("Job", Job);
        parameters.put("UnivName", UnivName);
//
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
