package com.example.project_mobileprog_aa;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements ListAdapter.OnGameListener {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private SharedPreferences sharedPreferences;
    private Gson gson;
    private List<ZeldaGames> lozGamesList;
    private Toolbar toolbar;

    private static final String BASE_URL = "https://raw.githubusercontent.com/Beuhlex/project_MobileProg_AA/master/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sharedPreferences = getSharedPreferences("app_AA", Context.MODE_PRIVATE);

        gson = new GsonBuilder()
                .setLenient()
                .create();

        lozGamesList = getDataFromCache();

        if(lozGamesList != null){
            showList(lozGamesList);
        }else{
            makeApiCall();
        }

        //Create a toolbar so that we can customize the title and put the name of the game on which the user clicked
        toolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(R.string.app_name);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) { //creates action menu
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {  //detects what item is selected
        switch(item.getItemId())
        {
            case R.id.about:
                Intent intent = new Intent(this, aboutActivity.class);
                startActivity(intent);
                break;
            case R.id.dark_mode:
                //dark_mode
                break;
            default:
                //unknown error
        }
        return super.onOptionsItemSelected(item);
    }

    private List<ZeldaGames> getDataFromCache() {
        String jsonLozGames = sharedPreferences.getString("Constant.KEY_LOZGAMES_LIST", null);

        if(jsonLozGames == null){
            return null;
        }else{
            Type listType = new TypeToken<List<ZeldaGames>>(){}.getType();
            return gson.fromJson(jsonLozGames, listType);
        }
    }

    private void showList(List<ZeldaGames> lozGamesList){
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<String> input = new ArrayList<>();

        mAdapter = new ListAdapter(lozGamesList, this);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall(){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
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
                    showList(zeldaGamesList);
                }else{
                    showError();
                }
            }

            @Override
            public void onFailure(Call<RestZeldaGamesResponse> call, Throwable t) {
                showError();
            }
        });

    }

    private void saveList(List<ZeldaGames> lozList){
        String jsonString = gson.toJson(lozList);
        sharedPreferences
                .edit()
                .putString("Constant.KEY_LOZGAMES_LIST", jsonString)
                .apply();

        Toast.makeText(getApplicationContext(), "List Saved", Toast.LENGTH_SHORT).show();

    }

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGameClick(int position) {
        String jsonString = gson.toJson(lozGamesList);


        Intent intent = new Intent(this, itemDescription.class);
        intent.putExtra("EXTRA_LIST", jsonString);
        intent.putExtra("EXTRA_POSITION", position);
        startActivity(intent);
    }
}
