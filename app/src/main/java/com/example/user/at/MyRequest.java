package com.example.user.at;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class MyRequest extends StringRequest {
    final static private String URL="http://218.50.169.50:800/At/SeeMy.php";
    private Map<String,String> parameter;

    public MyRequest(String toid, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameter = new HashMap<>();
        parameter.put("id",toid);
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return parameter;
    }
}
