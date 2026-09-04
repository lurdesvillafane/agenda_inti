package com.emprende.inti_agenda;

import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    private static final String LOGIN_REQUEST_URL = Constants.BASE_URL + "login.php";
    private Map<String, String> params;

    public LoginRequest(String user_name, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        super(Method.POST, LOGIN_REQUEST_URL, listener, errorListener);
        params = new HashMap<>();
        params.put("user_name", user_name);
        params.put("password", password);
    }

    @Nullable
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}