package com.example.paulfranken.myapplication;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


import static com.example.paulfranken.myapplication.StundenSetzen.c;
import static com.example.paulfranken.myapplication.StundenSetzen.list;

public class StundenSetzen extends AppCompatActivity {
    public static Context c;
    public static ArrayList<String> list=new ArrayList<>();
    String url="http://facharbeit.square7.ch/Facharbeit:HochundRunter/get.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_stunden_setzen);
        c=getApplicationContext();


        final Downloader d=new Downloader(this,url);

        Button fab = (Button) findViewById(R.id.buddy);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Test().execute();

            }
        });
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

        Toast.makeText(c,list.get(0).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(1).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(2).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(3).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(4).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(5).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(6).toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(c,list.get(7).toString(),Toast.LENGTH_LONG).show();

    }
}