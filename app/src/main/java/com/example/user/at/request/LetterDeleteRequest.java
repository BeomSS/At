package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class LetterDeleteRequest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/LetterDelete.php";
    private Map<String, String> parameter;

    //하나만 삭제할때
    public  LetterDeleteRequest(int deleteType, String userId, String messageId,  Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("deleteType", String.valueOf(deleteType));
        parameter.put("userId",userId);
        parameter.put("messageId",messageId);
    }
    //전부 삭제시
    public  LetterDeleteRequest(int deleteType, String userId, int where,  Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("deleteType", String.valueOf(deleteType));
        parameter.put("userId",userId);
        parameter.put("where",String.valueOf(where));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
