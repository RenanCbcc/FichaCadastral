package com.example.dell.fichacadastral;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.maps.model.LatLng;

import Classes.Customer;
import Classes.LoadedRequest;
import Fragments.Deliveries_Fragment;
import Fragments.Profile_Fragment;
import Interfaces.onModifyFragment;
import Interfaces.onRequestClick;

/**
 * Created by Dell on 05/08/2017.
 * The principal purpose of this Activity is to offer a simple interface, through three fragments,
 * to a Customer, in this case a deliverer, using a Drawer View.
 */

public class Deliverer_Activity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, onModifyFragment,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult>, onRequestClick {
    private static final String EXTRA_ORING = "origin";
    private static final String EXTRA_CUSTOMER = "customer"; // Primary Key
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle; // used to open and quit the lateral menu
    private int selectedOption;
    private Customer costumer;
    private static final int REQUEST_ERRO_PLAY_SERVICES = 1;
    private static final int REQUEST_CHECK_GPS = 2;
    private Handler handler;
    private int numberOfAttempts;
    private boolean shouldExhibitDialog;
    private LatLng origin;
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

        handler = new Handler();
        shouldExhibitDialog = savedInstanceState == null;
        googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();


    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        origin = savedInstanceState.getParcelable(EXTRA_ORING);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("menuItem", selectedOption);
        outState.putParcelable(EXTRA_ORING, origin);
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
                fragment = Profile_Fragment.newInstance(costumer);
                break;
            case R.id.action_entregas:
                fragment = Deliveries_Fragment.newInstance(costumer);
                break;
            case R.id.action_logout:
                    Intent intent = new Intent(this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    Toast.makeText(this, getString(R.string.sucsses_msg_02,"Fulano"), Toast.LENGTH_SHORT).show();
                    startActivity(intent);

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

    /*
    private void handleSignInResult(GoogleSignInResult result) {
        if (result.isSuccess()) {
            final TextView txtNome = (TextView) findViewById(R.id.txtNome);
            final ImageView imgCapa = (ImageView) findViewById(R.id.imgCapa);
            final ImageView imgFoto = (ImageView) findViewById(R.id.imgFotoPerfil);
            if (googleApiClient.isConnected()) {
                Person person = Plus.PeopleApi.getCurrentPerson(googleApiClient);
                if (person != null) {
                    GoogleSignInAccount account = result.getSignInAccount();
                    costumer.setEmail(account.getEmail());
                    costumer.setNome(person.getDisplayName());
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
                    .findItem(R.id.action_logout)
                    .setTitle(googleApiClient.isConnected() ?
                            "Logout" : "Login");
        }
    }


    */

    @Override
    protected void onStart() {
        super.onStart();
        googleApiClient.connect();
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }


    /**
     * Verify whether the location settings is enable usng the method:
     * <code>LocationServices.SettingsApi.checkLocationSettings</code>, that, then, returns an
     * <code>PendingResult<LocationSettingsResult></code> object.
     *
     * @see {@link com.example.dell.fichacadastral#onResult(LocationSettingsResult)}
     */
    private void verifyGPSStatus() {
        Toast.makeText(this, "verifyGPSStatus", Toast.LENGTH_SHORT).show();

        final LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        LocationSettingsRequest.Builder locationSettingsRequest =
                new LocationSettingsRequest.Builder();

        locationSettingsRequest.setAlwaysShow(true);
        locationSettingsRequest.addLocationRequest(locationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(
                        googleApiClient,
                        locationSettingsRequest.build());

        result.setResultCallback(this);

    }


    //If the connection failed, attempt to encounter a solution. Generally on Google Play.
    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()) {
            try {
                connectionResult.startResolutionForResult(this,
                        REQUEST_ERRO_PLAY_SERVICES);
            } catch (IntentSender.SendIntentException siex) {
                siex.printStackTrace();
            }

        } else {
            //exhibitErrorMessage(this,connectionResult.getErrorCode());
            Toast.makeText(this, "Erro unsolved", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Soon after any sort of special permission was requested, it verifies the result.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(this, "onActivityResult", Toast.LENGTH_SHORT).show();

        if (requestCode == REQUEST_ERRO_PLAY_SERVICES
                && resultCode == RESULT_OK) {
            googleApiClient.connect();
        } else if (requestCode == REQUEST_CHECK_GPS) {
            if (resultCode == RESULT_OK) {
                this.numberOfAttempts = 0;
                handler.removeCallbacksAndMessages(null);
                getCurrentLocation();
            }
        } else {
            Toast.makeText(this, R.string.gps_error, Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Method get the current location soon after request permission from user.
     *
     * @see {@link com.example.dell.fichacadastral#onActivityResult(int, int, Intent)}
     */
    private void getCurrentLocation() {
        //Verify if the application have permissions of user.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

        } else {
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);

            if (location != null) {
                this.numberOfAttempts = 0;
                origin = new LatLng(location.getLatitude(), location.getLongitude());
            } else if (this.numberOfAttempts < 10) {
                this.numberOfAttempts++;
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        getCurrentLocation();
                    }
                }, 2000);
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permission, int[] grantResults) {
        Toast.makeText(this, "onRequestPermissionsResult", Toast.LENGTH_SHORT).show();
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else{
            Toast.makeText(this, R.string.gps_error, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method overrides a abstract method of the interface ResultCallback and verify whether
     * the settings of location have been activated. After this, whenever the user press any option,
     * the method below will be called
     *
     * @param locationSettingsResult
     * @see {@link com.example.dell.fichacadastral#onActivityResult(int, int, Intent)}
     */

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        Toast.makeText(this, "onResult", Toast.LENGTH_SHORT).show();
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                getCurrentLocation(); // in this case, we already have the necessary permission
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //Prevent confirmation message from being displayed more than once.
                if (shouldExhibitDialog) {
                    try {
                        status.startResolutionForResult(this,
                                REQUEST_CHECK_GPS);
                    } catch (IntentSender.SendIntentException isx) {
                        isx.printStackTrace();
                    }
                }
                break;
            case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                Log.wtf("NGVL", "This should not happen...");
                break;
        }
    }


    @Override
    public void selectRequest(LoadedRequest loadedRequest) {
        //TODO
    }
}