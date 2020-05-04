package com.example.project_mobileprog_aa;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ListAdapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;

    private static final String BASE_URL = "https://raw.githubusercontent.com/Beuhlex/project_MobileProg_AA/master/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        makeApiCall();
    }

    private void showList(List<ZeldaGames> lozGamesList){
        recyclerView = findViewById(R.id.recycler_view);

        recyclerView.setHasFixedSize(true);

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final List<String> input = new ArrayList<>();

        mAdapter = new ListAdapter(lozGamesList);
        recyclerView.setAdapter(mAdapter);
    }

    private void makeApiCall(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

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

    private void showError() {
        Toast.makeText(getApplicationContext(), "API Error", Toast.LENGTH_SHORT).show();
    }
}
