package com.emprende.inti_agenda;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.widget.CalendarView;
import android.widget.Toast;
import android.content.Intent;
import android.widget.LinearLayout;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);
        CalendarView calendarView = findViewById(R.id.calendarView);
        LinearLayout categoria1 = findViewById(R.id.categoria1);
        LinearLayout categoria2 = findViewById(R.id.categoria2);
        LinearLayout categoria3 = findViewById(R.id.categoria3);
        LinearLayout categoria4 = findViewById(R.id.categoria4);

        categoria1.setOnClickListener(v -> abrirCategoria("Categoria 1"));
        categoria2.setOnClickListener(v -> abrirCategoria("Categoria 2"));
        categoria1.setOnClickListener(v -> abrirCategoria("Categoria 3"));
        categoria2.setOnClickListener(v -> abrirCategoria("Categoria 4"));

        calendarView.setDate(System.currentTimeMillis(), true, true);
        calendarView.setMinDate(System.currentTimeMillis());
        calendarView.setOnDateChangeListener((view, year, month, dayOfMonth) -> {

            String fecha = dayOfMonth + "/" + (month + 1) + "/" + year;

            Toast.makeText(HomeActivity.this, "Fecha: " + fecha, Toast.LENGTH_SHORT).show();

        });
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }
    private void abrirCategoria(String categoria) {

        Intent intent = new Intent(HomeActivity.this, CategoryActivity.class);
        intent.putExtra("categoria", categoria);
        startActivity(intent);

    }

}