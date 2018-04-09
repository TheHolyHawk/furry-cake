package com.frankensterzenbach.paulfranken.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Toast;

public class PlanWebView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_web_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        String easyPuzzle = intent.getExtras().getString("epuzzle");

        WebView webView=(WebView)findViewById(R.id.webview_plan);
        webView.loadUrl(easyPuzzle);
        webView.setVisibility(View.VISIBLE);

        Toast.makeText(this, "Leider kann der Vertretungsplan heute nicht abgeglichen werden. Du kannst diesen allerdings als Foto betrachten. Vielen Dank", Toast.LENGTH_LONG).show();


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.planwebview_menu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fertig) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
