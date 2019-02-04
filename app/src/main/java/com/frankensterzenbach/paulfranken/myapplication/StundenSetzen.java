package com.frankensterzenbach.paulfranken.myapplication;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


import static com.frankensterzenbach.paulfranken.myapplication.StundenSetzen.l;
import static com.frankensterzenbach.paulfranken.myapplication.StundenSetzen.list;
import static com.frankensterzenbach.paulfranken.myapplication.StundenSetzen.tasklaeuft;

public class StundenSetzen extends AppCompatActivity {
    public static Context c;
    public static ArrayList<String> list, stundenliste, gkliste, lk1liste,unterstufenliste;

    String url = "https://ifacharbeit.111mb.de/runterladen.php";

    String LK1, LK2;

    public static ListView listView;
    public Spinner s1,s2;
    static StundenSetzen instance;

    public Button btn;

    public static boolean tasklaeuft=false;

    public Calendar datumheute = Calendar.getInstance();

    public  CharSequence[] items = new CharSequence[0];
    public ArrayList seletedItems=new ArrayList();
    public  boolean[] checkedItems;
public static  SwipeRefreshLayout l;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_stunden_setzen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        checkedItems = new boolean[0];

        btn = (Button) findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                AlertDialog dialog = new AlertDialog.Builder(StundenSetzen.this)
                        .setTitle("Wähle deine GK's")
                        .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);

                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }

                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here

                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                //  Your code when user clicked on Cancel
                            }
                        }).setNeutralButton(R.string.clear_all_label, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                for (int m = 0; m < checkedItems.length; m++) {

                                    checkedItems[m] = false;

                                }
                            }
                        })

                        .create();
                dialog.show();

            }
        });



        l=(SwipeRefreshLayout)findViewById(R.id.swipe2);

        l.setColorSchemeResources(R.color.f1,R.color.f4,R.color.f7);//Farben festlegen

        l.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {

            @Override

            public void onRefresh() {

                l.setRefreshing(true);

                (new Handler()).postDelayed(new Runnable() {

                    @Override

                    public void run() {
if(tasklaeuft==false) {
    aktualisieren();
}else {
    Toast.makeText(StundenSetzen.this, "Die Liste wird bereits aktualisiert.", Toast.LENGTH_SHORT).show();
}
                        //Die Daten werden erneut aufgerufen


                    }

                },3000);



            }

        });







        s1=(Spinner)findViewById(R.id.LK1);
        s2=(Spinner)findViewById(R.id.LK2);

        c = getApplicationContext();
        final Downloader d = new Downloader(this, url);
        list = new ArrayList<>();
        if(isNetworkAvailable()==true) {
            new Test().execute();
            tasklaeuft=true;
        }

        gkliste = new ArrayList<>();
        unterstufenliste = new ArrayList<>();
        lk1liste = new ArrayList<>();







    }
    public void aktualisieren(){
        list.clear();
        gkliste.clear();
        lk1liste.clear();
        stundenliste.clear();
        Test t=new Test();
        t.execute();

    }

    public int farbe(String fach){
        int farbe=000;
        if(fach.equals("D ")||fach.equals("D")){
            farbe=-1427400;
        }else if(fach.equals("E")||fach.equals("E5")){
            farbe=-1414344;
        }else if(fach.equals("F")||fach.equals("F6")){
            farbe=-1401288;
        }else if(fach.equals("L")||fach.equals("L6")){
            farbe=-1401288;
        }else if(fach.equals("S")||fach.equals("S8")){
            farbe=-1396936;
        }

        else if(fach.equals("M ")||fach.equals("M")){
            farbe=-3874250;
        }else if(fach.equals("PH")||fach.equals("PH EXP")){
            farbe=-6038988;
        }else if(fach.equals("CH")||fach.equals("CH EXP")){
            farbe=-8794062;
        }else if(fach.equals("BI")||fach.equals("BI EXP")){
            farbe=-13780179;
        }else if(fach.equals("IF")){
            farbe=-14180244;
        }else if(fach.equals("TC")){
            farbe=-14513012;
        }

        else if(fach.equals("GE")){
            farbe=-13866858;
        }else if(fach.equals("SW")||fach.equals("PK")){
            farbe=-13479268;
        }else if(fach.equals("EK")){
            farbe=-12961117;
        }else if(fach.equals("PL")||fach.equals("PP")){
            farbe=-11651680;
        }else if(fach.equals("ER")){
            farbe=-10473058;
        }else if(fach.equals("KR")){
            farbe=-9163621;
        }
        else if(fach.equals("REL")){
            farbe=-9163621;
        }

        else if(fach.equals("KU")){
            farbe=-6871657;
        }else if(fach.equals("MU")){
            farbe=-4575619;
        }else if(fach.equals("LI")){
            farbe=-3198109;
        }else if(fach.equals("SP")){
            farbe=-11184811;
        }else if(fach.equals("SN")){
            farbe=-11184811;
        }
        else if(fach.equals("EIS")){
            farbe=-11184811;
        }else if(fach.equals("CO")){
            farbe=-5855578;
        }else if(fach.equals("OR")){
            farbe=-5855578;
        }else if(fach.equals("BigB")){
            farbe=-5855578;
        }

        else if(fach.equals("AG-Blaeser")){
            farbe=-5855578;
        }else if(fach.equals("AG-LEGO")){
            farbe=-5855578;
        }else if(fach.equals("AG-SP")){
            farbe=-5855578;
        }
        else if(fach.equals("LRS")){
            farbe=-6676889;
        }

        else if(fach.equals("FOE"+MainActivity.klasse.charAt(0))){
            farbe=-5855578;
        }

        else if(fach.equals("Diff-BI")||fach.equals("Diff-CH")||fach.equals("Diff-EK")||fach.equals("Diff-MU")||fach.equals("Diff-PH")||fach.equals("Diff-CO")){
            farbe=-6777668;
        }



        return farbe;
    }
    public void löschen(){
        SharedPreferences settings=getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor=settings.edit();
        editor.clear();
        editor.commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        löschen();

        if (id == R.id.action_settings2) {

            uLKs();
            if(LK1 !=null){
                stundeSetzen(LK1);
            }
            if(LK2!=null) {
                stundeSetzen(LK2);
            }
            for(int i=0;i<seletedItems.size();i++){
                stundeSetzen(gkliste.get(Integer.valueOf(seletedItems.get(i).toString())));
            }

this.finish();

        }
        return super.onOptionsItemSelected(item);
    }
    //Wenn mehr als nur die Q1 funktionieren soll muss das hier noch dazu
    public void stundeSetzen(String stunde) {



        for (int i = 0; i < list.size() - 7; i++) {
            //-7 ist notwendig um noch alles ohne nullpointer durchsuchen zu können
            if (list.get(i + 4).equals(stunde)) {

                if (list.get(i + 2).equals(MainActivity.klasse)) {


if(stunde.length()==6) {

    if (list.get(i + 2).equals("Q1") || list.get(i + 2).equals("Q2") || list.get(i + 2).equals("EF")) {


        MainActivity.alleStunden.get(platzbestimmer(i)).löschen();
        MainActivity.alleStunden.get(platzbestimmer(i)).setTextColor(Color.BLACK);


        if (stunde.equals("BigB")) {
            MainActivity.alleStunden.get(platzbestimmer(i)).fach = "BigB";
            MainActivity.alleStunden.get(platzbestimmer(i)).farbe = String.valueOf(-5855578);
        } else {
            MainActivity.alleStunden.get(platzbestimmer(i)).farbe = String.valueOf(farbe("" + stunde.charAt(0) + stunde.charAt(1)));
            MainActivity.alleStunden.get(platzbestimmer(i)).fach = "" + stunde.charAt(0) + stunde.charAt(1);
        }
        MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();

        MainActivity.alleStunden.get(platzbestimmer(i)).setText(MainActivity.alleStunden.get(platzbestimmer(i)).fach + "\n" + "\n" + list.get(i + 5));


        if (stunde.length() > 2) {
            MainActivity.alleStunden.get(platzbestimmer(i)).kurs = "" + stunde.charAt(3);
        }

        MainActivity.alleStunden.get(platzbestimmer(i)).platz = String.valueOf(platzbestimmer(i));
        if (stunde.length() > 4) {
            MainActivity.alleStunden.get(platzbestimmer(i)).nummer = "" + stunde.charAt(5);

        }
        MainActivity.alleStunden.get(platzbestimmer(i)).raum = list.get(i + 5);
        MainActivity.alleStunden.get(platzbestimmer(i)).lehrer = list.get(i + 3);

        MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();

        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        if (weekDay.equals("Monday")) {

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 6);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Tuesday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Wednesday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Thursday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Friday")) {

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
        }
        if (weekDay.equals("Saturday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Sunday")) {

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -6);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
        }

        MainActivity.alleStunden.get(platzbestimmer(i)).aktualisieren();
        speichern();

        widget_speichern();
        WidgetProvider.updateWidget(getApplicationContext());

    } else {
        MainActivity.alleStunden.get(platzbestimmer(i)).löschen();
        MainActivity.alleStunden.get(platzbestimmer(i)).fach = "" + stunde;
        MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();
        MainActivity.alleStunden.get(platzbestimmer(i)).setText(MainActivity.alleStunden.get(platzbestimmer(i)).fach + "\n" + "\n" + list.get(i + 5));
        MainActivity.alleStunden.get(platzbestimmer(i)).farbe = String.valueOf(farbe("" + stunde));

        MainActivity.alleStunden.get(platzbestimmer(i)).kurs = "";
        MainActivity.alleStunden.get(platzbestimmer(i)).platz = String.valueOf(platzbestimmer(i));
        MainActivity.alleStunden.get(platzbestimmer(i)).lehrer = list.get(i + 3);

        MainActivity.alleStunden.get(platzbestimmer(i)).nummer = "";
        MainActivity.alleStunden.get(platzbestimmer(i)).raum = list.get(i + 5);


        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

        Calendar calendar = Calendar.getInstance();
        weekDay = dayFormat.format(calendar.getTime());

        if (weekDay.equals("Monday")) {

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 6);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Tuesday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Wednesday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Thursday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Friday")) {

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
        }
        if (weekDay.equals("Saturday")) {
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

        }
        if (weekDay.equals("Sunday")) {

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                datumheute.add(Calendar.DATE, -6);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                datumheute.add(Calendar.DATE, -5);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                datumheute.add(Calendar.DATE, -4);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                datumheute.add(Calendar.DATE, -3);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }

            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                datumheute.add(Calendar.DATE, -2);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                datumheute.add(Calendar.DATE, -1);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
            if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                datumheute.add(Calendar.DATE, 0);
                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                String formatted = format1.format(datumheute.getTime());
                MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

            }
        }

        MainActivity.alleStunden.get(platzbestimmer(i)).aktualisieren();
        speichern();

        widget_speichern();
        WidgetProvider.updateWidget(getApplicationContext());
    }
}else if(stunde.length()==5){
    {

        if (list.get(i + 2).equals("Q1") || list.get(i + 2).equals("Q2") || list.get(i + 2).equals("EF")) {


            MainActivity.alleStunden.get(platzbestimmer(i)).löschen();
            MainActivity.alleStunden.get(platzbestimmer(i)).setTextColor(Color.BLACK);


            if (stunde.equals("BigB")) {
                MainActivity.alleStunden.get(platzbestimmer(i)).fach = "BigB";
                MainActivity.alleStunden.get(platzbestimmer(i)).farbe = String.valueOf(-5855578);
            } else {
                MainActivity.alleStunden.get(platzbestimmer(i)).farbe = String.valueOf(farbe("" + stunde.charAt(0) ));
                MainActivity.alleStunden.get(platzbestimmer(i)).fach = "" + stunde.charAt(0);
            }
            MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();
            MainActivity.alleStunden.get(platzbestimmer(i)).setText(MainActivity.alleStunden.get(platzbestimmer(i)).fach + "\n" + "\n" + list.get(i + 5));


            if (stunde.length() > 2) {
                MainActivity.alleStunden.get(platzbestimmer(i)).kurs = "" + stunde.charAt(2);
            }

            MainActivity.alleStunden.get(platzbestimmer(i)).platz = String.valueOf(platzbestimmer(i));
            if (stunde.length() > 4) {
                MainActivity.alleStunden.get(platzbestimmer(i)).nummer = "" + stunde.charAt(4);

            }
            MainActivity.alleStunden.get(platzbestimmer(i)).raum = list.get(i + 5);
            MainActivity.alleStunden.get(platzbestimmer(i)).lehrer = list.get(i + 3);

            MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();

            String weekDay;
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

            Calendar calendar = Calendar.getInstance();
            weekDay = dayFormat.format(calendar.getTime());

            if (weekDay.equals("Monday")) {

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 6);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Tuesday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Wednesday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Thursday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Friday")) {

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
            }
            if (weekDay.equals("Saturday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Sunday")) {

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -6);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
            }

            MainActivity.alleStunden.get(platzbestimmer(i)).aktualisieren();
            speichern();

            widget_speichern();
            WidgetProvider.updateWidget(getApplicationContext());

        } else {
            MainActivity.alleStunden.get(platzbestimmer(i)).löschen();
            MainActivity.alleStunden.get(platzbestimmer(i)).fach = "" + stunde;
            MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();
            MainActivity.alleStunden.get(platzbestimmer(i)).setText(MainActivity.alleStunden.get(platzbestimmer(i)).fach + "\n" + "\n" + list.get(i + 5));
            MainActivity.alleStunden.get(platzbestimmer(i)).farbe = String.valueOf(farbe("" + stunde));

            MainActivity.alleStunden.get(platzbestimmer(i)).kurs = "";
            MainActivity.alleStunden.get(platzbestimmer(i)).platz = String.valueOf(platzbestimmer(i));
            MainActivity.alleStunden.get(platzbestimmer(i)).lehrer = list.get(i + 3);

            MainActivity.alleStunden.get(platzbestimmer(i)).nummer = "";
            MainActivity.alleStunden.get(platzbestimmer(i)).raum = list.get(i + 5);


            String weekDay;
            SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

            Calendar calendar = Calendar.getInstance();
            weekDay = dayFormat.format(calendar.getTime());

            if (weekDay.equals("Monday")) {

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 6);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Tuesday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Wednesday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Thursday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Friday")) {

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
            }
            if (weekDay.equals("Saturday")) {
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

            }
            if (weekDay.equals("Sunday")) {

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")) {
                    datumheute.add(Calendar.DATE, -6);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")) {
                    datumheute.add(Calendar.DATE, -5);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")) {
                    datumheute.add(Calendar.DATE, -4);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")) {
                    datumheute.add(Calendar.DATE, -3);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }

                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")) {
                    datumheute.add(Calendar.DATE, -2);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")) {
                    datumheute.add(Calendar.DATE, -1);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
                if (MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")) {
                    datumheute.add(Calendar.DATE, 0);
                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                    String formatted = format1.format(datumheute.getTime());
                    MainActivity.alleStunden.get(platzbestimmer(i)).datum = formatted;

                }
            }

            MainActivity.alleStunden.get(platzbestimmer(i)).aktualisieren();
            speichern();

            widget_speichern();
            WidgetProvider.updateWidget(getApplicationContext());
        }
    }
}
                }
            }

        }
    }
    public   void  widget_speichern(){

        ArrayList<String> fach=new ArrayList<>();
        ArrayList<String> raum=new ArrayList<>();

//alle facher informationen
        fach.clear();

        fach.add(MainActivity.alleStunden.get(1).fach);
        fach.add(MainActivity.alleStunden.get(7).fach);
        fach.add(MainActivity.alleStunden.get(13).fach);
        fach.add(MainActivity.alleStunden.get(19).fach);
        fach.add(MainActivity.alleStunden.get(25).fach);
        fach.add(MainActivity.alleStunden.get(31).fach);
        fach.add(MainActivity.alleStunden.get(37).fach);
        fach.add(MainActivity.alleStunden.get(43).fach);
        fach.add(MainActivity.alleStunden.get(49).fach);
        fach.add(MainActivity.alleStunden.get(55).fach);
        fach.add(MainActivity.alleStunden.get(61).fach);



        fach.add(MainActivity.alleStunden.get(2).fach);
        fach.add(MainActivity.alleStunden.get(8).fach);
        fach.add(MainActivity.alleStunden.get(14).fach);
        fach.add(MainActivity.alleStunden.get(20).fach);
        fach.add(MainActivity.alleStunden.get(26).fach);
        fach.add(MainActivity.alleStunden.get(32).fach);
        fach.add(MainActivity.alleStunden.get(38).fach);
        fach.add(MainActivity.alleStunden.get(44).fach);
        fach.add(MainActivity.alleStunden.get(50).fach);
        fach.add(MainActivity.alleStunden.get(56).fach);
        fach.add(MainActivity.alleStunden.get(62).fach);



        fach.add(MainActivity.alleStunden.get(3).fach);
        fach.add(MainActivity.alleStunden.get(9).fach);
        fach.add(MainActivity.alleStunden.get(15).fach);
        fach.add(MainActivity.alleStunden.get(21).fach);
        fach.add(MainActivity.alleStunden.get(27).fach);
        fach.add(MainActivity.alleStunden.get(33).fach);
        fach.add(MainActivity.alleStunden.get(39).fach);
        fach.add(MainActivity.alleStunden.get(45).fach);
        fach.add(MainActivity.alleStunden.get(51).fach);
        fach.add(MainActivity.alleStunden.get(57).fach);
        fach.add(MainActivity.alleStunden.get(63).fach);



        fach.add(MainActivity.alleStunden.get(4).fach);
        fach.add(MainActivity.alleStunden.get(10).fach);
        fach.add(MainActivity.alleStunden.get(16).fach);
        fach.add(MainActivity.alleStunden.get(22).fach);
        fach.add(MainActivity.alleStunden.get(28).fach);
        fach.add(MainActivity.alleStunden.get(34).fach);
        fach.add(MainActivity.alleStunden.get(40).fach);
        fach.add(MainActivity.alleStunden.get(46).fach);
        fach.add(MainActivity.alleStunden.get(52).fach);
        fach.add(MainActivity.alleStunden.get(58).fach);
        fach.add(MainActivity.alleStunden.get(64).fach);



        fach.add(MainActivity.alleStunden.get(5).fach);
        fach.add(MainActivity.alleStunden.get(11).fach);
        fach.add(MainActivity.alleStunden.get(17).fach);
        fach.add(MainActivity.alleStunden.get(23).fach);
        fach.add(MainActivity.alleStunden.get(29).fach);
        fach.add(MainActivity.alleStunden.get(35).fach);
        fach.add(MainActivity.alleStunden.get(41).fach);
        fach.add(MainActivity.alleStunden.get(47).fach);
        fach.add(MainActivity.alleStunden.get(53).fach);
        fach.add(MainActivity.alleStunden.get(59).fach);
        fach.add(MainActivity.alleStunden.get(65).fach);

        for(int i=0;i<fach.size();i++){
            if(fach.get(i).equals("")){
                fach.set(i," ");
            }
        }


        //fach
        if(fach.size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String s: fach){

                stringBuilder.append(s);
                stringBuilder.append(",");


            }



            SharedPreferences settings=getSharedPreferences("fach",0);
            SharedPreferences.Editor editor=settings.edit();

            editor.putString("fach",stringBuilder.toString());

            editor.commit();

        }else if(fach.size()==0){
            SharedPreferences settings=getSharedPreferences("fach",0);
            SharedPreferences.Editor editor=settings.edit();
            editor.clear();
            editor.commit();
        }



        //alle raum informationen


        raum.clear();

        raum.add(MainActivity.alleStunden.get(1).raum);
        raum.add(MainActivity.alleStunden.get(7).raum);
        raum.add(MainActivity.alleStunden.get(13).raum);
        raum.add(MainActivity.alleStunden.get(19).raum);
        raum.add(MainActivity.alleStunden.get(25).raum);
        raum.add(MainActivity.alleStunden.get(31).raum);
        raum.add(MainActivity.alleStunden.get(37).raum);
        raum.add(MainActivity.alleStunden.get(43).raum);
        raum.add(MainActivity.alleStunden.get(49).raum);
        raum.add(MainActivity.alleStunden.get(55).raum);
        raum.add(MainActivity.alleStunden.get(61).raum);



        raum.add(MainActivity.alleStunden.get(2).raum);
        raum.add(MainActivity.alleStunden.get(8).raum);
        raum.add(MainActivity.alleStunden.get(14).raum);
        raum.add(MainActivity.alleStunden.get(20).raum);
        raum.add(MainActivity.alleStunden.get(26).raum);
        raum.add(MainActivity.alleStunden.get(32).raum);
        raum.add(MainActivity.alleStunden.get(38).raum);
        raum.add(MainActivity.alleStunden.get(44).raum);
        raum.add(MainActivity.alleStunden.get(50).raum);
        raum.add(MainActivity.alleStunden.get(56).raum);
        raum.add(MainActivity.alleStunden.get(62).raum);



        raum.add(MainActivity.alleStunden.get(3).raum);
        raum.add(MainActivity.alleStunden.get(9).raum);
        raum.add(MainActivity.alleStunden.get(15).raum);
        raum.add(MainActivity.alleStunden.get(21).raum);
        raum.add(MainActivity.alleStunden.get(27).raum);
        raum.add(MainActivity.alleStunden.get(33).raum);
        raum.add(MainActivity.alleStunden.get(39).raum);
        raum.add(MainActivity.alleStunden.get(45).raum);
        raum.add(MainActivity.alleStunden.get(51).raum);
        raum.add(MainActivity.alleStunden.get(57).raum);
        raum.add(MainActivity.alleStunden.get(63).raum);



        raum.add(MainActivity.alleStunden.get(4).raum);
        raum.add(MainActivity.alleStunden.get(10).raum);
        raum.add(MainActivity.alleStunden.get(16).raum);
        raum.add(MainActivity.alleStunden.get(22).raum);
        raum.add(MainActivity.alleStunden.get(28).raum);
        raum.add(MainActivity.alleStunden.get(34).raum);
        raum.add(MainActivity.alleStunden.get(40).raum);
        raum.add(MainActivity.alleStunden.get(46).raum);
        raum.add(MainActivity.alleStunden.get(52).raum);
        raum.add(MainActivity.alleStunden.get(58).raum);
        raum.add(MainActivity.alleStunden.get(64).raum);



        raum.add(MainActivity.alleStunden.get(5).raum);
        raum.add(MainActivity.alleStunden.get(11).raum);
        raum.add(MainActivity.alleStunden.get(17).raum);
        raum.add(MainActivity.alleStunden.get(23).raum);
        raum.add(MainActivity.alleStunden.get(29).raum);
        raum.add(MainActivity.alleStunden.get(35).raum);
        raum.add(MainActivity.alleStunden.get(41).raum);
        raum.add(MainActivity.alleStunden.get(47).raum);
        raum.add(MainActivity.alleStunden.get(53).raum);
        raum.add(MainActivity.alleStunden.get(59).raum);
        raum.add(MainActivity.alleStunden.get(65).raum);

        for(int i=0;i<raum.size();i++){
            if(raum.get(i).equals("")){
                raum.set(i," ");
            }
        }


        //raum
        if(raum.size()>0){
            StringBuilder stringBuilder=new StringBuilder();
            for(String s: raum){

                stringBuilder.append(s);
                stringBuilder.append(",");


            }



            SharedPreferences settings=getSharedPreferences("raum",0);
            SharedPreferences.Editor editor=settings.edit();

            editor.putString("raum",stringBuilder.toString());

            editor.commit();

        }else if(raum.size()==0){
            SharedPreferences settings=getSharedPreferences("raum",0);
            SharedPreferences.Editor editor=settings.edit();
            editor.clear();
            editor.commit();
        }

    }
    public  void speichern(){
        umwandelnhin();
        StringBuilder stringBuilder=new StringBuilder();
        for(String s: MainActivity.speichern_laden){
            stringBuilder.append(s);
            stringBuilder.append(",");


        }


        SharedPreferences settings=getSharedPreferences("PREFS",0);
        SharedPreferences.Editor editor=settings.edit();

        editor.putString("words",stringBuilder.toString());

        editor.commit();

    }

    public void umwandelnhin() {
        ArrayList<String> t = new ArrayList<String>();
        MainActivity.speichern_laden = t;

        for (int i = 0; i < MainActivity.alleStunden.size(); i++) {

            if (!MainActivity.alleStunden.get(i).farbe.equals("")) {

                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).getText()));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).farbe));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).kurs));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).nummer));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).datum));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).platz));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).fach));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).raum));
                MainActivity.speichern_laden.add(String.valueOf(MainActivity.alleStunden.get(i).lehrer));


            }
        }
    }
    public int platzbestimmer(int i){
        int tag,stunde,returner;
        tag=Integer.parseInt(list.get(i+6));
        stunde=Integer.parseInt(list.get(i+7));
        stunde= stunde -1;
        returner= stunde*6+tag;
        return returner;
    }
    public void listenladen(){
        stundenListe();



        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, lk1liste); //selected item will look like a spinner set from XML
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s1.setAdapter(spinnerArrayAdapter);


            s2.setAdapter(spinnerArrayAdapter);


        items=gkliste.toArray(new CharSequence[gkliste.size()]);


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    public void stundenListe(){
        stundenliste= new ArrayList<>();
        for(int i=0; i<list.size();i++){
            if(list.get(i+2).equals(MainActivity.klasse)){
                if(!list.get(i+2).equals("Q1")&&!list.get(i+2).equals("Q2")&!list.get(i+2).equals("EF")){
                    unterstufenliste.add(list.get(i+4));


                }else {


                    stundenliste.add(list.get(i + 4));

                }

                    }


            i = i+7;
        }

        Set<String> hs2 = new HashSet<>();
        hs2.addAll(unterstufenliste);
        unterstufenliste.clear();
        unterstufenliste.addAll(hs2);

        gkliste=unterstufenliste;


        // add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(stundenliste);
        stundenliste.clear();
        stundenliste.addAll(hs);


        for(int i=0; i<stundenliste.size(); i++){
           Log.d(stundenliste.get(i),""+stundenliste.get(i).length());

            if(stundenliste.get(i).length()==2||stundenliste.get(i).equals("BigB")) {

                    gkliste.add(stundenliste.get(i));

            }

            else if(stundenliste.get(i).length()>=4) {

                String stunde1 = String.valueOf(stundenliste.get(i).charAt(3));
                String stunde2 = String.valueOf(stundenliste.get(i).charAt(2));

                 if(stunde1.equals("G")||stunde2.equals("G")){
                     gkliste.add(stundenliste.get(i));

                 }

                 if(stunde1.equals("Z")||stunde2.equals("Z")){
                     gkliste.add(stundenliste.get(i));
                 }
                if(stunde1.equals("L")||stunde2.equals("L")){
                     stunde1 = String.valueOf(stundenliste.get(i).charAt(0));
                     if(stunde1.equals("A")||stunde2.equals("A")){
                         gkliste.add(stundenliste.get(i));
                     }else{
                         lk1liste.add(stundenliste.get(i));
                     }




                }

            }


        }

        Collections.sort(gkliste);


        Collections.sort(lk1liste);
        lk1liste.add(0,"Wähle deine LK's.");

        checkedItems = new boolean[gkliste.size()];
    }
    @Override//Muss für das Menü vorhanden sein. In der Variable mymenu wird das Meu geschpeichert
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stunden_setzen, menu);

        return true;
    }
    public void uLKs(){
        if(s1.getSelectedItem()!=null) {
            LK1 = s1.getSelectedItem().toString();
        } else {
            LK1="";
        }
            if(s2.getSelectedItem()!=null) {
                LK2 = s2.getSelectedItem().toString();
            }else{
                LK2="";
            }

    }

    public static StundenSetzen getInstance(){
        return instance;
    }




}

class Test extends AsyncTask<Void,Void,Void> {
    String test="";
    public Test(){

    }

    @Override
    protected Void doInBackground(Void... voids) {
        test= Downloader.downloadData();
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        l.setRefreshing(false);
        tasklaeuft=false;
        try
        {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja=new JSONArray(test);

            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo=null;

            list.clear();

            //LOOP THRU ARRAY

            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);

                //RETRIOEVE NAME
                String ID=jo.getString("ID");
                String Nummer=jo.getString("Nummer");
                String Klasse=jo.getString("Klasse");
                String Lehrer=jo.getString("Lehrer");
                String Fach=jo.getString("Fach");
                String Raum=jo.getString("Raum");
                String Tag=jo.getString("Tag");
                String Stunde=jo.getString("Stunde");

                //ADD IT TO OUR ARRAYLIST
                list.add(ID);
                list.add(Nummer);
                list.add(Klasse);
                list.add(Lehrer);
                list.add(Fach);
                list.add(Raum);
                list.add(Tag);
                list.add(Stunde);
                Log.d("Hallo",Klasse);



            }




        } catch (JSONException e) {
            e.printStackTrace();
        }


        StundenSetzen instance = StundenSetzen.getInstance();
        instance.listenladen();



    }
}