package com.ut3.restop.Screen;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ut3.restop.R;

import java.util.Calendar;
import java.util.Date;

public class ReservationActivity extends AppCompatActivity {

    private Spinner spinnerNbPersonnes;
    private TextView textViewDate;
    private TextView textViewHeure;
    private Button buttonReserver;

    private Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation);

        spinnerNbPersonnes = findViewById(R.id.spinner_nb_personnes);
        textViewDate = findViewById(R.id.text_view_date);
        textViewHeure = findViewById(R.id.text_view_heure);
        buttonReserver = findViewById(R.id.button_reserver);

        String[] nbPersonnes = new String[10];
        for (int i = 0; i < nbPersonnes.length; i++) {
            nbPersonnes[i] = String.valueOf(i + 1);
        }

        ArrayAdapter<String> adapterNbPersonnes = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, nbPersonnes);
        adapterNbPersonnes.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerNbPersonnes.setAdapter(adapterNbPersonnes);

        spinnerNbPersonnes.setSelection(0);
        Date date = new Date();
        String currentDate = DateFormat.getLongDateFormat(this).format(date);
        String currentTime = DateFormat.getTimeFormat(this).format(date);
        textViewDate.setText(currentDate);
        textViewHeure.setText(currentTime);

        // Initialisation du calendrier et de l'horloge
        calendar = Calendar.getInstance();

        // Liaison du bouton date au calendrier
        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir un dialogue avec le calendrier
                new DatePickerDialog(ReservationActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        // Mettre à jour la date sélectionnée
                        calendar.set(Calendar.YEAR, year);
                        calendar.set(Calendar.MONTH, month);
                        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        textViewDate.setText(DateFormat.getLongDateFormat(ReservationActivity.this).format(calendar.getTime()));

                    }
                }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        // Liaison du bouton heure à l'horloge
        textViewHeure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Ouvrir un dialogue avec l'horloge
                TimePickerDialog timePickerDialog = new TimePickerDialog(ReservationActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        // Mettre à jour l'heure sélectionnée
                        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        // **Forcer les minutes à 0**
                        calendar.set(Calendar.MINUTE, minute);
                        String formattedTime = DateFormat.getTimeFormat(ReservationActivity.this).format(calendar.getTime());
                        textViewHeure.setText(formattedTime);

                    }
                }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
                timePickerDialog.setTitle("Choisissez une heure");
                timePickerDialog.show();
            }
        });

        // Envoi des informations de réservation
        buttonReserver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinnerNbPersonnes.getSelectedItem() == null || calendar.getTimeInMillis() == 0) {
                    Toast.makeText(ReservationActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
                    return;
                }
                // Récupérer les informations du spinner
                int nbPersonnes = Integer.parseInt(spinnerNbPersonnes.getSelectedItem().toString());

                // Envoyer les informations à la page suivante
                Intent intent = new Intent(ReservationActivity.this, ReservationDoneActivity.class);
                startActivity(intent);
            }
        });
    }
}
