package com.emprende.inti_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class Registro extends AppCompatActivity implements View.OnClickListener {

    EditText etnombre, etusuario, etpassword, etemail;
    Button btnregistrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro2);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        etnombre = findViewById(R.id.etNombre);
        etusuario = findViewById(R.id.etUsername);
        etpassword = findViewById(R.id.etPassword);
        etemail = findViewById(R.id.etEmail);

        btnregistrar = findViewById(R.id.btn_registro);

        btnregistrar.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        final String name = etnombre.getText() != null ? etnombre.getText().toString().trim() : "";
        final String email = etemail.getText() != null ? etemail.getText().toString().trim() : "";
        final String user_name = etusuario.getText() != null ? etusuario.getText().toString().trim() : "";
        final String password = etpassword.getText() != null ? etpassword.getText().toString().trim() : "";

        Response.Listener<String> respoListener = response -> {
            Log.d("DATOS_REGISTRO", "Respuesta PHP: " + response);

            try {
                JSONObject jsonResponse = new JSONObject(response);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {
                    Intent intent = new Intent(Registro.this, MainActivity.class);
                    intent.putExtra("nombre_usuario", name);
                    Registro.this.startActivity(intent);
                    finish();
                } else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(Registro.this);
                    builder.setMessage("Error en el registro")
                            .setNegativeButton("Retry", null)
                            .create().show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        };

        RegisterRequest registerRequest = new RegisterRequest(name, email, user_name, password, respoListener);

        RequestQueue queue = Volley.newRequestQueue(Registro.this);
        queue.add(registerRequest);
    }
}