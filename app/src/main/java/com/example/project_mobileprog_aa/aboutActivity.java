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
        setTheme(R.style.darkTheme);
        setContentView(R.layout.about_activity);

        TextView generalDescView = (TextView) findViewById(R.id.aboutTextView);
        generalDescView.setText(getString(R.string.about));

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
