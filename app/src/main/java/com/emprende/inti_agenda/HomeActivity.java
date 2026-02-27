package com.emprende.inti_agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kizitonwose.calendarview.CalendarView;
import com.kizitonwose.calendarview.model.CalendarDay;
import com.kizitonwose.calendarview.ui.DayBinder;
import com.kizitonwose.calendarview.ui.ViewContainer;

import java.time.DayOfWeek;
import java.time.YearMonth;

public class HomeActivity extends AppCompatActivity {

    private CalendarView calendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        calendarView = findViewById(R.id.calendarMini);

        configurarCalendario();
    }

    private void configurarCalendario() {

        calendarView.setDayBinder(new DayBinder<DayViewContainer>() {

            @Override
            public DayViewContainer create(View view) {
                return new DayViewContainer(view);
            }

            @Override
            public void bind(DayViewContainer container, CalendarDay day) {

                container.textView.setText(
                        String.valueOf(day.getDate().getDayOfMonth())
                );

                container.view.setOnClickListener(v -> {

                    Toast.makeText(
                            HomeActivity.this,
                            "Fecha: " + day.getDate(),
                            Toast.LENGTH_SHORT
                    ).show();
                });
            }
        });
    }

    public static class DayViewContainer extends ViewContainer {

        TextView textView;
        View view;

        public DayViewContainer(View view) {
            super(view);
            this.view = view;
            textView = view.findViewById(android.R.id.text1);
        }
    }
}
