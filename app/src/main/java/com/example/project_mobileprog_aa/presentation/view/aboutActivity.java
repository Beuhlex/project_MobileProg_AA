package com.example.project_mobileprog_aa.presentation.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;

import com.example.project_mobileprog_aa.Constants;
import com.example.project_mobileprog_aa.R;
import com.example.project_mobileprog_aa.presentation.controller.aboutController;
import com.google.gson.GsonBuilder;

public class aboutActivity extends AppCompatActivity {

    private Toolbar actionToolbar;
    private aboutController ac;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ac = new aboutController(this, getSharedPreferences("app_AA", Context.MODE_PRIVATE));
        ac.onStart();



        setUpToolbar();
    }

    public void loadTheme(int savedTheme){
        if(savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.darkTheme);
        }else{
            setTheme(R.style.lightTheme);
        }
        setContentView(R.layout.about_activity);
    }

    private void setUpToolbar(){
        actionToolbar = findViewById(R.id.toolbar_actionbar);
        actionToolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(actionToolbar);
        getSupportActionBar().setTitle("About");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
