package com.example.analizator_bilansu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class Bilans extends AppCompatActivity {

    public static String rok;
    public static String profil;
    public static String nazwa_firmy;
    static final String NAZWA = "Nieznana";
    public static final String PROFIL = "Nieznany";
    DatabaseHandlerRok db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences sharedPref = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        String nazwa = sharedPref.getString(NAZWA, "Nieznana");
        if (nazwa.equals("Nieznana")) {
            setContentView(R.layout.dane_firmy);
            dodajElementyDoSpinner();

        } else {
            nazwa_firmy = nazwa;
            Intent i = new Intent(getApplicationContext(), AktywaTrwale.class);
            startActivity(i);
        }

    }

    public void dodajElementyDoSpinner() {

        String[] elementySpinner = {"produkcyjny", "handlowy", "usługowy"};
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, elementySpinner);
        final Spinner spinner = (Spinner) findViewById(R.id.profil);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int id, long position) {

                switch ((int) position) {
                    case 0:
                        profil = "produkcyjny";
                        SharedPreferences sharedPref = getSharedPreferences("PROFIL", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putString(PROFIL, profil);
                        editor.apply();
                        break;
                    case 1:
                        profil = "handlowy";
                        sharedPref = getSharedPreferences("PROFIL", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString(PROFIL, profil);
                        editor.apply();
                        break;
                    case 2:
                        profil = "uslugowy";
                        sharedPref = getSharedPreferences("PROFIL", Context.MODE_PRIVATE);
                        editor = sharedPref.edit();
                        editor.putString(PROFIL, profil);
                        editor.apply();
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
    }

    public void przejdzDoBilansu(View view) {
        EditText tt = (EditText) findViewById(R.id.nazwa);
        nazwa_firmy = tt.getText().toString();

        SharedPreferences sharedPref1 = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref1.edit();
        editor.putString(NAZWA, nazwa_firmy);
        editor.apply();

        Intent i = new Intent(getApplicationContext(), AktywaTrwale.class);
        startActivity(i);
    }
}
