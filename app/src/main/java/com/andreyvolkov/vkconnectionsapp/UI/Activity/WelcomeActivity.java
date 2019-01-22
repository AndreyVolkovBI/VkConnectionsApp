package com.andreyvolkov.vkconnectionsapp.UI.Activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.andreyvolkov.vkconnectionsapp.R;
import com.vk.sdk.VKScope;
import com.vk.sdk.VKSdk;
import com.vk.sdk.util.VKUtil;

import java.util.Arrays;

public class WelcomeActivity extends AppCompatActivity {

    private static final String TAG = "WelcomeActivity";

    TextView mainText;
    TextView subtitle1;
    TextView subtitle2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        mainText = findViewById(R.id.mainTitle);
        subtitle1 = findViewById(R.id.subtitle1);
        subtitle2 = findViewById(R.id.subtitle2);

        Typeface typoGraphic = Typeface.createFromAsset(getAssets(), "fonts/TypoGraphica.otf");
        mainText.setTypeface(typoGraphic);
    }

    public void mainClick(View view) {
        Intent intent = new Intent(WelcomeActivity.this, VkAuthActivity.class);
        startActivity(intent);
    }
}
