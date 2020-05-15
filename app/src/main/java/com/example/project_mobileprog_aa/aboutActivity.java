package com.example.project_mobileprog_aa;

import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class aboutActivity extends AppCompatActivity {

    private Toolbar actionToolbar;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        String aboutContent = getString(R.string.about);
        SpannableString spanAboutText = new SpannableString(aboutContent);

        //Make the title of the section bigger and brown, and the content black

        spanAboutText.setSpan(new ForegroundColorSpan(Color.BLACK), 0, aboutContent.length(),0);

        //Set the text in the TextView
        TextView generalDescView = (TextView) findViewById(R.id.aboutTextView);
        generalDescView.setText(spanAboutText);

        actionToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
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
