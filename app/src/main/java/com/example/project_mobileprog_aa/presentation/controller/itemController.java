package com.example.project_mobileprog_aa.presentation.controller;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.example.project_mobileprog_aa.Constants;
import com.example.project_mobileprog_aa.R;
import com.example.project_mobileprog_aa.Singletons;
import com.example.project_mobileprog_aa.presentation.model.ZeldaGames;
import com.example.project_mobileprog_aa.presentation.view.itemDescription;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.List;

public class itemController {
    private Toolbar actionToolbar;
    private SharedPreferences sharedPreferences;
    public int savedTheme;
    private Gson gson;
    private itemDescription id;

    public itemController(itemDescription itemDescription, Gson gson, SharedPreferences sharedPreferences){
        this.id = itemDescription;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){
        savedTheme = sharedPreferences.getInt(Constants.KEY_APPTHEME, 1);

        id.loadTheme(savedTheme);

        ZeldaGames gameClicked = getGameClicked(getPositionClicked(), Singletons.getLozGamesList(id.getApplicationContext()));

        setDescription(gameClicked);

        //Set the cover image of the game at the top of the page
        Picasso.get().load(Singletons.getLozGamesList(id.getApplicationContext()).get(getPositionClicked()).getImg_Cover_URL()).into((ImageView) id.findViewById(R.id.gameCover));

        setUpToolbar(gameClicked);
    }

    private int getPositionClicked(){
        return id.getIntent().getIntExtra("EXTRA_POSITION", 0);
    }

    private ZeldaGames getGameClicked(int positionClicked, List<ZeldaGames> lozGameList){
        //Get the game on which the user clicked
        return lozGameList.get(positionClicked);
    }

    private void setDescription(ZeldaGames gameClicked){
        //Set the "general characteristics" text
        String generalDesc = id.getString(R.string.generalCharacteristics, gameClicked.getName(),gameClicked.getRelease_year_in_Europe(), gameClicked.getModes(),
                gameClicked.getOriginal_Platforms(), gameClicked.getDungeons(), gameClicked.getRemakes());
        SpannableString spanGeneralDesc = new SpannableString(generalDesc);

        //Make the title of the section bigger and brown, and the content black
        spanGeneralDesc.setSpan(new RelativeSizeSpan(1.5f), 0, 23,0);

        spanGeneralDesc.setSpan(new ForegroundColorSpan(Color.BLACK), 23, generalDesc.length(),0);

        //Set the text in the TextView
        TextView generalDescView = id.findViewById(R.id.itemDescGeneral);
        spanGeneralDesc.setSpan(new ForegroundColorSpan(ContextCompat.getColor(generalDescView.getContext(),R.color.descSectionTitle)), 0,23,0);
        if(savedTheme == AppCompatDelegate.MODE_NIGHT_YES) {
            spanGeneralDesc.setSpan(new ForegroundColorSpan(ContextCompat.getColor(generalDescView.getContext(), R.color.darkerWhite)), 23, generalDesc.length(),0);
        }else{
            spanGeneralDesc.setSpan(new ForegroundColorSpan(ContextCompat.getColor(generalDescView.getContext(), R.color.black)), 23, generalDesc.length(),0);
        }
        generalDescView.setText(spanGeneralDesc);
    }

    private void setUpToolbar(ZeldaGames gameClicked){
        //Create a toolbar so that we can customize the title and put the name of the game on which the user clicked
        actionToolbar = (Toolbar) id.findViewById(R.id.toolbar_actionbar);
        actionToolbar.setTitleTextColor(0xFFFFFFFF);
        id.setSupportActionBar(actionToolbar);
        id.getSupportActionBar().setTitle(gameClicked.getName());
        id.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void spinnerItemSelected(MenuItem item){
        switch(item.getItemId())
        {
            case R.id.about:
                buttonAboutClick();
                break;
            case R.id.dark_mode:
                buttonDarkModeClick();
                break;
            default:
                //unknown error
        }
    }

    public void buttonAboutClick(){
        //Intent intent = new Intent(id, aboutActivity.class);
        id.startActivity(Singletons.getAboutIntent(id));
    }

    public void buttonDarkModeClick(){
        updatePreferencesTheme((savedTheme%2)+1);
        reloadTheme((savedTheme%2)+1);
    }

    public void updatePreferencesTheme(int appTheme) {
        sharedPreferences
                .edit()
                .putInt(Constants.KEY_APPTHEME, appTheme)
                .apply();
    }

    public void reloadTheme(int appTheme){
        //Maybe create a function for this in MainActivity? Not sure tho
        if(appTheme==1) {//If we are in dark mode
            id.setTheme(R.style.lightTheme);
        }else{//If we are in light mode
            id.setTheme(R.style.darkTheme);
        }
        id.finish();
        id.startActivity(id.getIntent());
    }
}
