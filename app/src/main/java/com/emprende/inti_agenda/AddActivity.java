package com.emprende.inti_agenda;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;
import java.util.Arrays;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        ImageView circulorosa = findViewById(R.id.circulorosa);
        ImageView circuloverde = findViewById(R.id.circuloverde);
        ImageView circuloturquesa = findViewById(R.id.circuloturquesa);
        ImageView circulorosa2 = findViewById(R.id.circulorosa2);
        ImageView circuloverde2 = findViewById(R.id.circuloverde2);

        List<ImageView> colors = Arrays.asList(
                circulorosa,
                circulorosa2,
                circuloturquesa,
                circuloverde2,
                circuloverde
        );

        View.OnClickListener listener = v -> {

            for (ImageView img : colors) {
                img.setBackground(null);
            }

            v.setBackgroundResource(R.drawable.circulogris);
        };

        for (ImageView img : colors) {
            img.setOnClickListener(listener);
        }
    }
}
