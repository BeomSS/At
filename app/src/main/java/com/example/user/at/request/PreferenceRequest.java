package com.example.user.at.request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.example.user.at.LoginActivity;

import java.util.HashMap;
import java.util.Map;

public class PreferenceRequest extends StringRequest {
    final static private String URL = LoginActivity.ipAddress + ":800/At/Preference.php";
    private Map<String, String> parameter;

    public PreferenceRequest(String userID, String userFavorite, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);
        parameter = new HashMap<>();
        parameter.put("userID", userID);
        parameter.put("userFavorite", userFavorite);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return parameter;
    }
}
