package com.frankensterzenbach.paulfranken.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;

import static com.frankensterzenbach.paulfranken.myapplication.R.id.text102;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text108;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text16;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text26;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text31;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text37;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text4;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text41;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text46;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text56;
import static com.frankensterzenbach.paulfranken.myapplication.R.id.text65;

public class TextView2 extends android.support.v7.widget.AppCompatTextView {
public String farbe="";
    public String datum="";
    public String kurs="";
    public String nummer="";
    public String platz="";
    public String fach="";
    public String raum="";
    public String tag="";
    public String stunde="";

    public String farbe2="";
    public String lehrer="";

    public void löschen(){
        if(this.getId()==text65||this.getId()==text4||this.getId()==text16||this.getId()==text102||this.getId()==text26||this.getId()==text31||this.getId()==text37||this.getId()==text41||this.getId()==text46||this.getId()==text108||this.getId()==text56) {

        }else {
            this.setText("");
            farbe = "";
            farbe2 = "";
             lehrer="";
            datum = "";
            kurs = "";
            nummer = "";
            fach="";
            platz = "";
            raum="";
            this.setBackgroundDrawable(getResources().getDrawable(R.drawable.back2));


        }

    }
    public void umwandeln(){

        if (fach.equals("D ")||fach.equals("D")) {
            fach = "Deutsch";
        }else if (fach.equals("E ")||fach.equals("E5")) {
            fach = "Englisch";
        }else if (fach.equals("F ")||fach.equals("F6")) {
            fach = "Franz";
        }else if (fach.equals("L ")||fach.equals("L6")) {
            fach = "Latein";
        }else if (fach.equals("S ")) {
            fach = "Spanisch";
        }else if (fach.equals("S8")) {
            fach = "Spanisch8";

        }else if (fach.equals("M ")||fach.equals("M")) {
            fach = "Mathe";
        }else if (fach.equals("PH")) {
            fach = "Physik";
        }else if (fach.equals("CH")) {
            fach = "Chemie";
        }else if (fach.equals("BI")) {
            fach = "Biologie";
        }else if (fach.equals("IF")) {
            fach = "Info";
        }else if (fach.equals("TC")) {
            fach = "Technik";

        }
        else if (fach.equals("PH EXP")) {
            fach = "PH.Exp.";
        }else if (fach.equals("BI EXP")) {
            fach = "Bi.Exp.";
        }else if (fach.equals("CH EXP")) {
            fach = "CH.Exp.";
        }

        else if (fach.equals("AG-Blaeser")) {
            fach = "Bläser";
        }else if (fach.equals("AG-LEGO")) {
            fach = "LEGO";
        }


        else if (fach.equals("GE")) {
            fach = "Geschi";
        }else if (fach.equals("PK")) {
            fach = "Politik";
        }else if (fach.equals("SW")) {
            fach = "Sowi";
        }else if (fach.equals("EK")) {
            fach = "Erdkunde";
        }else if (fach.equals("PL")) {
            fach = "Philo";
        }else if (fach.equals("ER")) {
            fach = "Reli(E)";
        }else if (fach.equals("PP")) {
            fach = "P.Philo";
        }else if (fach.equals("REL")) {
            fach = "Religion";
        }else if (fach.equals("KR")) {
            fach = "Reli(K)";

        }else if (fach.equals("KU")) {
            fach = "Kunst";
        }else if (fach.equals("MU")) {
            fach = "Musik";
        }else if (fach.equals("LI")) {
            fach = "Literatur";
        }else if (fach.equals("SP")) {
            fach = "Sport";
        }else if (fach.equals("SN")) {
            fach = "Schwimmen";
        }else if (fach.equals("EIS")) {
            fach = "Eislaufen";
        }else if (fach.equals("CO")) {
            fach = "Chor";
        }else if (fach.equals("FOE"+MainActivity.klasse.charAt(0))) {
            fach = "Förder";
        }else if (fach.equals("Diff"+MainActivity.klasse.charAt(0))) {
            fach = "Diff";
        }else if (fach.equals("OR")) {
            fach = "Orchester";
        }
        else if (fach.equals("BigB")) {
            fach = "Big Band";
        }

        if (kurs.equals("L")) {
            kurs = "LK";
        }
        if (kurs.equals("G")) {
            kurs = "GK";
        }
    }
    public void aktualisieren(){
        GradientDrawable gd = new GradientDrawable();

        int greenColorValue =Integer.parseInt(farbe);

        gd.setColor(greenColorValue); // Changes this drawbale to use a single color instead of a gradient

        gd.setStroke(1, 0xFF000000);


        this.setBackgroundDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.back2));
        this.setBackgroundDrawable(gd);

    }
    public void aktualisieren2(){
        GradientDrawable gd = new GradientDrawable();



        gd.setColor(Color.DKGRAY); // Changes this drawbale to use a single color instead of a gradient

        gd.setStroke(1, 0xFF000000);


        this.setBackgroundDrawable(ContextCompat.getDrawable(this.getContext(), R.drawable.back2));
        this.setBackgroundDrawable(gd);

    }

    public TextView2(Context context) {
        super(context);

    }

    public TextView2(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    public TextView2(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}