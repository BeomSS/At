package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardRequest extends StringRequest {
    final static private String URL="http://218.55.127.203:800/At/SeeBoard.php";
    private Map<String,String> parameter;

    public BoardRequest(int category, Response.Listener<String> listener){
        super(Request.Method.POST,URL,listener,null);
        parameter = new HashMap<>();
        parameter.put("category",String.valueOf(category));
    }

    @Override
    protected Map<String,String> getParams() throws AuthFailureError {
        return parameter;
    }
}
