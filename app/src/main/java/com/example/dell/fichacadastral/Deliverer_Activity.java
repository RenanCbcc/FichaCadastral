package com.example.dell.fichacadastral;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

/**
 * Created by Dell on 05/08/2017.
 */

public class Deliverer_Activity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle; // used to open and quit the lateral menu
    private int selectedOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // we define toolbar as action bar for this activity

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.app_name,
                R.string.app_name);
        //we associate drawerLayout to actionBarDrawerToggle
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            selectedOption = R.id.action_entregas;
        } else {
            selectedOption = savedInstanceState.getInt("menuItem");
        }
        selectFromMenu(navigationView.getMenu().findItem(selectedOption));

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menuItem", selectedOption);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void selectFromMenu(MenuItem menuItem) {
        selectedOption = menuItem.getItemId();
        menuItem.setChecked(true);
        drawerLayout.closeDrawers();
        //creating fragment object
        Fragment fragment = null;

        //initializing the fragment object which is selected
        switch (selectedOption) {
            case R.id.action_dados:
                fragment = new Profile_Activity();
                break;
            case R.id.action_entregas:
                fragment = new Delivery_Activity();
                break;
        }

        /*
        *  //replacing the fragment
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            //do not add unnecessarily a new fragment
            if (fragmentManager.findFragmentByTag(menuItem.getTitle().toString()) == null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.content_frame, fragment, menuItem.getTitle().toString());
            }
        }
        * */

        //replacing the fragment
        if (fragment != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.content_frame, fragment);
            ft.commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        selectFromMenu(item);
        return true;
    }
}
