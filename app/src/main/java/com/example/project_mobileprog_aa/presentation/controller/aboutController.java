package com.example.project_mobileprog_aa.presentation.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import com.example.project_mobileprog_aa.Constants;
import com.example.project_mobileprog_aa.R;
import com.example.project_mobileprog_aa.presentation.view.aboutActivity;

public class aboutController {

    private SharedPreferences sharedPreferences;
    private aboutActivity aa;

    public aboutController(aboutActivity aa, SharedPreferences sharedPreferences){
        this.sharedPreferences = sharedPreferences;
        this.aa = aa;
    }

    public void onStart(){
        int savedTheme = sharedPreferences.getInt(Constants.KEY_APPTHEME, 1);
        aa.loadTheme(savedTheme);

        setAboutText();
    }

    public void setAboutText(){
        TextView generalDescView = aa.findViewById(R.id.aboutTextView);
        generalDescView.setText(aa.getString(R.string.about));
    }
}
