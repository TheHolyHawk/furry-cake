package com.example.paulfranken.myapplication;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;

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
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


import static com.example.paulfranken.myapplication.StundenSetzen.c;
import static com.example.paulfranken.myapplication.StundenSetzen.list;

public class StundenSetzen extends AppCompatActivity {
    public static Context c;
    public static ArrayList<String> list, stundenliste, gkliste, lk1liste;
    public static int dummy;
    String url = "http://facharbeit.square7.ch/Facharbeit:HochundRunter/get.php";
    Spinner LK1S, LK2S;
    String LK1, LK2;
    Model modelItems[];
    public static ListView listView;
    public Spinner s1,s2;
    static StundenSetzen instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        instance = this;
        setContentView(R.layout.activity_stunden_setzen);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.Listview_1);
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


        listenladen();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();


        if (id == R.id.action_settings2) {

            uLKs();
            stundeSetzen(LK1);
            stundeSetzen(LK2);



        }
        return super.onOptionsItemSelected(item);
    }
    //Wenn mehr als nur die Q1 funktionieren soll muss das hier noch dazu
    public void stundeSetzen(String stunde){
        for(int i=0; i<list.size()-7; i++){
            //-7 ist notwendig um noch alles ohne nullpointer durchsuchen zu können
            if(list.get(i+4).equals(stunde)){
                Toast.makeText(this, ""+platzbestimmer(i), Toast.LENGTH_SHORT).show();
                MainActivity.alleStunden.get(platzbestimmer(i)).löschen();
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
        final ListView s = (ListView) findViewById(R.id.Listview_1);
        modelItems = new Model[gkliste.size()];
        for (int i = 0; i < gkliste.size(); i++) {

            modelItems[i] = new Model(gkliste.get(i), 0);

        }

            CustomAdapter adapter = new CustomAdapter(this, modelItems);
            listView.setAdapter(adapter);


            ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
            (this, android.R.layout.simple_spinner_item, lk1liste); //selected item will look like a spinner set from XML
            spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            s1.setAdapter(spinnerArrayAdapter);
            s2.setAdapter(spinnerArrayAdapter);
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
                    lk1liste.add(stundenliste.get(i));
                }
            }


        }
        Collections.sort(gkliste);
        Collections.sort(lk1liste);
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