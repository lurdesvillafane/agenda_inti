package com.emprende.inti_agenda;

import androidx.annotation.Nullable;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest {
    // Usamos el archivo de registro centralizado en PHP
    private static final String REGISTER_REQUEST_URL = Constants.BASE_URL + "registro.php";
    private Map<String, String> params;

    public RegisterRequest(String name, String email, String user_name, String password, Response.Listener<String> listener) {
        super(Method.POST, REGISTER_REQUEST_URL, listener, null);
        params = new HashMap<>();
        params.put("name", name);
        params.put("email", email);
        params.put("user_name", user_name);
        params.put("password", password);
    }

    @Nullable
    @Override
    public Map<String, String> getParams() {
        return params;
    }
}