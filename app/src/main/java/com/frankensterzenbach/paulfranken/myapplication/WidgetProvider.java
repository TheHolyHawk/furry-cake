package com.frankensterzenbach.paulfranken.myapplication;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.RemoteViews;

import java.util.ArrayList;

public class WidgetProvider extends AppWidgetProvider {
    public static String EXTRA_WORD = "WORD";
    public static String UPDATE_LIST = "UPDATE_LIST";
    public static String For = "For";
    public static String Aktualisieren = "aktualisieren";

    public static ArrayList<String>montag=new ArrayList<String>();

    public static ArrayList<String>test=new ArrayList<String>();
    public static String fach,stunde;
    public static String test3 = " ";
    public static  String kurs, kursid,text,raum2;



    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {

        Log.e("app widget id - ", appWidgetIds.length+"");
        for (int i = 0; i < appWidgetIds.length; i++) {
            Intent svcIntent = new Intent(context, WidgetService.class);
            //svcIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetIds[i]);
            //svcIntent.setData(Uri.parse(svcIntent .toUri(Intent.URI_INTENT_SCHEME)));

            RemoteViews widget = new RemoteViews(context.getPackageName(), R.layout.widget);
            widget.setRemoteAdapter(appWidgetIds[i], R.id.words, svcIntent);

            Intent clickIntent = new Intent(context, MainActivity.class);
            PendingIntent clickPI = PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
            widget.setPendingIntentTemplate(R.id.words, clickPI);


            clickIntent = new Intent(context, WidgetProvider.class);
            clickIntent.setAction(UPDATE_LIST);
            PendingIntent pendingIntentRefresh = PendingIntent.getBroadcast(context,0, clickIntent, 0);
            widget.setOnClickPendingIntent(R.id.button, pendingIntentRefresh);

            clickIntent = new Intent(context, WidgetProvider.class);
            clickIntent.setAction(For);
             pendingIntentRefresh = PendingIntent.getBroadcast(context,0, clickIntent, 0);
            widget.setOnClickPendingIntent(R.id.button2, pendingIntentRefresh);



            RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget);
            if(AppWidgetViewsFactory.tag==0){
                row.setTextViewText(R.id.widget_tag,"Montag");
            }else  if(AppWidgetViewsFactory.tag==1){
                row.setTextViewText(R.id.widget_tag,"Dienstag");
            } if(AppWidgetViewsFactory.tag==2){
                row.setTextViewText(R.id.widget_tag,"Mittwoch");
            } if(AppWidgetViewsFactory.tag==3){
                row.setTextViewText(R.id.widget_tag,"Donnerstag");
            } if(AppWidgetViewsFactory.tag==4){
                row.setTextViewText(R.id.widget_tag,"Freitag");
            }
            appWidgetManager.updateAppWidget(appWidgetIds[i], row);



            appWidgetManager.updateAppWidget(appWidgetIds[i], widget);
            updateWidget(context);
        }

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);

        if(intent.getAction().equalsIgnoreCase(UPDATE_LIST)){
            if(AppWidgetViewsFactory.tag==4){
                AppWidgetViewsFactory.tag=3;
            }else  if(AppWidgetViewsFactory.tag==3){
                AppWidgetViewsFactory.tag=2;
            }else  if(AppWidgetViewsFactory.tag==2){
                AppWidgetViewsFactory.tag=1;
            }else  if(AppWidgetViewsFactory.tag==1){
                AppWidgetViewsFactory.tag=0;
            }else  if(AppWidgetViewsFactory.tag==0){
                AppWidgetViewsFactory.tag=4;
            }
            updateWidget(context);
        }
       else  if(intent.getAction().equalsIgnoreCase(For)){
            if(AppWidgetViewsFactory.tag==4){
                AppWidgetViewsFactory.tag=0;
            }else  if(AppWidgetViewsFactory.tag==3){
                AppWidgetViewsFactory.tag=4;
            }else  if(AppWidgetViewsFactory.tag==2){
                AppWidgetViewsFactory.tag=3;
            }else  if(AppWidgetViewsFactory.tag==1){
                AppWidgetViewsFactory.tag=2;
            }else  if(AppWidgetViewsFactory.tag==0){
                AppWidgetViewsFactory.tag=1;
            }
            updateWidget(context);
        }

    }

    public static void updateWidget(Context context) {

        RemoteViews row = new RemoteViews(context.getPackageName(), R.layout.widget);
        if(AppWidgetViewsFactory.tag==0){
            row.setTextViewText(R.id.widget_tag,"Montag");
        }else  if(AppWidgetViewsFactory.tag==1){
            row.setTextViewText(R.id.widget_tag,"Dienstag");
        } if(AppWidgetViewsFactory.tag==2){
            row.setTextViewText(R.id.widget_tag,"Mittwoch");
        } if(AppWidgetViewsFactory.tag==3){
            row.setTextViewText(R.id.widget_tag,"Donnerstag");
        } if(AppWidgetViewsFactory.tag==4){
            row.setTextViewText(R.id.widget_tag,"Freitag");
        }

        Laden(context);



        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);

        int appWidgetIds[] = appWidgetManager.getAppWidgetIds(new ComponentName(context, WidgetProvider.class));
        appWidgetManager.updateAppWidget(appWidgetIds,row);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.words);
    }



    public static void Laden(Context context){


        SharedPreferences fach=context.getSharedPreferences("fach",0);


        String fachString= fach.getString("fach",null);

        if(fachString!=null){
            String[]itemwors=fachString.split(",");
            ArrayList<String>worte=new ArrayList<>();
            for(int i=0;i<itemwors.length;i++) {

                worte.add(itemwors[i]);
                AppWidgetViewsFactory.fach = worte;


            }

        }

        //raum infromationen widget


        SharedPreferences raum=context.getSharedPreferences("raum",0);


        String raumString= raum.getString("raum",null);

        if(raumString!=null){
            String[]itemwors=raumString.split(",");
            ArrayList<String>worte=new ArrayList<>();
            for(int i=0;i<itemwors.length;i++) {

                worte.add(itemwors[i]);
                AppWidgetViewsFactory.raumeString = worte;


            }

        }




    }



    }


