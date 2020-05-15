package com.example.project_mobileprog_aa;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.lang.reflect.Type;
import java.util.List;

public class itemDescription extends AppCompatActivity{

    private Gson gson;
    private Toolbar actionToolbar;

    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_description);
        //Getting the string containing our item list, and the position clicked.
        String jsonString = getIntent().getStringExtra("EXTRA_LIST");
        int positionClicked = getIntent().getIntExtra("EXTRA_POSITION", 0);

        //Create the gson
        gson = new GsonBuilder()
                .setLenient()
                .create();

        //Convert the string of the list back into a list
        Type listType = new TypeToken<List<ZeldaGames>>(){}.getType();
        List<ZeldaGames> lozGameList = gson.fromJson(jsonString, listType);

        //Get the game on which the user clicked
        ZeldaGames gameClicked = lozGameList.get(positionClicked);

        //Set the "general characteristics" text
        String generalDesc = getString(R.string.generalCharacteristics, gameClicked.getName(),gameClicked.getRelease_year_in_Europe(), gameClicked.getModes(),
                gameClicked.getOriginal_Platforms(), gameClicked.getDungeons(), gameClicked.getRemakes());
        SpannableString spanGeneralDesc = new SpannableString(generalDesc);

        //Make the title of the section bigger and brown, and the content black
        spanGeneralDesc.setSpan(new RelativeSizeSpan(1.5f), 0, 23,0);
        spanGeneralDesc.setSpan(new ForegroundColorSpan(Color.rgb(156,106,106)), 0,23,0);
        spanGeneralDesc.setSpan(new ForegroundColorSpan(Color.BLACK), 23, generalDesc.length(),0);

        //Set the text in the TextView
        TextView generalDescView = (TextView) findViewById(R.id.itemDescGeneral);
        generalDescView.setText(spanGeneralDesc);

        //Set the cover image of the game at the top of the page
        Picasso.get().load(lozGameList.get(positionClicked).getImg_Cover_URL()).into((ImageView) findViewById(R.id.gameCover));

        //Create a toolbar so that we can customize the title and put the name of the game on which the user clicked
        actionToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        actionToolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(actionToolbar);
        getSupportActionBar().setTitle(gameClicked.getName());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
}
