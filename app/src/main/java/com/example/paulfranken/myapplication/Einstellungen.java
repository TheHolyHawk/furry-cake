package com.example.paulfranken.myapplication;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class Einstellungen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Spinner klasse;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        klasse=(Spinner)findViewById(R.id.spinner);
        klasse.setOnItemSelectedListener(this);
       setzeKlasse();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        MainActivity.klasse=(String)klasse.getSelectedItem();
        speichern_einstellungen();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    public  void speichern_einstellungen() {
        SharedPreferences settings = getSharedPreferences("Einstellungen", 0);
        SharedPreferences.Editor editor = settings.edit();

        editor.putString("words2", MainActivity.klasse);

        editor.commit();
    }
    public void setzeKlasse(){

        if (MainActivity.klasse.equals("Q1")) {

            klasse.setSelection(26);
        }else
        if (MainActivity.klasse.equals("EF")) {

            klasse.setSelection(25);
        }else
        if (MainActivity.klasse.equals("Q2")) {

            klasse.setSelection(27);
        }else
        if (MainActivity.klasse.equals("5A")) {

            klasse.setSelection(0);
        }else
        if (MainActivity.klasse.equals("5B")) {

            klasse.setSelection(1);
        }
        else
        if (MainActivity.klasse.equals("5C")) {

            klasse.setSelection(2);
        }else
        if (MainActivity.klasse.equals("5D")) {

            klasse.setSelection(3);
        }else
        if (MainActivity.klasse.equals("5E")) {

            klasse.setSelection(4);
        }else
        if (MainActivity.klasse.equals("6A")) {

            klasse.setSelection(5);
        }else
        if (MainActivity.klasse.equals("6B")) {

            klasse.setSelection(6);
        }
        else
        if (MainActivity.klasse.equals("6C")) {

            klasse.setSelection(7);
        }else
        if (MainActivity.klasse.equals("6D")) {

            klasse.setSelection(8);
        }else
        if (MainActivity.klasse.equals("6E")) {

            klasse.setSelection(9);
        }else
        if (MainActivity.klasse.equals("7A")) {

            klasse.setSelection(10);
        }else
        if (MainActivity.klasse.equals("7B")) {

            klasse.setSelection(11);
        }
        else
        if (MainActivity.klasse.equals("7C")) {

            klasse.setSelection(12);
        }else
        if (MainActivity.klasse.equals("7D")) {

            klasse.setSelection(13);
        }else
        if (MainActivity.klasse.equals("7E")) {

            klasse.setSelection(14);
        }else
        if (MainActivity.klasse.equals("8A")) {

            klasse.setSelection(15);
        }else
        if (MainActivity.klasse.equals("8B")) {

            klasse.setSelection(16);
        }
        else
        if (MainActivity.klasse.equals("8C")) {

            klasse.setSelection(17);
        }else
        if (MainActivity.klasse.equals("8D")) {

            klasse.setSelection(18);
        }else
        if (MainActivity.klasse.equals("8E")) {

            klasse.setSelection(19);
        }else
        if (MainActivity.klasse.equals("9A")) {

            klasse.setSelection(20);
        }else
        if (MainActivity.klasse.equals("9B")) {

            klasse.setSelection(21);
        }
        else
        if (MainActivity.klasse.equals("9C")) {

            klasse.setSelection(22);
        }else
        if (MainActivity.klasse.equals("9D")) {

            klasse.setSelection(23);
        }else
        if (MainActivity.klasse.equals("9E")) {

            klasse.setSelection(24);
        }
    }
}
