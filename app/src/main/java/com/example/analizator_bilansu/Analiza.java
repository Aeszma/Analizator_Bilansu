package com.example.analizator_bilansu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Analiza extends AppCompatActivity {

    float aktywaTrwale, aktywaObrotowe, aktywaRazem, kapitalWlasny, zobowiazania, pasywaRazem;
    float aktywaTrwaleUb, aktywaObrotoweUb, aktywaRazemUb, kapitalWlasnyUb, zobowiazaniaUb, pasywaRazemUb;
    DatabaseHandler db;
    RequestQueue requestQueue;
    private static String url_receive_item = "http://v-ie.uek.krakow.pl/~s186129/projekt/get.php";

    String rok;
    String nazwaFirmy;

    static float[] aktywaTrwaleT = new float[2];
    static float[] aktywaObrotoweT = new float[2];
    static float[] aktywaRazemT = new float[2];
    static float[] kapitalWlasnyT = new float[2];
    static float[] zobowiazaniaT = new float[2];
    static float[] pasywaRazemT = new float[2];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.analiza);
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        SharedPreferences sharedPref = getSharedPreferences("ROK", Context.MODE_PRIVATE);
        SharedPreferences sharedPref2 = getSharedPreferences("NAZWA", Context.MODE_PRIVATE);
        rok = sharedPref.getString(AktywaTrwale.ROK, "Nieznany");
        nazwaFirmy = sharedPref2.getString(Bilans.NAZWA, "Nieznana");

        System.out.println(rok + nazwaFirmy);

        pobierz("Aktywa trwale", aktywaTrwaleT);
        pobierz("Aktywa obrotowe", aktywaObrotoweT);
        pobierz("Aktywa razem", aktywaRazemT);
        pobierz("Kapital wlasny", kapitalWlasnyT);
        pobierz("Zobowiazania", zobowiazaniaT);
        pobierz("Pasywa razem", pasywaRazemT);

    }

    public void pobierz(final String nazwa, final float[] tabela) {
        StringRequest request = new StringRequest(Request.Method.POST, url_receive_item, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    String analiza = response.toString();
                    JSONObject obiekt = new JSONObject(response);
                    String s = obiekt.getString("bilans");
                    s = s.replace("[", "");
                    s = s.replace("]", "");
                    obiekt = new JSONObject(s);
                    tabela[0] = Float.parseFloat(obiekt.getString("wartoscObecna"));
                    tabela[1] = Float.parseFloat(obiekt.getString("wartoscUbiegla"));

                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.append(error.getMessage());
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("rok", rok);
                parameters.put("nazwaFirmy", nazwaFirmy);
                parameters.put("nazwa", nazwa);
                return parameters;
            }
        };
        requestQueue.add(request);
    }

    public void przejdzAnalizaPionowa(View view) {
        aktywaTrwale = aktywaTrwaleT[0];
        aktywaTrwaleUb = aktywaTrwaleT[1];
        aktywaObrotowe = aktywaObrotoweT[0];
        aktywaObrotoweUb = aktywaObrotoweT[1];
        aktywaRazem = aktywaRazemT[0];
        aktywaRazemUb = aktywaRazemT[1];
        kapitalWlasny = kapitalWlasnyT[0];
        kapitalWlasnyUb = kapitalWlasnyT[1];
        zobowiazania = zobowiazaniaT[0];
        zobowiazaniaUb = zobowiazaniaT[1];
        pasywaRazem = pasywaRazemT[0];
        pasywaRazemUb = pasywaRazemT[1];
        System.out.println(aktywaTrwale + " " + aktywaTrwaleUb);
        System.out.println(aktywaObrotowe + " " + aktywaObrotoweUb);
        System.out.println(aktywaRazem + " " + aktywaRazemUb);
        System.out.println(kapitalWlasny + " " + kapitalWlasnyUb);
        System.out.println(zobowiazania + " " + zobowiazaniaUb);
        System.out.println(pasywaRazem + " " + pasywaRazemUb);

        setContentView(R.layout.analiza_pionowa);

        TextView wsSAT = (TextView) findViewById(R.id.wsk_strAT1);
        TextView wsSATU = (TextView) findViewById(R.id.wsk_strAT2);
        wsSAT.setText(String.valueOf((aktywaTrwale / aktywaRazem) * 100));
        wsSATU.setText(String.valueOf((aktywaTrwaleUb / aktywaRazemUb) * 100));

        TextView wsSAO = (TextView) findViewById(R.id.wsk_strAO1);
        TextView wsSAOU = (TextView) findViewById(R.id.wsk_strAO2);
        wsSAO.setText(String.valueOf((aktywaObrotowe / aktywaRazem) * 100));
        wsSAOU.setText(String.valueOf((aktywaObrotoweUb / aktywaRazemUb) * 100));

        TextView wsSA = (TextView) findViewById(R.id.wsk_strA1);
        TextView wsSAU = (TextView) findViewById(R.id.wsk_strA2);
        wsSA.setText(String.valueOf((aktywaTrwale / aktywaObrotowe) * 100));
        wsSAU.setText(String.valueOf((aktywaTrwaleUb / aktywaObrotoweUb) * 100));

        TextView wsSKW = (TextView) findViewById(R.id.wsk_strKW1);
        TextView wsSKWU = (TextView) findViewById(R.id.wsk_strKW2);
        wsSKW.setText(String.valueOf((kapitalWlasny / pasywaRazem) * 100));
        wsSKWU.setText(String.valueOf((kapitalWlasnyUb / pasywaRazemUb) * 100));

        TextView wsSKO = (TextView) findViewById(R.id.wsk_strKO1);
        TextView wsSKOU = (TextView) findViewById(R.id.wsk_strKO2);
        wsSKO.setText(String.valueOf((zobowiazania / pasywaRazem) * 100));
        wsSKOU.setText(String.valueOf((zobowiazania / pasywaRazemUb) * 100));

        TextView wsZF = (TextView) findViewById(R.id.wsk_zrfin1);
        TextView wsZFU = (TextView) findViewById(R.id.wsk_zrfin2);
        wsZF.setText(String.valueOf((kapitalWlasny / zobowiazania) * 100));
        wsZFU.setText(String.valueOf((kapitalWlasnyUb / zobowiazaniaUb) * 100));

        TextView wsPZ = (TextView) findViewById(R.id.wsk_pokr1);
        TextView wsPZU = (TextView) findViewById(R.id.wsk_pokr2);
        wsPZ.setText(String.valueOf((zobowiazania / kapitalWlasny) * 100));
        wsPZU.setText(String.valueOf((zobowiazaniaUb / kapitalWlasnyUb) * 100));

        db = new DatabaseHandler(this);
        db.addValue(new Wskazniki(rok, "AnalizaPionowa1", ((aktywaTrwale / aktywaRazem) * 100), ((aktywaTrwaleUb / aktywaRazemUb) * 100)));
        System.out.println("HHHHHHHHHHH");
        db.addValue(new Wskazniki(rok, "AnalizaPionowa2", ((aktywaObrotowe / aktywaRazem) * 100), ((aktywaObrotoweUb / aktywaRazemUb) * 100)));
        db.addValue(new Wskazniki(rok, "AnalizaPionowa3", ((aktywaTrwale / aktywaObrotowe) * 100), ((aktywaTrwaleUb / aktywaObrotoweUb) * 100)));
        db.addValue(new Wskazniki(rok, "AnalizaPionowa4", ((kapitalWlasny / pasywaRazem) * 100), ((kapitalWlasnyUb / pasywaRazemUb) * 100)));
        db.addValue(new Wskazniki(rok, "AnalizaPionowa5", ((zobowiazania / pasywaRazem) * 100), ((kapitalWlasnyUb / pasywaRazemUb) * 100)));
        db.addValue(new Wskazniki(rok, "AnalizaPionowa6", ((kapitalWlasny / zobowiazania) * 100), ((kapitalWlasnyUb / zobowiazaniaUb) * 100)));
        db.addValue(new Wskazniki(rok, "AnalizaPionowa7", ((zobowiazania / kapitalWlasny) * 100), ((zobowiazaniaUb / kapitalWlasnyUb) * 100)));
        db.close();
    }

    public void przejdzAnalizaPozioma(View view) {
        aktywaTrwale = aktywaTrwaleT[0];
        aktywaTrwaleUb = aktywaTrwaleT[1];
        aktywaObrotowe = aktywaObrotoweT[0];
        aktywaObrotoweUb = aktywaObrotoweT[1];
        aktywaRazem = aktywaRazemT[0];
        aktywaRazemUb = aktywaRazemT[1];
        kapitalWlasny = kapitalWlasnyT[0];
        kapitalWlasnyUb = kapitalWlasnyT[1];
        zobowiazania = zobowiazaniaT[0];
        zobowiazaniaUb = zobowiazaniaT[1];
        pasywaRazem = pasywaRazemT[0];
        pasywaRazemUb = pasywaRazemT[1];
        System.out.println(aktywaTrwale + " " + aktywaTrwaleUb);
        System.out.println(aktywaObrotowe + " " + aktywaObrotoweUb);
        System.out.println(aktywaRazem + " " + aktywaRazemUb);
        System.out.println(kapitalWlasny + " " + kapitalWlasnyUb);
        System.out.println(zobowiazania + " " + zobowiazaniaUb);
        System.out.println(pasywaRazem + " " + pasywaRazemUb);
        setContentView(R.layout.analiza_pozioma);

        TextView wsDAT = (TextView) findViewById(R.id.wsk_dynAT1);
        TextView wsDAO = (TextView) findViewById(R.id.wsk_dynAO1);
        TextView wsDA = (TextView) findViewById(R.id.wsk_dynA1);
        TextView wsDKW = (TextView) findViewById(R.id.wsk_dynKW1);
        TextView wsDKO = (TextView) findViewById(R.id.wsk_dynKO1);
        TextView wsDP = (TextView) findViewById(R.id.wsk_dynP1);

        wsDAT.setText(String.valueOf((aktywaTrwale / aktywaTrwaleUb) * 100));
        wsDAO.setText(String.valueOf((aktywaObrotowe / aktywaObrotoweUb) * 100));
        wsDA.setText(String.valueOf((aktywaRazem / aktywaRazemUb) * 100));
        wsDKW.setText(String.valueOf((kapitalWlasny / kapitalWlasnyUb) * 100));
        wsDKO.setText(String.valueOf((zobowiazania / zobowiazaniaUb) * 100));
        wsDP.setText(String.valueOf((pasywaRazem / pasywaRazemUb) * 100));

        db = new DatabaseHandler(this);
        db.addValue(new Wskazniki(rok, "AnalizaPozioma1", ((aktywaTrwale / aktywaTrwaleUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaPozioma2", ((aktywaObrotowe / aktywaObrotoweUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaPozioma3", ((aktywaRazem / aktywaRazemUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaPozioma4", ((kapitalWlasny / kapitalWlasnyUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaPozioma5", ((zobowiazania / zobowiazaniaUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaPozioma6", ((pasywaRazem / pasywaRazemUb) * 100), 0));
        db.close();
    }

    public void okAnalizaPozioma(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void okAnalizaPionowa(View view) {
        Intent intent = new Intent(this, Menu.class);
        startActivity(intent);
    }

    public void przejdzWskTempaZmian(View view) {
        setContentView(R.layout.analiza_pozioma_wsk_tempazmian);

        TextView wskTZAT = (TextView) findViewById(R.id.wsk_tzAT1);
        TextView wskTZAO = (TextView) findViewById(R.id.wsk_tzAO1);
        TextView wskTZA = (TextView) findViewById(R.id.wsk_tzA1);
        TextView wskTZKW = (TextView) findViewById(R.id.wsk_tzKW1);
        TextView wskTZKO = (TextView) findViewById(R.id.wsk_tzKO1);
        TextView wskTZP = (TextView) findViewById(R.id.wsk_tzP1);

        wskTZAT.setText(String.valueOf(((aktywaTrwale - aktywaTrwaleUb) / aktywaTrwaleUb) * 100));
        wskTZAO.setText(String.valueOf(((aktywaObrotowe - aktywaObrotoweUb) / aktywaObrotoweUb) * 100));
        wskTZA.setText(String.valueOf(((aktywaRazem - aktywaRazemUb) / aktywaRazemUb) * 100));
        wskTZKW.setText(String.valueOf(((kapitalWlasny - kapitalWlasnyUb) / kapitalWlasnyUb) * 100));
        wskTZKO.setText(String.valueOf(((zobowiazania - zobowiazaniaUb) / zobowiazaniaUb) * 100));
        wskTZP.setText(String.valueOf(((pasywaRazem - pasywaRazemUb) / pasywaRazemUb) * 100));

        db = new DatabaseHandler(this);
        db.addValue(new Wskazniki(rok, "AnalizaTempaZmian1", (((aktywaTrwale - aktywaTrwaleUb) / aktywaTrwaleUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaTempaZmian2", (((aktywaObrotowe - aktywaObrotoweUb) / aktywaObrotoweUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaTempaZmian3", (((aktywaRazem - aktywaRazemUb) / aktywaRazemUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaTempaZmian4", (((kapitalWlasny - kapitalWlasnyUb) / kapitalWlasnyUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaTempaZmian5", (((zobowiazania - zobowiazaniaUb) / zobowiazaniaUb) * 100), 0));
        db.addValue(new Wskazniki(rok, "AnalizaTempaZmian6", (((pasywaRazem - pasywaRazemUb) / pasywaRazemUb) * 100), 0));
        db.close();
    }

    public void przejdzWskPokrycia(View view) {
        setContentView(R.layout.analiza_pozioma_pokrycia);

        TextView wskPAT = (TextView) findViewById(R.id.wsk_pokrAT1);
        TextView wskPATU = (TextView) findViewById(R.id.wsk_pokrAT2);
        TextView wskPAO = (TextView) findViewById(R.id.wsk_pokrAO1);
        TextView wskPAOU = (TextView) findViewById(R.id.wsk_pokrAO2);

        wskPAT.setText(String.valueOf((kapitalWlasny / aktywaTrwale) * 100));
        wskPATU.setText(String.valueOf((kapitalWlasnyUb / aktywaTrwaleUb) * 100));
        wskPAO.setText(String.valueOf((zobowiazania / aktywaObrotowe) * 100));
        wskPAOU.setText(String.valueOf((zobowiazaniaUb / aktywaObrotoweUb) * 100));

        db = new DatabaseHandler(this);
        db.addValue(new Wskazniki(rok, "AnalizaPokrycia1", ((kapitalWlasny / aktywaTrwale) * 100), ((kapitalWlasnyUb / aktywaTrwaleUb) * 100)));
        db.addValue(new Wskazniki(rok, "AnalizaPokrycia2", ((zobowiazania / aktywaObrotowe) * 100), ((zobowiazaniaUb / aktywaObrotoweUb) * 100)));
        db.close();
    }
}
