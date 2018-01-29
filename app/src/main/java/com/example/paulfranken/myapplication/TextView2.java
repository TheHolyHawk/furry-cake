package com.example.paulfranken.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;
import android.widget.Toast;

import static android.R.attr.id;
import static com.example.paulfranken.myapplication.R.id.text102;
import static com.example.paulfranken.myapplication.R.id.text108;
import static com.example.paulfranken.myapplication.R.id.text16;
import static com.example.paulfranken.myapplication.R.id.text26;
import static com.example.paulfranken.myapplication.R.id.text31;
import static com.example.paulfranken.myapplication.R.id.text37;
import static com.example.paulfranken.myapplication.R.id.text4;
import static com.example.paulfranken.myapplication.R.id.text41;
import static com.example.paulfranken.myapplication.R.id.text46;
import static com.example.paulfranken.myapplication.R.id.text56;
import static com.example.paulfranken.myapplication.R.id.text65;

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

    public void löschen(){
        if(this.getId()==text65||this.getId()==text4||this.getId()==text16||this.getId()==text102||this.getId()==text26||this.getId()==text31||this.getId()==text37||this.getId()==text41||this.getId()==text46||this.getId()==text108||this.getId()==text56) {

        }else {
            this.setText("");
            farbe = "";
            farbe2 = "";

            datum = "";
            kurs = "";
            nummer = "";
            fach="";
            platz = "";
            raum="";
            this.setBackgroundDrawable(getResources().getDrawable(R.drawable.back2));


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


        gd.setColor(Color.DKGRAY);
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