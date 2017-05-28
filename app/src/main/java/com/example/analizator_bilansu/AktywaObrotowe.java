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

public class AktywaObrotowe extends AppCompatActivity {

    public static String wartoscObecna, wartoscUbiegla, nazwa, wartoscObecna2, wartoscUbiegla2;
    DatabaseHandler db;
    TextView tvNazwa;
    TextView tvWartoscObecna;
    TextView tvWartoscUbiegla;
    RequestQueue requestQueue;
    String nazwa_firmy, rok;

    private static String url_create_product = "http://v-ie.uek.krakow.pl/~s186129/projekt/insert.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aktywa_obrotowe);
        SharedPreferences sharedPref = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        nazwa_firmy = sharedPref.getString(Bilans.NAZWA, "Nieznana");
        SharedPreferences sharedPref2 = getSharedPreferences("ROK", Context.MODE_PRIVATE);
        rok = sharedPref2.getString(AktywaTrwale.ROK, "Nieznany");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void przejdz_pasywa(View view) {
        setContentView(R.layout.pasywa);
    }

    public void wyliczAktywaObrotowe(View view) {
        int[] idsAktywaObrotowe = new int[]{R.id.aktywaOb1, R.id.aktywaOb2, R.id.aktywaOb3, R.id.aktywaOb4, R.id.aktywaOb5};
        int[] idsAktywaObrotoweUb = new int[]{R.id.aktywaObUb1, R.id.aktywaObUb2, R.id.aktywaObUb3, R.id.aktywaObUb4, R.id.aktywaObUb5};
        float suma = 0;
        float sumaUb = 0;
        int i = 1;
        DecimalFormat df = new DecimalFormat("#.##");
        TextView tvAktywa = (TextView) findViewById(R.id.akt_ob1);
        for (int id : idsAktywaObrotowe) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            suma += v;
            i++;
        }
        tvAktywa.setText(String.valueOf(df.format(suma)));

        i = 0;
        TextView tvAktywaUb = (TextView) findViewById(R.id.akt_ob2);
        for (int id : idsAktywaObrotoweUb) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaUb += v;
            i++;
        }
        tvAktywaUb.setText(String.valueOf(df.format(sumaUb)));

        TextView tvAktywaRazem = (TextView) findViewById(R.id.aktywa_razem1);
        TextView tvAktywaRazemUb = (TextView) findViewById(R.id.aktywa_razem2);
        tvAktywaRazem.setText(String.valueOf(df.format(suma + AktywaTrwale.sumaAktywaTw)));
        tvAktywaRazemUb.setText(String.valueOf(df.format(sumaUb + AktywaTrwale.sumaAktywaTwUb)));

    }

    public void zapiszAktywaObrotowe(View view) {

        tvNazwa = (TextView) findViewById(R.id.akt_ob);
        tvWartoscObecna = (TextView) findViewById(R.id.akt_ob1);
        tvWartoscUbiegla = (TextView) findViewById(R.id.akt_ob2);
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
                nazwa = "Aktywa obrotowe";
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwa_firmy);
                parameters.put("nazwa", nazwa);
                parameters.put("wartoscObecna", String.valueOf(wartoscObecna));
                parameters.put("wartoscUbiegla", String.valueOf(wartoscUbiegla));
                return parameters;
            }
        };
        requestQueue.add(request);

        tvWartoscObecna = (TextView) findViewById(R.id.aktywa_razem1);
        tvWartoscUbiegla = (TextView) findViewById(R.id.aktywa_razem2);
        wartoscObecna2 = tvWartoscObecna.getText().toString();
        wartoscUbiegla2 = tvWartoscUbiegla.getText().toString();

        request = new StringRequest(Request.Method.POST, url_create_product, new Response.Listener<String>() {
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
                nazwa = "Aktywa razem";
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwa_firmy);
                parameters.put("nazwa", nazwa);
                parameters.put("wartoscObecna", String.valueOf(wartoscObecna2));
                parameters.put("wartoscUbiegla", String.valueOf(wartoscUbiegla2));
                return parameters;
            }
        };
        requestQueue.add(request);

        Intent intent = new Intent(this, Pasywa.class);
        startActivity(intent);
    }


}
