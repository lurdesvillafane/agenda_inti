package com.emprende.inti_agenda;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class PedidosActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. Habilitar EdgeToEdge
        EdgeToEdge.enable(this);

        setContentView(R.layout.activity_pedidos);

        // 2. Configuración de Insets (para que no se tape el contenido)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.drawerLayout), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 3. Vincular componentes del Menú
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navView);

        // 4. Configurar el botón "Hamburguesa" (imageView7) para abrir el menú
        ImageView btnMenu = findViewById(R.id.imageView7);
        if (btnMenu != null) {
            btnMenu.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            });
        }

        // 5. Configurar clics del menú lateral
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_home) {
                // Al presionar Inicio, volvemos a la HomeActivity
                finish();
            } else if (id == R.id.nav_pedidos) {
                // Ya estamos en pedidos, solo cerramos el menú
                drawerLayout.closeDrawer(GravityCompat.START);
            }

            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    // Manejar el botón "Atrás" del celular para cerrar el menú si está abierto
    @Override
    public void onBackPressed() {
        if (drawerLayout != null && drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}