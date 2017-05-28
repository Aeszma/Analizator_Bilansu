package com.example.analizator_bilansu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class Wyniki extends AppCompatActivity {

    DatabaseHandlerRok dbr;
    DatabaseHandler db;
    String rok;
    String rokWybrany;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rok2);
        dodajElementyDoSpinner();
    }

    public void dodajElementyDoSpinner() {
        dbr = new DatabaseHandlerRok(this);
        List<Rok> roki = new ArrayList<>();
        roki = dbr.getAll();
        int wielkosc = dbr.getYearsCount();
        if (wielkosc == 0) {
            Toast.makeText(this, "Brak wyników", Toast.LENGTH_LONG).show();
        } else {
            String[] elementySpinner = new String[wielkosc];
            System.out.println(elementySpinner.length);
            System.out.println(roki.get(0).getRok());
            System.out.println("blabla");

            for (int i = 0; i < wielkosc; i++) {
                elementySpinner[i] = roki.get(i).getRok();
                System.out.println(elementySpinner[i]);
            }
            ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, elementySpinner);
            Spinner spinner = (Spinner) findViewById(R.id.rok2);
            spinner.setAdapter(adp);

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long arg3) {
                    rokWybrany = parent.getItemAtPosition(position).toString();
                }

                public void onNothingSelected(AdapterView<?> arg0) {
                    Toast.makeText(getParent().getApplicationContext(), "Nie wybrano roku!", Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void przejdzDoWynikow(View view) {

        if (rokWybrany == null) {
            Toast.makeText(this, "Nie wybrano roku!", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, Menu.class);
            startActivity(intent);
        } else {
            setContentView(R.layout.analiza_pionowa);
            db = new DatabaseHandler(this);
            Wskazniki w1 = db.getValueA(rokWybrany, "AnalizaPionowa1");
            Wskazniki w2 = db.getValueA(rokWybrany, "AnalizaPionowa2");
            Wskazniki w3 = db.getValueA(rokWybrany, "AnalizaPionowa3");
            Wskazniki w4 = db.getValueA(rokWybrany, "AnalizaPionowa4");
            Wskazniki w5 = db.getValueA(rokWybrany, "AnalizaPionowa5");
            Wskazniki w6 = db.getValueA(rokWybrany, "AnalizaPionowa6");
            Wskazniki w7 = db.getValueA(rokWybrany, "AnalizaPionowa7");

            TextView wsSAT = (TextView) findViewById(R.id.wsk_strAT1);
            TextView wsSATU = (TextView) findViewById(R.id.wsk_strAT2);
            wsSAT.setText(String.valueOf(w1.getWartoscObecna()));
            wsSATU.setText(String.valueOf(w1.getWartoscUbiegla()));

            TextView wsSAO = (TextView) findViewById(R.id.wsk_strAO1);
            TextView wsSAOU = (TextView) findViewById(R.id.wsk_strAO2);
            wsSAO.setText(String.valueOf(w2.getWartoscObecna()));
            wsSAOU.setText(String.valueOf(w2.getWartoscUbiegla()));

            TextView wsSA = (TextView) findViewById(R.id.wsk_strA1);
            TextView wsSAU = (TextView) findViewById(R.id.wsk_strA2);
            wsSA.setText(String.valueOf(w3.getWartoscObecna()));
            wsSAU.setText(String.valueOf(w3.getWartoscUbiegla()));

            TextView wsSKW = (TextView) findViewById(R.id.wsk_strKW1);
            TextView wsSKWU = (TextView) findViewById(R.id.wsk_strKW2);
            wsSKW.setText(String.valueOf(w4.getWartoscObecna()));
            wsSKWU.setText(String.valueOf(w4.getWartoscUbiegla()));

            TextView wsSKO = (TextView) findViewById(R.id.wsk_strKO1);
            TextView wsSKOU = (TextView) findViewById(R.id.wsk_strKO2);
            wsSKO.setText(String.valueOf(w5.getWartoscObecna()));
            wsSKOU.setText(String.valueOf(w5.getWartoscUbiegla()));

            TextView wsZF = (TextView) findViewById(R.id.wsk_zrfin1);
            TextView wsZFU = (TextView) findViewById(R.id.wsk_zrfin2);
            wsZF.setText(String.valueOf(w6.getWartoscObecna()));
            wsZFU.setText(String.valueOf(w6.getWartoscUbiegla()));

            TextView wsPZ = (TextView) findViewById(R.id.wsk_pokr1);
            TextView wsPZU = (TextView) findViewById(R.id.wsk_pokr2);
            wsPZ.setText(String.valueOf(w7.getWartoscObecna()));
            wsPZU.setText(String.valueOf(w7.getWartoscUbiegla()));

        }
    }

    public void okAnalizaPionowa(View view) {
        setContentView(R.layout.analiza_pozioma);
        db = new DatabaseHandler(this);

        Wskazniki w1 = db.getValueA(rokWybrany, "AnalizaPozioma1");
        Wskazniki w2 = db.getValueA(rokWybrany, "AnalizaPozioma2");
        Wskazniki w3 = db.getValueA(rokWybrany, "AnalizaPozioma3");
        Wskazniki w4 = db.getValueA(rokWybrany, "AnalizaPozioma4");
        Wskazniki w5 = db.getValueA(rokWybrany, "AnalizaPozioma5");
        Wskazniki w6 = db.getValueA(rokWybrany, "AnalizaPozioma6");

        TextView wsDAT = (TextView) findViewById(R.id.wsk_dynAT1);
        wsDAT.setText(String.valueOf(w1.getWartoscObecna()));

        TextView wsDAO = (TextView) findViewById(R.id.wsk_dynAO1);
        wsDAO.setText(String.valueOf(w2.getWartoscObecna()));

        TextView wsDA = (TextView) findViewById(R.id.wsk_dynA1);
        wsDA.setText(String.valueOf(w3.getWartoscObecna()));

        TextView wsDKW = (TextView) findViewById(R.id.wsk_dynKW1);
        wsDKW.setText(String.valueOf(w4.getWartoscObecna()));

        TextView wsDKO = (TextView) findViewById(R.id.wsk_dynKO1);
        wsDKO.setText(String.valueOf(w5.getWartoscObecna()));

        TextView wsDP = (TextView) findViewById(R.id.wsk_dynP1);
        wsDP.setText(String.valueOf(w6.getWartoscObecna()));
    }

    public void przejdzWskTempaZmian(View view) {
        setContentView(R.layout.analiza_pozioma_wsk_tempazmian);
        db = new DatabaseHandler(this);

        Wskazniki w1 = db.getValueA(rokWybrany, "AnalizaTempaZmian1");
        Wskazniki w2 = db.getValueA(rokWybrany, "AnalizaTempaZmian2");
        Wskazniki w3 = db.getValueA(rokWybrany, "AnalizaTempaZmian3");
        Wskazniki w4 = db.getValueA(rokWybrany, "AnalizaTempaZmian4");
        Wskazniki w5 = db.getValueA(rokWybrany, "AnalizaTempaZmian5");
        Wskazniki w6 = db.getValueA(rokWybrany, "AnalizaTempaZmian6");

        TextView wskTZAT = (TextView) findViewById(R.id.wsk_tzAT1);
        wskTZAT.setText(String.valueOf(w1.getWartoscObecna()));

        TextView wskTZAO = (TextView) findViewById(R.id.wsk_tzAO1);
        wskTZAO.setText(String.valueOf(w2.getWartoscObecna()));

        TextView wskTZA = (TextView) findViewById(R.id.wsk_tzA1);
        wskTZA.setText(String.valueOf(w3.getWartoscObecna()));

        TextView wskTZKW = (TextView) findViewById(R.id.wsk_tzKW1);
        wskTZKW.setText(String.valueOf(w4.getWartoscObecna()));

        TextView wskTZKO = (TextView) findViewById(R.id.wsk_tzKO1);
        wskTZKO.setText(String.valueOf(w5.getWartoscObecna()));

        TextView wskTZP = (TextView) findViewById(R.id.wsk_tzP1);
        wskTZP.setText(String.valueOf(w6.getWartoscObecna()));
    }

    public void przejdzWskPokrycia(View view) {
        setContentView(R.layout.analiza_pozioma_pokrycia);
        db = new DatabaseHandler(this);

        Wskazniki w1 = db.getValueA(rokWybrany, "AnalizaPokrycia1");
        Wskazniki w2 = db.getValueA(rokWybrany, "AnalizaPokrycia2");

        TextView wskPAT = (TextView) findViewById(R.id.wsk_pokrAT1);
        TextView wskPATU = (TextView) findViewById(R.id.wsk_pokrAT2);
        wskPAT.setText(String.valueOf(w1.getWartoscObecna()));
        wskPATU.setText(String.valueOf(w1.getWartoscUbiegla()));

        TextView wskPAO = (TextView) findViewById(R.id.wsk_pokrAO1);
        TextView wskPAOU = (TextView) findViewById(R.id.wsk_pokrAO2);
        wskPAO.setText(String.valueOf(w2.getWartoscObecna()));
        wskPAOU.setText(String.valueOf(w2.getWartoscUbiegla()));
    }

    public void okAnalizaPozioma(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
