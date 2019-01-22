package com.andreyvolkov.vkconnectionsapp.UI.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.andreyvolkov.vkconnectionsapp.R;
import com.andreyvolkov.vkconnectionsapp.UI.Helper.BottomNavigationViewHelper;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.BetweenSearch.BetweenSearchFragment;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.SimpleSearch.SimpleSearchFragment;
import com.andreyvolkov.vkconnectionsapp.UI.Fragment.VerifiedSearch.VerifiedSearchFragment;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private static final int ACTIVITY_NUM = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        init();
        if (!userIsLoggedIn()) {
            goToWelcomeActivity();
        }
    }

    private boolean userIsLoggedIn() {
        SharedPreferences sharedPref = getSharedPreferences("userId", Context.MODE_PRIVATE);
        String userId = sharedPref.getString("id", "");
        if (userId.length() != 0) {
            return true;
        } else {
            return false;
        }
    }

    private void goToWelcomeActivity() {
        Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
        startActivity(intent);
    }

    private void init() {
        BottomNavigationViewEx bottomNavigationViewEx = findViewById(R.id.bottomNavigation);
        bottomNavigationViewEx.setOnNavigationItemSelectedListener(navListener);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new SimpleSearchFragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    switch (item.getItemId()) {
                        case R.id.navVerified:
                            selectedFragment = new VerifiedSearchFragment();
                            break;
                        case R.id.navSearch:
                            selectedFragment = new SimpleSearchFragment();
                            break;
                        case R.id.navPeople:
                            selectedFragment = new BetweenSearchFragment();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
                    return true;
                }
            };

    public void descriptionClick(View view) {
        Intent intent = new Intent(getApplicationContext(), DescriptionActivity.class);
        startActivity(intent);
    }
}
