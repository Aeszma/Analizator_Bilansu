package com.example.analizator_bilansu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;

public class AktywaTrwale extends AppCompatActivity {

    public static float sumaAktywaTw;
    public static float sumaAktywaTwUb;
    public static String wartoscObecna, wartoscUbiegla, nazwa;
    DatabaseHandlerRok db;
    RequestQueue requestQueue;
    String rok, nazwa_firmy;
    static final String ROK = "Nieznany";

    private static String url_create_product = "http://v-ie.uek.krakow.pl/~s186129/projekt/insert.php";
    TextView tvNazwa;
    TextView tvWartoscObecna;
    TextView tvWartoscUbiegla;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rok);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences sharedPref = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        nazwa_firmy = sharedPref.getString(Bilans.NAZWA, "Nieznana");
    }

    public void przejdz_aktywaOb(View view) {
        setContentView(R.layout.aktywa_obrotowe);
    }

    public void wyliczAktywaTrwale(View view) {
        int[] idsAktywaTrwale = new int[]{R.id.aktywaTrwale1, R.id.aktywaTrwale2, R.id.aktywaTrwale3};
        int[] idsAktywaTrwaleUb = new int[]{R.id.aktywaTrwaleUb1, R.id.aktywaTrwaleUb2, R.id.aktywaTrwaleUb3};
        sumaAktywaTw = 0;
        sumaAktywaTwUb = 0;
        int i = 1;
        DecimalFormat df = new DecimalFormat("#.##");
        TextView tvAktywa = (TextView) findViewById(R.id.akt_trw1);
        for (int id : idsAktywaTrwale) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaAktywaTw += v;
            i++;
        }
        tvAktywa.setText(String.valueOf(df.format(sumaAktywaTw)));

        i = 0;
        TextView tvAktywaUb = (TextView) findViewById(R.id.akt_trw2);
        for (int id : idsAktywaTrwaleUb) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaAktywaTwUb += v;
            i++;
        }
        tvAktywaUb.setText(String.valueOf(df.format(sumaAktywaTwUb)));
    }

    public void zapiszAktywaTrwale(View view) {
        tvNazwa = (TextView) findViewById(R.id.akt_tw);
        tvWartoscObecna = (TextView) findViewById(R.id.akt_trw1);
        tvWartoscUbiegla = (TextView) findViewById(R.id.akt_trw2);
        nazwa = "Aktywa trwale";
        wartoscObecna = tvWartoscObecna.getText().toString();
        wartoscUbiegla = tvWartoscUbiegla.getText().toString();

        StringRequest request = new StringRequest(Request.Method.POST, url_create_product, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                System.out.println(response.toString());
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwa_firmy);
                parameters.put("nazwa", nazwa);
                parameters.put("wartoscObecna", String.valueOf(wartoscObecna));
                parameters.put("wartoscUbiegla", String.valueOf(wartoscUbiegla));
                return parameters;
            }
        };
        requestQueue.add(request);
        Intent i = new Intent(getApplicationContext(), AktywaObrotowe.class);
        startActivity(i);
    }


    public void przejdzDoBilansu(View view) {
        EditText year = (EditText) findViewById(R.id.rok);
        rok = year.getText().toString();
        System.out.println(rok);

        SharedPreferences sharedPref = getSharedPreferences("ROK", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(ROK, rok);
        editor.apply();

        db = new DatabaseHandlerRok(this);
        db.addYear(new Rok(rok));
        db.close();
        setContentView(R.layout.aktywa_trwale);
    }
}
