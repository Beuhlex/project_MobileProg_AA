package com.example.project_mobileprog_aa.presentation.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project_mobileprog_aa.R;
import com.example.project_mobileprog_aa.Singletons;
import com.example.project_mobileprog_aa.presentation.controller.mainController;
import com.example.project_mobileprog_aa.presentation.model.ZeldaGames;
import com.google.gson.GsonBuilder;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListAdapter.OnGameListener {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private mainController mc;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mc = new mainController(this, Singletons.getGson(),Singletons.getSharedPreferences(getApplicationContext()));
        mc.onStart();

        setUpToolbar();
    }

    private void setUpToolbar(){
        //Create a toolbar so that we can customize the title and put the name of the game on which the user clicked
        toolbar = findViewById(R.id.toolbar_actionbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }


    public void loadTheme(int savedTheme){
        if(savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme);
        }else{
            setTheme(R.style.lightTheme);
        }
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRestart() {
        super.onRestart();

        mc.onStart();
        setUpToolbar();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates action menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        if(mc.savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.getItem(0).setChecked(true);
        }else{
            menu.getItem(0).setChecked(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //detects what item is selected
        mc.spinnerItemSelected(item);
        return super.onOptionsItemSelected(item);
    }



    public void showList(List<ZeldaGames> lozGamesList){
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mAdapter = new ListAdapter(lozGamesList, this);
        recyclerView.setAdapter(mAdapter);
    }

    public void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGameClick(int position) {
        mc.startItemActivity(position);
    }

}
