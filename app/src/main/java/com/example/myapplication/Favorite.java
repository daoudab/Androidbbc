package com.example.myapplication;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;
import java.util.ArrayList;

public class Favorite extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{
    int selectedItemPosition=-1;
    DBHelper db;
    ArrayList<fav> favList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        db = new DBHelper(this);
        if (favList!=null) {
            favList.clear();
        }
        favList = db.getFavNews();
        Button delButton = findViewById(R.id.delFav);
        delButton.setVisibility(View.GONE);

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


        class MyListAdapter2 extends BaseAdapter {
            public int getCount() {
                return favList.size();}
            public Object getItem(int position) {
                return position;}
            public long getItemId(int position) {
                return position;}
            public View getView(int position, View old, ViewGroup parent) {
                View newView = old;
                LayoutInflater inflater = getLayoutInflater();
                if (newView == null) {
                    newView = inflater.inflate(R.layout.row_layout, parent, false);
                }
                TextView tView = newView.findViewById(R.id.textGoesHere);
                tView.setText(favList.get(position).title);
                return newView;
            }
        }

        ListView listView2 = findViewById(R.id.listView2);
        MyListAdapter2 myAdapter2 = new MyListAdapter2();
        listView2.setAdapter(myAdapter2);

        listView2.setOnItemClickListener( (parent, view, position, id) -> {
            delButton.setVisibility(View.VISIBLE);
            view.setBackgroundColor(Color.YELLOW);
            fav selectedNews = favList.get(position);

            // Change the background color of the previously selected item (if any) back to white
            if (selectedItemPosition != -1 && selectedItemPosition != position) {
                View previousSelectedView = parent.getChildAt(selectedItemPosition - parent.getFirstVisiblePosition());
                previousSelectedView.setBackgroundColor(Color.WHITE);
            }
            // Update the selected item position
            selectedItemPosition = position;

            //fragment pops out
            fragment(selectedNews);
            FrameLayout fragmentLocation2 = findViewById(R.id.fragmentLocation2);

            //link to web browser
            if (selectedNews.link!= null || !selectedNews.link.equals("") ) {
                fragmentLocation2.setOnClickListener(click -> {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(selectedNews.link));
                    startActivity(intent);
                });
            }

            //DELETE THE NEWS FROM FAVOURITE LIST
            if (findViewById(R.id.delFav) !=null) {
                delButton.setOnClickListener(click ->
                { String message = getResources().getString(R.string.removed);
                    db.deleteNews(selectedNews);
                    Toast.makeText(Favorite.this, message, Toast.LENGTH_LONG).show();
                    if (favList!=null) {
                        favList.clear();
                    }
                    favList = db.getFavNews();
                    myAdapter2.notifyDataSetChanged();
                    fav blankNews = new fav(-1,null,null,null,null);
                    fragment(blankNews);
                    delButton.setVisibility(View.GONE);
                });
            }

        });
    }

    private void fragment(fav selectedNews) {
        Bundle dataToPass = new Bundle();

        dataToPass.putString("title",selectedNews.title);
        dataToPass.putString("description", selectedNews.description);
        dataToPass.putString("link", selectedNews.link);
        dataToPass.putString("date", selectedNews.date);

        newsFragment fragment = new newsFragment();
        fragment.setArguments(dataToPass);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fragmentLocation2, fragment).commit();

    }

    //menu and nav
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
            String message = getResources().getString(R.string.note5);
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
