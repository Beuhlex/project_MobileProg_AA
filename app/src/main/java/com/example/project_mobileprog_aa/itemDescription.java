package com.example.project_mobileprog_aa;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

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

        ZeldaGames gameClicked = lozGameList.get(positionClicked);

        String generalDesc = getString(R.string.generalCharacteristics, gameClicked.getName(),gameClicked.getRelease_year_in_Europe(), gameClicked.getModes(),
                gameClicked.getOriginal_Platforms(), gameClicked.getDungeons(), gameClicked.getRemakes());
        SpannableString spanGeneralDesc = new SpannableString(generalDesc);

        spanGeneralDesc.setSpan(new RelativeSizeSpan(1.5f), 0, 23,0);
        spanGeneralDesc.setSpan(new ForegroundColorSpan(Color.rgb(156,106,106)), 0,23,0);
        spanGeneralDesc.setSpan(new ForegroundColorSpan(Color.BLACK), 23, generalDesc.length(),0);

        TextView generalDescView = (TextView) findViewById(R.id.itemDescGeneral);
        generalDescView.setText(spanGeneralDesc);

        Picasso.get().load(lozGameList.get(positionClicked).getImg_Cover_URL()).into((ImageView) findViewById(R.id.gameCover));

    }


}
