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

public class Pasywa extends AppCompatActivity {

    public static String wartoscObecna, wartoscUbiegla, nazwa, wartoscObecna2, wartoscUbiegla2, wartoscObecna3, wartoscUbiegla3;
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
        setContentView(R.layout.pasywa);
        SharedPreferences sharedPref = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        nazwa_firmy = sharedPref.getString(Bilans.NAZWA, "Nieznana");
        SharedPreferences sharedPref2 = getSharedPreferences("ROK", Context.MODE_PRIVATE);
        rok = sharedPref2.getString(AktywaTrwale.ROK, "Nieznany");
        requestQueue = Volley.newRequestQueue(getApplicationContext());
    }

    public void wyliczPasywa(View view) {
        int[] idsKapital = new int[]{R.id.pasywa1, R.id.pasywa2, R.id.pasywa3};
        int[] idsKapitalUb = new int[]{R.id.pasywaub1, R.id.pasywaub2, R.id.pasywaub3};
        int[] idsZobow = new int[]{R.id.pasywa4, R.id.pasywa5};
        int[] idsZobowUb = new int[]{R.id.pasywaub4, R.id.pasywaub5};
        float sumaKapital = 0;
        float sumaKapitalUb = 0;
        float sumaZobow = 0;
        float sumaZobowUb = 0;
        float suma = 0;
        float sumaUb = 0;
        int i = 1;
        DecimalFormat df = new DecimalFormat("#.##");
        TextView tvKapital = (TextView) findViewById(R.id.kap_wl1);
        for (int id : idsKapital) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaKapital += v;
            i++;
        }
        tvKapital.setText(String.valueOf(df.format(sumaKapital)));

        i = 0;
        TextView tvKapitalUb = (TextView) findViewById(R.id.kap_wl2);
        for (int id : idsKapitalUb) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaKapitalUb += v;
            i++;
        }
        tvKapitalUb.setText(String.valueOf(df.format(sumaKapitalUb)));
        i = 0;
        TextView tvZobow = (TextView) findViewById(R.id.zobowiazania1);
        for (int id : idsZobow) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaZobow += v;
            i++;
        }
        tvZobow.setText(String.valueOf(df.format(sumaZobow)));
        i = 0;
        TextView tvZobowUb = (TextView) findViewById(R.id.zobowiazania2);
        for (int id : idsZobowUb) {
            EditText t = (EditText) findViewById(id);
            float v = Float.parseFloat(t.getText().toString());
            sumaZobowUb += v;
            i++;
        }
        tvZobowUb.setText(String.valueOf(df.format(sumaZobowUb)));

        TextView tvPasywa = (TextView) findViewById(R.id.pasywa_razem1);
        TextView tvPasywaUb = (TextView) findViewById(R.id.pasywa_razem2);
        suma = sumaKapital + sumaZobow;
        sumaUb = sumaKapitalUb + sumaZobowUb;
        tvPasywa.setText(String.valueOf(df.format(suma)));
        tvPasywaUb.setText(String.valueOf(df.format(sumaUb)));
    }

    public void zapiszPasywa(View view) {

        tvWartoscObecna = (TextView) findViewById(R.id.kap_wl1);
        tvWartoscUbiegla = (TextView) findViewById(R.id.kap_wl2);
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
                nazwa = "Kapital wlasny";
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwa_firmy);
                parameters.put("nazwa", nazwa);
                parameters.put("wartoscObecna", String.valueOf(wartoscObecna));
                parameters.put("wartoscUbiegla", String.valueOf(wartoscUbiegla));
                return parameters;
            }
        };
        requestQueue.add(request);

        tvWartoscObecna = (TextView) findViewById(R.id.zobowiazania1);
        tvWartoscUbiegla = (TextView) findViewById(R.id.zobowiazania2);
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
                nazwa = "Zobowiazania";
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwa_firmy);
                parameters.put("nazwa", nazwa);
                parameters.put("wartoscObecna", String.valueOf(wartoscObecna2));
                parameters.put("wartoscUbiegla", String.valueOf(wartoscUbiegla2));
                return parameters;
            }
        };
        requestQueue.add(request);

        tvWartoscObecna = (TextView) findViewById(R.id.pasywa_razem1);
        tvWartoscUbiegla = (TextView) findViewById(R.id.pasywa_razem2);
        wartoscObecna3 = tvWartoscObecna.getText().toString();
        wartoscUbiegla3 = tvWartoscUbiegla.getText().toString();

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
                nazwa = "Pasywa razem";
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwa_firmy);
                parameters.put("nazwa", nazwa);
                parameters.put("wartoscObecna", String.valueOf(wartoscObecna3));
                parameters.put("wartoscUbiegla", String.valueOf(wartoscUbiegla3));
                return parameters;
            }
        };
        requestQueue.add(request);

        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }
}
