package com.example.analizator_bilansu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu);
        SharedPreferences sharedPref = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        String nazwa = sharedPref.getString(Bilans.NAZWA, "Nieznana");
        Bilans.nazwa_firmy = nazwa;
    }

    public void bilans(View view) {
        Intent intent = new Intent(this, Bilans.class);
        startActivity(intent);
    }

    public void przejdzDoAnalizy(View view) {
        Intent intent = new Intent(this, Analiza.class);
        startActivity(intent);
    }

    public void przejdzDoWynikow(View view) {
        Intent intent = new Intent(this, Wyniki.class);
        startActivity(intent);
    }
}
