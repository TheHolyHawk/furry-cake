package com.example.paulfranken.myapplication;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import java.util.ArrayList;

public class AppWidgetViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    private Context context = null;
    //private int appWidgetId;
    //
    public static ArrayList<String> arrayList = new ArrayList<String>();
    public static int tag=4;
    public  ArrayList<String> stunden = new ArrayList<String>();

    public  ArrayList<String> zeiten = new ArrayList<String>();

    public static ArrayList<String> raume = new ArrayList<String>();

    public static ArrayList<String>test=new ArrayList<>();



    public static  ArrayList<String>fach=new ArrayList<>();
    public static ArrayList<String>raumeString=new ArrayList<>();

    public AppWidgetViewsFactory(Context ctxt, Intent intent) {
        this.context = ctxt;
        stunden.add("1.Stunde");
        stunden.add("2.Stunde");
        stunden.add("3.Stunde");
        stunden.add("4.Stunde");
        stunden.add("5.Stunde");
        stunden.add("6.Stunde");
        stunden.add("7.Stunde");
        stunden.add("8.Stunde");
        stunden.add("9.Stunde");
        stunden.add("10.Stunde");
        stunden.add("11.Stunde");

        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");
        raume.add("");

        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");
        arrayList.add("");


        zeiten.add("7:50-8:35");
        zeiten.add("8:40-9:25");
        zeiten.add("9:45-10:30");
        zeiten.add("10:35-11:20");
        zeiten.add("11:40-12:25");
        zeiten.add("12:30-13:15");
        zeiten.add("13:25-14:10");
        zeiten.add("14:15-15:00");
        zeiten.add("15:05-15:50");
        zeiten.add("15:55-16:40");
        zeiten.add("16:45-17:30");









		/*appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
				AppWidgetManager.INVALID_APPWIDGET_ID);
		Log.e(getClass().getSimpleName(), appWidgetId + "");*/
    }

    @Override
    public void onCreate() {






    }

    @Override
    public void onDestroy() {
        // no-op
    }

    @Override
    public int getCount() {
        return (stunden.size());
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.row);

        row.setTextViewText(android.R.id.text1, arrayList.get(position));




        row.setTextViewText(R.id.raum, raume.get(position));
        row.setTextViewText(R.id.stunde, stunden.get(position));
        row.setTextViewText(R.id.zeiten, zeiten.get(position));

        Intent i = new Intent();
        Bundle extras = new Bundle();

        extras.putString(WidgetProvider.EXTRA_WORD, arrayList.get(position));
        i.putExtras(extras);
        row.setOnClickFillInIntent(android.R.id.text1, i);


        return row;
    }




    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public void onDataSetChanged() {

        if(test.size()!=0){
            arrayList=test;
        }else {
            if (tag == 0) {

//stunden
                arrayList.clear();
                arrayList.add(fach.get(0));
                arrayList.add(fach.get(1));
                arrayList.add(fach.get(2));
                arrayList.add(fach.get(3));
                arrayList.add(fach.get(4));
                arrayList.add(fach.get(5));
                arrayList.add(fach.get(6));
                arrayList.add(fach.get(7));
                arrayList.add(fach.get(8));
                arrayList.add(fach.get(9));
                arrayList.add(fach.get(10));
//raume
                raume.clear();
                raume.add(raumeString.get(0));
                raume.add(raumeString.get(1));
                raume.add(raumeString.get(2));
                raume.add(raumeString.get(3));
                raume.add(raumeString.get(4));
                raume.add(raumeString.get(5));
                raume.add(raumeString.get(6));
                raume.add(raumeString.get(7));
                raume.add(raumeString.get(8));
                raume.add(raumeString.get(9));
                raume.add(raumeString.get(10));


            } else if (tag == 1) {
//Stunden
                arrayList.clear();
                arrayList.add(fach.get(11));
                arrayList.add(fach.get(12));
                arrayList.add(fach.get(13));
                arrayList.add(fach.get(14));
                arrayList.add(fach.get(15));
                arrayList.add(fach.get(16));
                arrayList.add(fach.get(17));
                arrayList.add(fach.get(18));
                arrayList.add(fach.get(19));
                arrayList.add(fach.get(20));
                arrayList.add(fach.get(21));

                //raume
                raume.clear();
                raume.add(raumeString.get(11));
                raume.add(raumeString.get(12));
                raume.add(raumeString.get(13));
                raume.add(raumeString.get(14));
                raume.add(raumeString.get(15));
                raume.add(raumeString.get(16));
                raume.add(raumeString.get(17));
                raume.add(raumeString.get(18));
                raume.add(raumeString.get(19));
                raume.add(raumeString.get(20));
                raume.add(raumeString.get(21));

            }
            if (tag == 2) {

                //srunden
                arrayList.clear();
                arrayList.add(fach.get(22));
                arrayList.add(fach.get(23));
                arrayList.add(fach.get(24));
                arrayList.add(fach.get(25));
                arrayList.add(fach.get(26));
                arrayList.add(fach.get(27));
                arrayList.add(fach.get(28));
                arrayList.add(fach.get(29));
                arrayList.add(fach.get(30));
                arrayList.add(fach.get(31));
                arrayList.add(fach.get(32));

                //raume
                raume.clear();
                raume.add(raumeString.get(22));
                raume.add(raumeString.get(23));
                raume.add(raumeString.get(24));
                raume.add(raumeString.get(25));
                raume.add(raumeString.get(26));
                raume.add(raumeString.get(27));
                raume.add(raumeString.get(28));
                raume.add(raumeString.get(29));
                raume.add(raumeString.get(30));
                raume.add(raumeString.get(31));
                raume.add(raumeString.get(32));


            } else if (tag == 3) {
                //stunden
                arrayList.clear();
                arrayList.add(fach.get(33));
                arrayList.add(fach.get(34));
                arrayList.add(fach.get(35));
                arrayList.add(fach.get(36));
                arrayList.add(fach.get(37));
                arrayList.add(fach.get(38));
                arrayList.add(fach.get(39));
                arrayList.add(fach.get(40));
                arrayList.add(fach.get(41));
                arrayList.add(fach.get(42));
                arrayList.add(fach.get(43));

                raume.clear();
                raume.add(raumeString.get(33));
                raume.add(raumeString.get(34));
                raume.add(raumeString.get(35));
                raume.add(raumeString.get(36));
                raume.add(raumeString.get(37));
                raume.add(raumeString.get(38));
                raume.add(raumeString.get(39));
                raume.add(raumeString.get(40));
                raume.add(raumeString.get(41));
                raume.add(raumeString.get(42));
                raume.add(raumeString.get(43));
            } else if (tag == 4) {

                //stunden
                arrayList.clear();
                arrayList.add(fach.get(44));
                arrayList.add(fach.get(45));
                arrayList.add(fach.get(46));
                arrayList.add(fach.get(47));
                arrayList.add(fach.get(48));
                arrayList.add(fach.get(49));
                arrayList.add(fach.get(50));
                arrayList.add(fach.get(51));
                arrayList.add(fach.get(52));
                arrayList.add(fach.get(53));
                arrayList.add(fach.get(54));
                //raume
                raume.clear();
                raume.add(raumeString.get(44));
                raume.add(raumeString.get(45));
                raume.add(raumeString.get(46));
                raume.add(raumeString.get(47));
                raume.add(raumeString.get(48));
                raume.add(raumeString.get(49));
                raume.add(raumeString.get(50));
                raume.add(raumeString.get(51));
                raume.add(raumeString.get(52));
                raume.add(raumeString.get(53));
                raume.add(raumeString.get(54));

            }


        }


    }

}