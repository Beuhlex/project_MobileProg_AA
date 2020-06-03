package com.example.project_mobileprog_aa.presentation.view;

import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.project_mobileprog_aa.R;
import com.example.project_mobileprog_aa.presentation.controller.itemController;
import com.google.gson.GsonBuilder;

public class itemDescription extends AppCompatActivity{

    private itemController ic;


    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        ic = new itemController(this,
                new GsonBuilder()
                        .setLenient()
                        .create(),
                getSharedPreferences("app_AA", Context.MODE_PRIVATE));

        ic.onStart();
    }

    public void loadTheme(int savedTheme){
        if(savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme);
        }else{
            setTheme(R.style.lightTheme);
        }
        setContentView(R.layout.item_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates action menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        if(ic.savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            menu.getItem(0).setChecked(true);
        }else{
            menu.getItem(0).setChecked(false);
        }

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //detects what item is selected
        ic.spinnerItemSelected(item);
        return super.onOptionsItemSelected(item);
    }
}
