package com.example.paulfranken.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class Einstellungen extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
Spinner klasse;
public Button btn1,btn2;
public ArrayList<String>speichern_laden;
Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_einstellungen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context=getApplicationContext();

        klasse=(Spinner)findViewById(R.id.spinner);
        klasse.setOnItemSelectedListener(this);

        String str;
        str = MainActivity.klasse;

        if(str != null && !str.isEmpty()){
            setzeKlasse();
        }
         //Button zum Erstellen eines Backups des Stundenplans
        btn1=(Button)findViewById(R.id.backup1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               //Abfrage ob bereites ein BackUp besteht
                SharedPreferences settings=getSharedPreferences("Sicherheit",0);
                String test= settings.getString("s",null);
                if(test!=null){//Wenn bereits eins besteht

                    new AlertDialog.Builder( Einstellungen.this )
                            .setTitle( "BackUp" )
                            .setMessage("Es exestiert bereits eine Sicherheitsdatei. Möchtest du diese überschreiben?")

                            .setPositiveButton( "Save", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                   speichern();
                                }
                            })
                            .setNegativeButton( "Cancel", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            } )
                            .show();
                }else{//Noch keine BackUPversion besteht
                  speichern();
                }






                }
        });
//Button zum Laden eines Backups des Stundenplans
        btn2=(Button)findViewById(R.id.backup2);
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences settings=getSharedPreferences("Sicherheit",0);
                String test= settings.getString("s",null);
                if(test!=null){

      Laden();
            speichern2();

            }
            else{
                    Toast.makeText(context, "Pech gehabt. Du hast keine Sicherheitsdatei erstellt.Hahaha", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



    public  void speichern(){
        umwandelnhin();
        if(speichern_laden.size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String s: MainActivity.speichern_laden){

                stringBuilder.append(s);
                stringBuilder.append(",");


            }


            SharedPreferences settings=getSharedPreferences("Sicherheit",0);
            SharedPreferences.Editor editor=settings.edit();

            editor.putString("s",stringBuilder.toString());

            editor.commit();

        }else if(speichern_laden.size()==0){
            SharedPreferences settings=getSharedPreferences("raume",0);
            SharedPreferences.Editor editor=settings.edit();
            editor.clear();
            editor.commit();
        }

    }
    public void   Laden(){


        SharedPreferences settings=getSharedPreferences("Sicherheit",0);


        String test= settings.getString("s",null);

        if(test!=null){
            String[]itemwors=test.split(",");
            ArrayList<String>worte=new ArrayList<>();
            for(int i=0;i<itemwors.length;i++) {

                worte.add(itemwors[i]);
                speichern_laden = worte;


            }
        }



        umwandelnzuruck();



    }
    public void umwandelnzuruck() {
        int platz;


        if(speichern_laden.size()!=0) {
            for (int i = 0; i < speichern_laden.size(); i++) {
                platz=Integer.parseInt(speichern_laden.get(i+5));



                MainActivity.alleStunden.get(platz).setText(speichern_laden.get(i+6)+"\n"+"\n"+speichern_laden.get(i+7));
                MainActivity.alleStunden.get(platz).farbe = speichern_laden.get(i+1);
                MainActivity.alleStunden.get(platz).kurs = speichern_laden.get(i + 2);
                MainActivity.alleStunden.get(platz).nummer = speichern_laden.get(i + 3);
                MainActivity.alleStunden.get(platz).datum = speichern_laden.get(i + 4);
                MainActivity.alleStunden.get(platz).aktualisieren();
                MainActivity.alleStunden.get(platz).platz = speichern_laden.get(i + 5);
                MainActivity.alleStunden.get(platz).fach = speichern_laden.get(i + 6);
                MainActivity.alleStunden.get(platz).raum=speichern_laden.get(i+7);



                i = i + 7;



            }
        }
    }


    public  void speichern2(){
        umwandelnhin();
        if(speichern_laden.size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String s: MainActivity.speichern_laden){

                stringBuilder.append(s);
                stringBuilder.append(",");


            }


            SharedPreferences settings=getSharedPreferences("PREFS",0);
            SharedPreferences.Editor editor=settings.edit();

            editor.putString("words",stringBuilder.toString());

            editor.commit();

        }else if(speichern_laden.size()==0){
            SharedPreferences settings=getSharedPreferences("raume",0);
            SharedPreferences.Editor editor=settings.edit();
            editor.clear();
            editor.commit();
        }

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

    public void umwandelnhin() {
        ArrayList<String> t=new ArrayList<String>();
        speichern_laden=t;

        for (int i = 0; i < MainActivity.alleStunden.size(); i++) {

            if(! MainActivity.alleStunden.get(i).farbe.equals("")) {

                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).getText()));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).farbe));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).kurs));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).nummer));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).datum));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).platz));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).fach));
                speichern_laden.add(String.valueOf( MainActivity.alleStunden.get(i).raum));



            }








        }


    }
}
