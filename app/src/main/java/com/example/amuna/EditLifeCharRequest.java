package com.example.amuna;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class EditLifeCharRequest extends StringRequest {
    final static private String URL = "http://matehunter.cafe24.com/updateLifeChar.php";
    private Map<String, String> parameters;

    public EditLifeCharRequest(String Key, int My_q1, int My_q2, int My_q3, int My_q4, int My_q5, int My_q6, int My_q7, int My_q8, int My_q9,
                               int Ot_q1, int Ot_q2, int Ot_q3, int Ot_q4, int Ot_q5, int Ot_q6, int Ot_q7, int Ot_q8, int Ot_q9, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters = new HashMap<>();
        parameters.put("Key",Key);

        parameters.put("My_q1",String.valueOf(My_q1));
        parameters.put("My_q2",String.valueOf(My_q2));
        parameters.put("My_q3",String.valueOf(My_q3));
        parameters.put("My_q4",String.valueOf(My_q4));
        parameters.put("My_q5",String.valueOf(My_q5));
        parameters.put("My_q6",String.valueOf(My_q6));
        parameters.put("My_q7",String.valueOf(My_q7));
        parameters.put("My_q8",String.valueOf(My_q8));
        parameters.put("My_q9",String.valueOf(My_q9));

        parameters.put("Ot_q1",String.valueOf(Ot_q1));
        parameters.put("Ot_q2",String.valueOf(Ot_q2));
        parameters.put("Ot_q3",String.valueOf(Ot_q3));
        parameters.put("Ot_q4",String.valueOf(Ot_q4));
        parameters.put("Ot_q5",String.valueOf(Ot_q5));
        parameters.put("Ot_q6",String.valueOf(Ot_q6));
        parameters.put("Ot_q7",String.valueOf(Ot_q7));
        parameters.put("Ot_q8",String.valueOf(Ot_q8));
        parameters.put("Ot_q9",String.valueOf(Ot_q9));
//
    }
    @Override
    public Map<String,String> getParams(){
        return parameters;
    }
}
