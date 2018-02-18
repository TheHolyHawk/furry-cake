package com.example.paulfranken.myapplication;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class StundenSetzen extends AppCompatActivity {

    public DatenDownload downloader;
    public static ArrayList<String> datensatz = new ArrayList<String>();
    public Button bdy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stunden_setzen);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        downloader = new DatenDownload();

        bdy=(Button)findViewById(R.id.buddy);
        bdy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datensatz = downloader.getDaten();
                Toast.makeText(getApplicationContext(), datensatz.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
