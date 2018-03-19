package com.frankensterzenbach.paulfranken.myapplication;


import java.io.IOException;


import org.jsoup.Jsoup;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;
import org.jsoup.nodes.Element;



/**
 * Created by Paul Franken on 03.10.2017.
 */

public class Vertretungsplan {
  //  public LocalDate datumheute;

    public static String fach;
    public static String test3 = " ";
    public String kurs, kursid;

    public  void test2() {
      //  datumheute = LocalDate.now();

        for (int l = 0; l < 5; l++) {

         //   DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyMMdd");

           // String formattedString = datumheute.format(formatter);
           // System.out.println(formattedString);

            try {

                String code = "http://www.ohg-bensberg.de/WSK_extdata/vplan/171004/Ver_Kla_Q1.htm";
                //http://www.ohg-bensberg.de/WSK_extdata/vplan/171004/Ver_Kla_Q1.htm

                Document doc = Jsoup.connect(code).ignoreHttpErrors(true).get();

                if (!doc.title().equals("Object not found!")) {
                    Element tables = doc.select("center font table").get(1);

                    Elements rows = tables.select("tr");// zählt wie viele
                    // spalten
                    // es
                    // gibt

                    int spalten = rows.size() + 1;

                    for (int i = 2; i < spalten; i++) {

                        String test = ("center font tbody tr:nth-child(" + i + ") td:nth-child(4)");

                        Elements values = doc.select(test);
                        for (Element elem : values) {
                            fach = elem.text();

                        }

                        if (!fach.equals(test3)) {
                            if (fach.length() == 5) {
                                kurs = String.valueOf(fach.charAt(3));
                                kursid = String.valueOf(fach.charAt(4));
                            } else if (fach.length() == 4) {
                                kurs = String.valueOf(fach.charAt(2));
                                kursid = String.valueOf(fach.charAt(3));
                            }

                            fach = String.valueOf(fach.charAt(0) + String.valueOf(fach.charAt(1)));



                        }

                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }

         //   datumheute = datumheute.plusDays(1);
        }

    }






}
