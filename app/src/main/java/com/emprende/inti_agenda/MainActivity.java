package com.emprende.inti_agenda;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class MainActivity extends AppCompatActivity {

    EditText etusuario, etpassword;
    Button btn_ingresar, btn_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // --- VERIFICAR SI YA HAY UNA SESIÓN INICIADA ---
        SharedPreferences preferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);

        if (isLoggedIn) {
            int userId = preferences.getInt("USER_ID", 0);
            String nombreUsuario = preferences.getString("NOMBRE_USUARIO", "");

            Intent intent = new Intent(MainActivity.this, HomeActivity.class);
            intent.putExtra("USER_ID", userId);
            intent.putExtra("NOMBRE_USUARIO", nombreUsuario);
            startActivity(intent);
            finish();
            return;
        }

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Vincular componentes
        etusuario = findViewById(R.id.etUsuarioLogin);
        etpassword = findViewById(R.id.etPasswordLogin);
        btn_ingresar = findViewById(R.id.btn_ingresar);
        btn_registro = findViewById(R.id.btn_registro);

        // Evento Iniciar Sesión
        btn_ingresar.setOnClickListener(v -> {
            final String user_name = etusuario.getText().toString().trim();
            final String password = etpassword.getText().toString().trim();

            if (user_name.isEmpty() || password.isEmpty()) {
                Toast.makeText(MainActivity.this, "Por favor completa todos los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Response.Listener<String> responseListener = response -> {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        int userId = jsonResponse.getInt("user_id");
                        String nombreReal = jsonResponse.getString("name");

                        // --- GUARDAR SESIÓN EN SHAREDPREFERENCES ---
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putBoolean("isLoggedIn", true);
                        editor.putInt("USER_ID", userId);
                        editor.putString("NOMBRE_USUARIO", nombreReal);
                        editor.apply();

                        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                        intent.putExtra("USER_ID", userId);
                        intent.putExtra("NOMBRE_USUARIO", nombreReal);

                        startActivity(intent);
                        finish();
                    } else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Acceso denegado")
                                .setMessage("El usuario o la contraseña son incorrectos. Si no tienes una cuenta, debes registrarte.")
                                .setPositiveButton("Registrarse", (dialog, which) -> {
                                    startActivity(new Intent(MainActivity.this, Registro.class));
                                })
                                .setNegativeButton("Reintentar", null)
                                .create()
                                .show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error en la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            };

            LoginRequest loginRequest = new LoginRequest(user_name, password, responseListener, error -> {
                Toast.makeText(MainActivity.this, "Error de red/servidor", Toast.LENGTH_SHORT).show();
            });

            RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
            queue.add(loginRequest);
        });

        // Evento Ir a Registro
        btn_registro.setOnClickListener(v ->
                startActivity(new Intent(MainActivity.this, Registro.class))
        );
    }
}