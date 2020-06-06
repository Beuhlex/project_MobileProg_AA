package com.example.project_mobileprog_aa;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.project_mobileprog_aa.data.LozApi;
import com.example.project_mobileprog_aa.presentation.model.ZeldaGames;
import com.example.project_mobileprog_aa.presentation.view.aboutActivity;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Singletons {

    private static Gson gsonInstance;
    private static SharedPreferences sharedPreferencesInstance;
    private static LozApi lozApiInstance;
    private static List<ZeldaGames> lozGamesListInstance;
    private static Intent aboutIntentInstance;


    public static Gson getGson(){
        if(gsonInstance == null){
            gsonInstance = new GsonBuilder()
                    .setLenient()
                    .create();
        }
        return gsonInstance;
    }

    public static SharedPreferences getSharedPreferences(Context context){
        if(sharedPreferencesInstance == null){
            sharedPreferencesInstance = context.getSharedPreferences("app_AA", Context.MODE_PRIVATE);
        }
        return sharedPreferencesInstance;
    }

    public static LozApi getLozApi(){
        if(lozApiInstance==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Constants.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(getGson()))
                    .build();

            lozApiInstance = retrofit.create(LozApi.class);
        }
        return lozApiInstance;
    }

    public static List<ZeldaGames> getLozGamesList(Context context){
        if(lozGamesListInstance == null){
            String jsonLozGames = getSharedPreferences(context).getString(Constants.KEY_LOZGAMES_LIST, null);

            if(jsonLozGames != null){
                Type listType = new TypeToken<List<ZeldaGames>>(){}.getType();
                lozGamesListInstance = getGson().fromJson(jsonLozGames, listType);
            }
        }
        return lozGamesListInstance;
    }

    public static Intent getAboutIntent(Activity activity){
        if(aboutIntentInstance == null){
            aboutIntentInstance = new Intent(activity, aboutActivity.class);
        }
        return aboutIntentInstance;
    }
}
