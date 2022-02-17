package com.jurgenbermudez.sl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Interface variables

    DrawerLayout DrawerLayoutMain;
    NavigationView NavigationViewMain;
    Toolbar ToolbarMain;
    ActionBarDrawerToggle ToggleMain;
    String Title;

    //Create the Activity

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //UI

        DrawerLayoutMain = findViewById(R.id.drawer_layout);
        NavigationViewMain = findViewById(R.id.nav_view);
        ToolbarMain = findViewById(R.id.toolbar);

        //When app start

        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.content, new HomeFragment()).commit();
            setTitle("Home");
        }

        //Setup Toolbar

        setSupportActionBar(ToolbarMain);

        ToggleMain = setupDrawerToggle();
        DrawerLayoutMain.addDrawerListener(ToggleMain);
        NavigationViewMain.setNavigationItemSelectedListener(this);

    }

    //Select the item of the navigation menu

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        selectItemNav(item);
        return true;
    }

    //Setup drawer toggle

    private ActionBarDrawerToggle setupDrawerToggle()
    {
        return new  ActionBarDrawerToggle(this,
                DrawerLayoutMain,
                ToolbarMain,
                R.string.drawer_open,
                R.string.drawer_close);
    }

    //Save some variable when the activity will create again

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("Title",Title);
    }

    //Load saved variables in the onSaveInstanceState

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Title = savedInstanceState.getString("Title");
        if (Title==null)
            setTitle("Home");
        else
            setTitle(Title);
    }

    //Sync the toggle when change orientation

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        ToggleMain.syncState();
    }

    //Hold the configuration when change orientation

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        ToggleMain.onConfigurationChanged(newConfig);
    }

    //Get the fragment that was selected in the navigation menu

    @SuppressLint("NonConstantResourceId")
    private void selectItemNav(@NonNull MenuItem item) {

        FragmentManager fm = getSupportFragmentManager(); //Object to manage the fragments
        FragmentTransaction ft = fm.beginTransaction(); //Object to transact a new fragment
        Fragment fragment = new HomeFragment(); //default fragment

        switch(item.getItemId()) {

            case R.id.nav_Template:
                fragment = new TemplateFragment();
                break;

            case R.id.nav_Home:
                fragment = new HomeFragment();
                break;

            case R.id.nav_Themes:
                fragment = new ThemesFragment();
                break;

            case R.id.nav_About_App:
                fragment = new AboutAppFragment();
                break;

            case R.id.nav_About_Us:
                fragment = new AboutUsFragment();
                break;
        }

        ft.replace(R.id.content,fragment).commit();

        Title = item.getTitle().toString();
        setTitle(Title);
        DrawerLayoutMain.closeDrawers();
    }

    //If a item is selected

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(ToggleMain.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }
}