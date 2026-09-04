package com.emprende.inti_agenda;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONException;
import org.json.JSONObject;

public class HomeActivity extends AppCompatActivity implements CalendarView.OnDateChangeListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private CalendarView calendarView;
    private TextView tvBienvenida;

    private String usuarioLogueado = "";
    private int idUsuario = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);
        calendarView = findViewById(R.id.CalendarView);
        tvBienvenida = findViewById(R.id.tvBienvenida);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        Intent intentOrigen = getIntent();
        String nombreUsuario = intentOrigen.getStringExtra("NOMBRE_USUARIO");
        idUsuario = intentOrigen.getIntExtra("USER_ID", 0);

        if (nombreUsuario != null) {
            usuarioLogueado = nombreUsuario;
        }

        if (tvBienvenida != null) {
            if (nombreUsuario != null && !nombreUsuario.isEmpty()) {
                tvBienvenida.setText("¡Hola, " + nombreUsuario + "!");
            } else {
                tvBienvenida.setText("¡Hola!");
            }
        }

        TextView tvPedidos = findViewById(R.id.textView8);
        if (tvPedidos != null) {
            tvPedidos.setOnClickListener(v -> {
                Intent intent = new Intent(HomeActivity.this, VerPedidosActivity.class);
                intent.putExtra("usuario", usuarioLogueado);
                intent.putExtra("USER_ID", idUsuario);
                startActivity(intent);
            });
        }

        ImageView btnMenu = findViewById(R.id.imageView7);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
        }

        calendarView.setOnDateChangeListener(this);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        // --- MANEJO DE OPCIONES DEL NAVIGATION DRAWER ---
        navigationView.setNavigationItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();

            } else if (itemId == R.id.nav_pedidos) {
                Intent intent = new Intent(HomeActivity.this, VerPedidosActivity.class);
                intent.putExtra("usuario", usuarioLogueado);
                intent.putExtra("USER_ID", idUsuario);
                startActivity(intent);

            } else if (itemId == R.id.nav_cat1 || itemId == R.id.nav_cat2 ||
                    itemId == R.id.nav_cat3 || itemId == R.id.nav_cat4) {

                // Abrir pedidos filtrando por la categoría seleccionada
                String categoria = item.getTitle().toString();
                Intent intent = new Intent(HomeActivity.this, VerPedidosActivity.class);
                intent.putExtra("usuario", usuarioLogueado);
                intent.putExtra("USER_ID", idUsuario);
                intent.putExtra("CATEGORIA", categoria);
                startActivity(intent);

            } else if (itemId == R.id.nav_calendar) {
                // Desplazar o enfocar calendario si es necesario
                Toast.makeText(this, "Calendario", Toast.LENGTH_SHORT).show();

            } else if (itemId == R.id.nav_papelera) {
                Toast.makeText(this, "Papelera", Toast.LENGTH_SHORT).show();

            } else if (itemId == R.id.nav_logout) {
                cerrarSesion();
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    private void cerrarSesion() {
        // 1. Limpiar SharedPreferences
        SharedPreferences preferences = getSharedPreferences("SesionUsuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();

        // 2. Redirigir al Login (MainActivity) borrando el historial
        Intent intent = new Intent(HomeActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarConteoPedidosHome();
    }

    private void cargarConteoPedidosHome() {
        String url = Constants.BASE_URL + "obtener_conteo_categorias.php?user_id=" + idUsuario;

        JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        if (response.getBoolean("success")) {
                            JSONObject conteos = response.getJSONObject("conteos");

                            int c1 = conteos.optInt("Categoria 1", 0);
                            int c2 = conteos.optInt("Categoria 2", 0);
                            int c3 = conteos.optInt("Categoria 3", 0);
                            int c4 = conteos.optInt("Categoria 4", 0);

                            TextView tvCat1 = findViewById(R.id.textView6);
                            TextView tvCat2 = findViewById(R.id.textView9);
                            TextView tvCat3 = findViewById(R.id.textView10);
                            TextView tvCat4 = findViewById(R.id.textView11);

                            if (tvCat1 != null) tvCat1.setText("Tiene " + c1 + " pedidos pendientes");
                            if (tvCat2 != null) tvCat2.setText("Tiene " + c2 + " pedidos pendientes");
                            if (tvCat3 != null) tvCat3.setText("Tiene " + c3 + " pedidos pendientes");
                            if (tvCat4 != null) tvCat4.setText("Tiene " + c4 + " pedidos pendientes");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> {}
        );

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
        final int dia = dayOfMonth;
        final int mes = month + 1;
        final int anio = year;

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        CharSequence[] items = new CharSequence[3];
        items[0] = "Agregar pedido";
        items[1] = "Ver pedidos";
        items[2] = "Cancelar";

        builder.setTitle("Seleccione una opción")
                .setItems(items, (dialogInterface, i) -> {
                    if (i == 0) {
                        Intent intent = new Intent(HomeActivity.this, AddActivity.class);
                        intent.putExtra("dia", dia);
                        intent.putExtra("mes", mes);
                        intent.putExtra("anio", anio);
                        intent.putExtra("usuario", usuarioLogueado);
                        intent.putExtra("user_id", idUsuario);
                        startActivity(intent);
                    } else if (i == 1) {
                        Intent intent = new Intent(HomeActivity.this, VerPedidosActivity.class);
                        intent.putExtra("dia", dia);
                        intent.putExtra("mes", mes);
                        intent.putExtra("anio", anio);
                        intent.putExtra("usuario", usuarioLogueado);
                        intent.putExtra("USER_ID", idUsuario);
                        startActivity(intent);
                    }
                });
        builder.create().show();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}