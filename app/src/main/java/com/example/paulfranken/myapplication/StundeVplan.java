package com.example.paulfranken.myapplication;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class StundeVplan {

    public String fach;
    public Calendar datum;
    public String kurs;
    public String kursid;
    public String text;
    public String tag;
    public String raum;
    public String stunde;


    public StundeVplan(String pfachkurz,Calendar pdatum, String pkurs, String pkursid,String ptext,String praum,String pstunde) {

        fach = pfachkurz;

        text=ptext;

        kurs = pkurs;
        kursid = pkursid;
        raum=praum;
        stunde=pstunde;

        datum=pdatum;
        String weekDay;
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.US);




        weekDay = dayFormat.format(datum.getTime());
        tag=weekDay;
        if (tag.equals("Monday")) {
            tag="Montag";
        } else if (tag.equals("Tuesday")) {
            tag="Dienstag";
        }else if (tag.equals("Wednesday")) {
            tag="Mittwoch";
        }else if (tag.equals("Thursday")) {
            tag="Donnerstag";
        }else if (tag.equals("Friday")) {
            tag="Freitag";
        }

        if (fach.equals("PL")) {
            fach = "Philosophie";
        } else if (fach.equals("E ")) {
            fach = "Englisch";
        } else if (fach.equals("PH")) {
            fach = "Physik";
        }else if (fach.equals("GE")) {
            fach = "Geschichte";
        }
        else if (fach.equals("SW")) {
            fach = "Politik";
        }else if (fach.equals("EK")) {
            fach = "Erdkunde";
        }else if (fach.equals("MU")) {
            fach = "Musik";
        }else if (fach.equals("BI")) {
            fach = "Biologie";
        }else if (fach.equals("L ")) {
            fach = "Latein";
        }else if (fach.equals("D ")) {
            fach = "Deutsch";
        }else if (fach.equals("S ")) {
            fach = "Spanisch";
        }else if (fach.equals("SP")) {
            fach = "Sport";
        }else if (fach.equals("M ")) {
            fach = "Mathe";
        }else if (fach.equals("CH")) {
            fach = "Chemie";
        }else if (fach.equals("GE")) {
            fach = "Geschichte";
         }else if (fach.equals("KU")) {
            fach = "Kunst";
        }else if (fach.equals("PH")) {
            fach = "Physik";
        }
        else if (fach.equals("L ")) {
            fach = "Latein";
        }else if (fach.equals("LI")) {
            fach = "Literatur";
        }
        else if (fach.equals("KR")) {
            fach = "K.Religion";
        }
        else if (fach.equals("S8")) {
            fach = "S8";
        }


        if (pkurs.equals("L")) {
            kurs = "LK";
        }
        if (pkurs.equals("G")) {
            kurs = "GK";
        }

    }

}
