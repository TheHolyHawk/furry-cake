package com.example.paulfranken.myapplication;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;


import static com.example.paulfranken.myapplication.StundenSetzen.c;
import static com.example.paulfranken.myapplication.StundenSetzen.list;
import static com.example.paulfranken.myapplication.WidgetProvider.raum2;
import static com.example.paulfranken.myapplication.WidgetProvider.stunde;

public class StundenSetzen extends AppCompatActivity {
    public static Context c;
    public static ArrayList<String> list, stundenliste, gkliste, lk1liste,lk2liste;
    public static int dummy;
    String url = "http://facharbeit.square7.ch/Facharbeit:HochundRunter/get.php";
    Spinner LK1S, LK2S;
    String LK1, LK2;
    Model modelItems[];
    public static ListView listView;
    public Spinner s1,s2;
    static StundenSetzen instance;

    public Button btn;

    public Calendar datumheute = Calendar.getInstance();

    public  CharSequence[] items = {""};
    public ArrayList seletedItems=new ArrayList();
    public  boolean[] checkedItems;

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
                                    Toast.makeText(StundenSetzen.this, ""+seletedItems.get(0), Toast.LENGTH_SHORT).show();
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










        s1=(Spinner)findViewById(R.id.LK1);
        s2=(Spinner)findViewById(R.id.LK2);

        c = getApplicationContext();
        final Downloader d = new Downloader(this, url);
        list = new ArrayList<>();
        if(isNetworkAvailable()==true) {
            new Test().execute();
        }

        gkliste = new ArrayList<>();
        lk1liste = new ArrayList<>();
        lk2liste = new ArrayList<>();




    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings2) {

            uLKs();
            stundeSetzen(LK1);
            stundeSetzen(LK2);
            for(int i=0;i<seletedItems.size();i++){
                stundeSetzen(gkliste.get(Integer.valueOf(seletedItems.get(i).toString())));
            }

this.finish();

        }
        return super.onOptionsItemSelected(item);
    }
    //Wenn mehr als nur die Q1 funktionieren soll muss das hier noch dazu
    public void stundeSetzen(String stunde){
        for(int i=0; i<list.size()-7; i++){
            //-7 ist notwendig um noch alles ohne nullpointer durchsuchen zu können
            if(list.get(i+4).equals(stunde)){

                MainActivity.alleStunden.get(platzbestimmer(i)).löschen();
                MainActivity.alleStunden.get(platzbestimmer(i)).setText(stunde+"\n"+"\n"+list.get(i+5));
                MainActivity.alleStunden.get(platzbestimmer(i)).farbe=String.valueOf(-769482);
                MainActivity.alleStunden.get(platzbestimmer(i)).fach=""+stunde.charAt(0)+stunde.charAt(1);
                MainActivity.alleStunden.get(platzbestimmer(i)).kurs=""+stunde.charAt(3);
                MainActivity.alleStunden.get(platzbestimmer(i)).platz=String.valueOf(platzbestimmer(i));
                MainActivity.alleStunden.get(platzbestimmer(i)).nummer=""+stunde.charAt(4);
                MainActivity.alleStunden.get(platzbestimmer(i)).raum=list.get(i+5);

                MainActivity.alleStunden.get(platzbestimmer(i)).umwandeln();

                String weekDay;
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);

                Calendar calendar = Calendar.getInstance();
                weekDay = dayFormat.format(calendar.getTime());

                if(weekDay.equals("Monday")){

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,4);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,5);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,6);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                }
                if(weekDay.equals("Tuesday")){
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,-1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,4);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,5);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                }
                if(weekDay.equals("Wednesday")){
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,-2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,-1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,4);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                }
                if(weekDay.equals("Thursday")){
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,-3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,-2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,-1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                }
                if(weekDay.equals("Friday")){

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,-4);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,-3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,-2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,-1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                }
                if(weekDay.equals("Saturday")){
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,-5);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,-4);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,-3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,-2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,-1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                }
                if(weekDay.equals("Sunday")){

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Montag")){
                        datumheute.add(Calendar.DATE,-6);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Dienstag")){
                        datumheute.add(Calendar.DATE,-5);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Mittwoch")){
                        datumheute.add(Calendar.DATE,-4);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Donnerstag")){
                        datumheute.add(Calendar.DATE,-3);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }

                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Freitag")){
                        datumheute.add(Calendar.DATE,-2);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                    if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Samstag")){
                        datumheute.add(Calendar.DATE,-1);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    } if( MainActivity.alleStunden.get(platzbestimmer(i)).tag.equals("Sonntag")){
                        datumheute.add(Calendar.DATE,0);
                        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
                        String formatted = format1.format(datumheute.getTime());
                        MainActivity.alleStunden.get(platzbestimmer(i)).datum=formatted;

                    }
                }

                MainActivity.alleStunden.get(platzbestimmer(i)).aktualisieren();
                speichern();

                widget_speichern();
                WidgetProvider.updateWidget(getApplicationContext());

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

        ArrayAdapter<String> spinneradapter2 = new ArrayAdapter<String>
                (this, android.R.layout.simple_spinner_item, lk2liste); //selected item will look like a spinner set from XML
        spinneradapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s2.setAdapter(spinneradapter2);


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
            if(list.get(i+2).equals("Q1")){


                       stundenliste.add(list.get(i+4));


                    }


            i = i+7;
        }




// add elements to al, including duplicates
        Set<String> hs = new HashSet<>();
        hs.addAll(stundenliste);
        stundenliste.clear();
        stundenliste.addAll(hs);


        for(int i=0; i<stundenliste.size(); i++){

            if(stundenliste.get(i).length()==2) {

                    gkliste.add(stundenliste.get(i));

            }else if(stundenliste.get(i).length()>=4) {
                String stunde1 = String.valueOf(stundenliste.get(i).charAt(3));
                 if(stunde1.equals("G")){
                     gkliste.add(stundenliste.get(i));
                 }
                if(stunde1.equals("L")){
                    String stunde2 = String.valueOf(stundenliste.get(i).charAt(4));
                    if(stunde2.equals("1")){
                        lk1liste.add(stundenliste.get(i));
                    }else  if(stunde2.equals("2")||stunde2.equals("3")){
                        lk2liste.add(stundenliste.get(i));
                    }

                }
            }


        }

        Collections.sort(gkliste);


        Collections.sort(lk1liste);
        Collections.sort(lk2liste);
        checkedItems = new boolean[gkliste.size()];
    }
    @Override//Muss für das Menü vorhanden sein. In der Variable mymenu wird das Meu geschpeichert
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_stunden_setzen, menu);

        return true;
    }
    public void uLKs(){
        LK1 = s1.getSelectedItem().toString();
        LK2 = s2.getSelectedItem().toString();

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

            }



        } catch (JSONException e) {
            e.printStackTrace();
        }


        StundenSetzen instance = StundenSetzen.getInstance();
        instance.listenladen();



    }
}