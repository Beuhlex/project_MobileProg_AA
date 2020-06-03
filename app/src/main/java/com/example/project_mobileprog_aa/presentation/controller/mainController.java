package com.example.project_mobileprog_aa.presentation.controller;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.project_mobileprog_aa.Constants;
import com.example.project_mobileprog_aa.R;
import com.example.project_mobileprog_aa.data.LozApi;
import com.example.project_mobileprog_aa.presentation.model.RestZeldaGamesResponse;
import com.example.project_mobileprog_aa.presentation.model.ZeldaGames;
import com.example.project_mobileprog_aa.presentation.view.MainActivity;
import com.example.project_mobileprog_aa.presentation.view.aboutActivity;
import com.example.project_mobileprog_aa.presentation.view.itemDescription;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class mainController {

    public SharedPreferences sharedPreferences;
    public Gson gson;
    public int savedTheme;
    public List<ZeldaGames> lozGamesList;
    private MainActivity ma;



    public mainController(MainActivity mainActivity, Gson gson, SharedPreferences sharedPreferences){
        this.ma = mainActivity;
        this.gson = gson;
        this.sharedPreferences = sharedPreferences;
    }

    public void onStart(){
        this.savedTheme = sharedPreferences.getInt(Constants.KEY_APPTHEME, 1);

        ma.loadTheme(savedTheme);

        lozGamesList = getDataFromCache();
        if(lozGamesList != null){
            ma.showList(lozGamesList);
        }else{
            makeApiCall();
        }
    }

    private List<ZeldaGames> getDataFromCache() {
        String jsonLozGames = sharedPreferences.getString(Constants.KEY_LOZGAMES_LIST, null);

        if(jsonLozGames == null){
            return null;
        }else{
            Type listType = new TypeToken<List<ZeldaGames>>(){}.getType();
            return gson.fromJson(jsonLozGames, listType);
        }
    }


    private void makeApiCall(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        LozApi lozapi = retrofit.create(LozApi.class);

        Call<RestZeldaGamesResponse> call = lozapi.getZeldaGamesResponse();
        call.enqueue(new Callback<RestZeldaGamesResponse>() {
            @Override
            public void onResponse(Call<RestZeldaGamesResponse> call, Response<RestZeldaGamesResponse> response) {
                if(response.isSuccessful() && response.body() != null){
                    List<ZeldaGames> zeldaGamesList = response.body().getResults();
                    saveList(zeldaGamesList);
                    ma.showList(zeldaGamesList);
                }else{
                    ma.showError();
                }
            }

            @Override
            public void onFailure(Call<RestZeldaGamesResponse> call, Throwable t) {
                ma.showError();
            }
        });

    }

    private void saveList(List<ZeldaGames> lozList){
        String jsonString = gson.toJson(lozList);
        sharedPreferences
                .edit()
                .putString(Constants.KEY_LOZGAMES_LIST, jsonString)
                .apply();

        Toast.makeText(ma.getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();
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
        Intent intent = new Intent(ma, aboutActivity.class);
        ma.startActivity(intent);
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
            ma.setTheme(R.style.lightTheme);
        }else{//If we are in light mode
            ma.setTheme(R.style.darkTheme);
        }

        ma.finish();
        ma.startActivity(new Intent(ma, ma.getClass()));
    }

    public void startItemActivity(int position){
        String jsonString = gson.toJson(lozGamesList);


        Intent intent = new Intent(ma, itemDescription.class);
        intent.putExtra("EXTRA_LIST", jsonString);
        intent.putExtra("EXTRA_POSITION", position);
        ma.startActivity(intent);
    }


}
