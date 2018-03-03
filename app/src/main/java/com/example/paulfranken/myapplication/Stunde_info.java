package com.example.paulfranken.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Stunde_info extends AppCompatActivity {
private RelativeLayout layout;
    private TextView fach,raum,kurs,nummer, lehrer;
    private Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stunde__info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String raum2="";
        String string;
        string="    "+MainActivity.alleStunden.get(MainActivity.bearbeiteni).stunde+".Stunde "+MainActivity.alleStunden.get(MainActivity.bearbeiteni).tag;
        setTitle(string);

        layout=(RelativeLayout)findViewById(R.id.relativeLayout);
        layout.setBackgroundColor(Integer.parseInt(MainActivity.bearbeiten.farbe));
        fach=(TextView)findViewById(R.id.fach);
        fach.setText("Fach: "+MainActivity.bearbeiten.fach);
        raum=(TextView)findViewById(R.id.raum);
        raum.setText("Raum: "+MainActivity.bearbeiten.raum);
        kurs=(TextView)findViewById(R.id.Kurs);
        kurs.setText("Kurs: "+MainActivity.bearbeiten.kurs);
        nummer=(TextView)findViewById(R.id.nummer);
        nummer.setText("Nummer: "+MainActivity.bearbeiten.nummer);
        lehrer= (TextView)findViewById(R.id.lehrer);
        lehrer.setText("Lehrer: "+MainActivity.bearbeiten.lehrer);


        btn=(Button) findViewById(R.id.btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Stunde_info.this, Stunde_bearbeiten.class);
                Stunde_bearbeiten.bearbeiten=true;


                startActivity(intent);
                finish2();
            }
        });





    }
public void finish2(){
    this.finish();
}


}
