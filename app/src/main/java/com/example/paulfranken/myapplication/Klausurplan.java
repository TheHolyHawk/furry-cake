package com.example.paulfranken.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.URLUtil;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Klausurplan extends AppCompatActivity {
public WebView w;
public static ListView o;
    public Calendar datumheute;
    public ArrayList<String>listadapter=new ArrayList<>();
    public Menu mymenu;
    public boolean heute=false,morgen=false,montag=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_klausurplan);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        w=(WebView)findViewById(R.id.webview);
        w.loadUrl("http://www.ohg-bensberg.de/WSK_extdata/171018EF.htm");


        datumheute= Calendar.getInstance();



        final ListView s = (ListView) findViewById(R.id.Listview);
        o = (ListView) findViewById(R.id.Listview);

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(Klausurplan.this,android.R.layout.simple_list_item_1,listadapter);
        s.setAdapter(adapter);
        s.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");



                String selectedSweet = s.getItemAtPosition(position).toString();





                String murl=String.valueOf(selectedSweet.charAt(6))+selectedSweet.charAt(7)+selectedSweet.charAt(3)+selectedSweet.charAt(4)+selectedSweet.charAt(0)+selectedSweet.charAt(1);


                String url="http://www.ohg-bensberg.de/WSK_extdata/"+murl+MainActivity.klasse+".htm";

                w.loadUrl(url);
                w.setVisibility(View.VISIBLE);








            }
        });


        w.setPadding(0, 0, 0, 0);
        w.setInitialScale(getScale());



        w.setVisibility(View.INVISIBLE);

    }
    private int getScale(){
        Display display = ((WindowManager) getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
        int width = display.getWidth();
        Double val = new Double(width)/new Double(width);
        val = val * 100d;
        return val.intValue();
    }






    public void check(){

        datumheute= Calendar.getInstance();
        SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");
        SimpleDateFormat format2 = new SimpleDateFormat("dd.MM.yy");
        String formatted = format1.format(datumheute.getTime());

        String formatted2 = format2.format(datumheute.getTime());





        final ArrayList<String>  arraySpinner=new ArrayList<>();
        arraySpinner.add("Wähle ein Datum");








        new Thread(new Runnable() {
            @Override
            public void run() {


                for (int i = 0; i < 5; i++) {


                    SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");


                    String formatted = format1.format(datumheute.getTime());


                    try {
                        String code = "http://ohg-bensberg.de/WSK_extdata/"+formatted+MainActivity.klasse+".htm";


                        org.jsoup.nodes.Document doc = Jsoup.connect(code).ignoreHttpErrors(true).get();

                        if (!doc.title().equals("Object not found!")) {
                            SimpleDateFormat format12 = new SimpleDateFormat("dd.MM.yy");
                            String test=format12.format(datumheute.getTime());

                            arraySpinner.add(test);

                        }


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    datumheute.add(Calendar.DATE, 1);
                }
            }

        }).start();

        ListView s = (ListView) findViewById(R.id.Listview);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text_layout, arraySpinner);
        s.setAdapter(adapter);


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.klausur, menu);
        mymenu=menu;



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement




        if(id==R.id.action_refresh){
            LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            ImageView iv = (ImageView)inflater.inflate(R.layout.ic_refresh, null);
            Animation rotation = AnimationUtils.loadAnimation(this, R.anim.rotate_refrsh);
            rotation.setRepeatCount(Animation.INFINITE);
            iv.startAnimation(rotation);
            item.setActionView(iv);

            new Klausurplan.MyTask(this).execute();
            new MyTask2(this).execute();




        }

        return super.onOptionsItemSelected(item);
    }

    class MyTask extends AsyncTask<Void,Void,Void> {

        private Context mCon;

        public MyTask(Context con)
        {
            mCon = con;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            for (int i = 0; i < 5; i++) {


                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");


                String formatted = format1.format(datumheute.getTime());


                try {
                    String code = "http://ohg-bensberg.de/WSK_extdata/"+formatted+MainActivity.klasse+".htm";


                    org.jsoup.nodes.Document doc = Jsoup.connect(code).ignoreHttpErrors(true).get();

                    if (!doc.title().equals("Object not found!")) {
                        SimpleDateFormat format12 = new SimpleDateFormat("dd.MM.yy");
                        String test=format12.format(datumheute.getTime());

                        listadapter.add(test);

                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }

                datumheute.add(Calendar.DATE, 1);
            }



            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {

resetUpdating();



            ListView s = (ListView) findViewById(R.id.Listview);

            ArrayAdapter<String> adapter=new ArrayAdapter<String>(Klausurplan.this,android.R.layout.simple_list_item_1,listadapter);
            s.setAdapter(adapter);









        }



    }


    class MyTask2 extends AsyncTask<Void,Void,Void>{

        private Context mCon;

        public MyTask2(Context con)
        {
            mCon = con;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            Calendar c=Calendar.getInstance();

            try{



                SimpleDateFormat format1 = new SimpleDateFormat("yyMMdd");


                String formatted = format1.format(c.getTime());
                String code = "http://ohg-bensberg.de/WSK_extdata/"+formatted+MainActivity.klasse+".htm";

                org.jsoup.nodes.Document doc = Jsoup.connect(code).ignoreHttpErrors(true).get();

                if (!doc.title().equals("Object not found!")) {
                    heute=true;


                }else{
                    heute=false;



                }


                c.add(Calendar.DATE,1);
                formatted = format1.format(c.getTime());
                 code = "http://ohg-bensberg.de/WSK_extdata/"+formatted+MainActivity.klasse+".htm";

                doc = null;
                try {
                    doc = Jsoup.connect(code).ignoreHttpErrors(true).get();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                if (!doc.title().equals("Object not found!")) {
                    morgen=true;


                }else{
                    morgen=false;




                }

                Calendar calendar=Calendar.getInstance();

                String weekDay;
                SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);




                weekDay = dayFormat.format(calendar.getTime());

                if(weekDay.equals("Friday")){
                    c.add(Calendar.DATE,1);
                    c.add(Calendar.DATE,1);
                    c.add(Calendar.DATE,1);
                    formatted = format1.format(c.getTime());
                     code = "http://ohg-bensberg.de/WSK_extdata/"+formatted+MainActivity.klasse+".htm";

                    doc = null;
                    try {
                        doc = Jsoup.connect(code).ignoreHttpErrors(true).get();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    if (!doc.title().equals("Object not found!")) {
                        montag=true;


                    }else{
                        montag=false;




                    }
                }
            }catch (IOException e){
                e.printStackTrace();
            }


            return null;
        }



        @Override
        protected void onPostExecute(Void aVoid) {

            Context context;
            CharSequence text ;
            int duration ;

            Toast toast;




            if (heute == true && morgen == false) {
                context = getApplicationContext();
                text = "Der Klausurplan für Heute ist verfügbar!";
                duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (morgen == true && heute == false) {
                context = getApplicationContext();
                text = "Der Klausurplan ist für Morgen verfügbar!";
                duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (heute == true && morgen == true) {
                context = getApplicationContext();
                text = "Der Klausurplan für Heute und Morgen ist verfügbar!";
                duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (heute == false && morgen == false) {
                context = getApplicationContext();

                text = "Der Klausurplan für Heute und Morgen ist noch nicht verfügbar oder du hast kein Internetzugriff!";
                duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            if (montag == true) {
                context = getApplicationContext();

                text = "Der Klausurplan für Montag ist verfügbar";
                duration = Toast.LENGTH_SHORT;

                toast = Toast.makeText(context, text, duration);
                toast.show();
            }
            heute = false;
            morgen = false;
            montag=false;



        }


    }

    public void resetUpdating() {
// Get our refresh item from the menu
        MenuItem m = mymenu.findItem(R.id.action_refresh);
        if(m.getActionView()!=null)
        {
// Remove the animation.
            m.getActionView().clearAnimation();
            m.setActionView(null);
        }
    }






    }

