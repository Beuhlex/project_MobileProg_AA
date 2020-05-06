package com.example.project_mobileprog_aa;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.util.List;

public class itemDescription extends AppCompatActivity {

    private Gson gson;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);
        String jsonString = getIntent().getStringExtra("EXTRA_LIST");
        int positionClicked = getIntent().getIntExtra("EXTRA_POSITION", 0);


        gson = new GsonBuilder()
                .setLenient()
                .create();


        Type listType = new TypeToken<List<ZeldaGames>>(){}.getType();
        List<ZeldaGames> lozGameList = gson.fromJson(jsonString, listType);

        String nameText = getString(R.string.gameName, lozGameList.get(positionClicked).getName());
        TextView descName = (TextView) findViewById(R.id.itemDescName);
        descName.setText(nameText);

        String releaseText = getString(R.string.gameRelease, lozGameList.get(positionClicked).getRelease_year_in_Europe());
        TextView descRelease = (TextView) findViewById(R.id.itemDescReleaseYear);
        descRelease.setText(releaseText);

        String modesText = getString(R.string.gameModes, lozGameList.get(positionClicked).getModes());
        TextView descModes = (TextView) findViewById(R.id.itemDescModes);
        descModes.setText(modesText);

        String platformText = getString(R.string.gamePlatform, lozGameList.get(positionClicked).getOriginal_Platforms());
        TextView  descPlatforms = (TextView) findViewById(R.id.itemDescPlatforms);
        descPlatforms.setText(platformText);

        String dungeonsText = getString(R.string.gameDungeons, lozGameList.get(positionClicked).getDungeons());
        TextView descDungeons = (TextView) findViewById(R.id.itemDescDungeons);
        descDungeons.setText(dungeonsText);

        String remakesText = getString(R.string.gameRemakes, lozGameList.get(positionClicked).getRemakes());
        TextView descRemakes = (TextView) findViewById(R.id.itemDescRemakes);
        descRemakes.setText(remakesText);

        Picasso.get().load(lozGameList.get(positionClicked).getImg_Cover_URL()).into((ImageView) findViewById(R.id.gameCover));

    }


}
