package com.andreyvolkov.vkconnectionsapp.UI.Activity;

import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.andreyvolkov.vkconnectionsapp.R;

public class DescriptionActivity extends AppCompatActivity {

    private TextView descriptionApplicationName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Typeface typoGraphic = Typeface.createFromAsset(getAssets(), "fonts/TypoGraphica.otf");
        descriptionApplicationName = findViewById(R.id.descApplicationName);
        descriptionApplicationName.setTypeface(typoGraphic);
    }
}
