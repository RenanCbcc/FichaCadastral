package com.example.dell.fichacadastral;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * Created by Dell on 05/08/2017.
 * The principal purpose of this Activity is to offer a simple interface, through three fragments,
 * to a Customer, in this case a deliverer, using a Drawer View.
 */

public class Deliverer_Activity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, Delivery_Fragment.onModifyFragment,
        Profile_Fragment.onModifyFragment {
    private static final String EXTRA_CUSTOMER = "customer"; // Primary Key
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle; // used to open and quit the lateral menu
    private int selectedOption;
    private Customer costumer;
    private GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.deliverer_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // we define toolbar as action bar for this activity
        {
            //We receive an object that will come from the activity Main Activity or Sign Up
            Intent intent = getIntent();
            costumer = (Customer) intent.getSerializableExtra(EXTRA_CUSTOMER);
        }
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
                fragment = new Profile_Fragment();
                break;
            case R.id.action_entregas:
                fragment = Delivery_Fragment.newInstance(costumer);
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

    @Override
    public void saveAllModifications(Customer customer) {
        this.costumer = customer; //Receives all changes made in the fragment
    }


    private void upDateMenu() {
        final ImageView imgCapa = (ImageView) findViewById(R.id.imgCapa);
        final ImageView imgFoto = (ImageView) findViewById(R.id.imgFotoPerfil);
        final TextView txtNome = (TextView) findViewById(R.id.txtNome);
        if (googleApiClient.isConnected()) {
            Person person = Plus.PeopleApi.getCurrentPerson(googleApiClient);
            if (person != null) {
                txtNome.setText(person.getDisplayName());
                if (person.hasImage()) {
                    Target target = new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom loadedFrom) {
                            RoundedBitmapDrawable fotoRedonda = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                            fotoRedonda.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
                            imgFoto.setImageDrawable(fotoRedonda);
                        }

                        @Override
                        public void onBitmapFailed(Drawable drawable) {
                        }

                        @Override
                        public void onPrepareLoad(Drawable drawable) {
                        }
                    };
                    Picasso.with(this)
                            .load(person.getImage().getUrl())
                            .into(target);
                }
                if (person.hasCover()) {
                    Picasso.with(this)
                            .load(person.getCover().getCoverPhoto().getUrl())
                            .into(imgCapa);
                }
            }
        } else {
            imgCapa.setImageBitmap(null);
            txtNome.setText(R.string.app_name);
            imgFoto.setImageResource(R.mipmap.ic_launcher);
        }
        navigationView.getMenu()
                .findItem(R.id.action_login_logout)
                .setTitle(googleApiClient.isConnected() ?
                        "Logout" : "Login");
    }


}
