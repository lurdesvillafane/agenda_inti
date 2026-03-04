package com.emprende.inti_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import android.widget.CalendarView;

public class HomeActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private CalendarView calendarView;
    private ImageView btnMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);
        toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        TextView tvPedidos = findViewById(R.id.textView8);
        if (tvPedidos != null) {
            tvPedidos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(HomeActivity.this, PedidosActivity.class);
                    startActivity(intent);
                }
            });
        }

        ImageView btnMenu = findViewById(R.id.imageView7);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        // --- SECCIÓN CALENDARIO ---
        calendarView = findViewById(R.id.CalendarView);
        ImageView categoria1 = findViewById(R.id.categoria1);
        ImageView categoria2 = findViewById(R.id.categoria2);
        ImageView categoria3 = findViewById(R.id.categoria3);
        ImageView categoria4 = findViewById(R.id.categoria4);

        categoria1.setOnClickListener(v -> abrirCategoria("Categoria 1"));
        categoria2.setOnClickListener(v -> abrirCategoria("Categoria 2"));
        categoria3.setOnClickListener(v -> abrirCategoria("Categoria 3"));
        categoria4.setOnClickListener(v -> abrirCategoria("Categoria 4"));

        calendarView.setDate(System.currentTimeMillis(), true, true);
        calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {
            String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;
            Toast.makeText(HomeActivity.this, "Fecha: " + fecha, Toast.LENGTH_SHORT).show();
        });

        // Manejo de Insets (opcional según tu layout XML)
        View mainView = findViewById(R.id.drawerLayout); // Ajustado al ID principal
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
        // --------------------------

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_home) {
                Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show();
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }
    private void abrirCategoria(String nombre) {
        Toast.makeText(this, "Abriendo: " + nombre, Toast.LENGTH_SHORT).show();
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