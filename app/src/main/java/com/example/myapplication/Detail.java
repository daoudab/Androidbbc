package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.material.navigation.NavigationView;

import com.google.android.material.snackbar.Snackbar;

public class Detail extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    DBHelper db;
    fav selectedNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //toolbar
        Toolbar myToolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, myToolbar, R.string.open, R.string.close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navView = findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        //get data from MainActivity
        Intent getIntent = getIntent();
        String title = getIntent.getStringExtra("title");
        String description = getIntent.getStringExtra("description");
        String link = getIntent.getStringExtra("link");
        String date = getIntent.getStringExtra("date");
        selectedNews = new fav(-1,title,description,link,date);

        TextView titleT = findViewById(R.id.title);
        titleT.setText(title);
        TextView desT = findViewById(R.id.description);
        desT.setText(description);
        TextView linkT = findViewById(R.id.link);
        linkT.setText(link);
        TextView dateT = findViewById(R.id.date);
        dateT.setText(date);

        //move to browser with the link
        linkT.setOnClickListener(click -> {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(link));
            startActivity(intent);
        });

        //add to favourite
        db = new DBHelper(this);
        Button addButton = findViewById(R.id.addFav);
        addButton.setOnClickListener(click ->
        { String message = getResources().getString(R.string.added);
            db.insertNews(selectedNews);
            View view = findViewById(R.id.content_frame);
            Snackbar snackbar = Snackbar.make(view,message,Snackbar.LENGTH_LONG);
            snackbar.setAction(R.string.back, v -> {
                Intent intent = new Intent(Detail.this, MainActivity.class);
                startActivity(intent);
            });
            snackbar.show();
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } if (id == R.id.fav) {
            Intent intent = new Intent(this, Favorite.class);
            startActivity(intent);
        } else if (id == R.id.exit) {
            finishAffinity();
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.homeIcon) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else if (id == R.id.helpIcon) {
            AlertDialog.Builder aDialog = new AlertDialog.Builder(this);
            String message = getResources().getString(R.string.note4);
            aDialog.setMessage(message)
                    .setTitle(R.string.howTo)
                    .setNeutralButton(R.string.ok, (click, arg) -> {
                    })
                    .create()
                    .show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_actions, menu);
        return true;
    }
}