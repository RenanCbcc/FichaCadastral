package com.example.dell.fichacadastral;
import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Dell on 05/08/2017.
 */

public class Deliveries_Fragment extends Fragment implements CompoundButton.OnCheckedChangeListener,
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        ResultCallback<LocationSettingsResult> {
    private static final String EXTRA_CUSTOMER = "customer"; // tuple{Key,value}
    private Customer customer;
    private TextView txt_Name;
    private TextView txt_Nivel;
    private TextView txt_Xp;
    private TextView txt_Feed;
    private TextView txt_Mean;
    private ToggleButton toggleButton;
    private GoogleApiClient googleApiClient;
    private static final int REQUEST_ERRO_PLAY_SERVICES = 1;
    private static final int REQUEST_CHECK_GPS = 2;
    private Handler handler;
    private int numberOfAttempts;
    private boolean shouldExhibitDialog;
    private LatLng origin;
    public static Deliveries_Fragment newInstance(Customer costumer) {
        Bundle parametros = new Bundle();
        parametros.putSerializable(EXTRA_CUSTOMER, costumer);
        Deliveries_Fragment fragment = new Deliveries_Fragment();
        fragment.setArguments(parametros);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer = (Customer) getArguments().getSerializable(EXTRA_CUSTOMER);
        handler = new Handler();
        shouldExhibitDialog = savedInstanceState == null;
        googleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.deliveries_layout, container, false);
        txt_Name = view.findViewById(R.id.nome_id);
        txt_Nivel = view.findViewById(R.id.nivel_id);
        txt_Xp = view.findViewById(R.id.xp_id);
        txt_Feed = view.findViewById(R.id.feedbad_id);
        txt_Mean = view.findViewById(R.id.media_id);
        toggleButton = view.findViewById(R.id.tgb_isAvailable);
        toggleButton.setOnCheckedChangeListener(this);

        if (customer != null) {
            txt_Name.setText(String.format("Nome: %s", customer.getNome()));
            txt_Nivel.setText(String.format("%s%s", "Nivel: ".toUpperCase(), customer.getNome()));
            txt_Xp.setText(String.format("XP: %s", customer.getNome()));
            txt_Feed.setText(String.format("%s%s", "FeedBack: ".toUpperCase(), customer.getNome()));
            txt_Mean.setText(String.format("%s%s", "Média: ".toUpperCase(), customer.getNome()));
            toggleButton.setChecked(customer.isAvailable());

        }
        return view;
    }

    @Override
    public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
        Activity activity = getActivity();
        if (activity instanceof onModifyFragment) {
            if (toggleButton.getText().toString().equals("Disponivel")) {
                toggleButton.setChecked(false);
                if (customer != null) {
                    customer.setAvailable(false);
                    Toast.makeText(getActivity(), "Estou indisponível para realizar Entregas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor", Toast.LENGTH_SHORT).show();
                }

            } else {
                toggleButton.setChecked(true);
                this.getCurrentLocation();
                if (customer != null) {
                    customer.setAvailable(true);
                    Toast.makeText(getActivity(), "Estou disponível para realizar Entregas", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getActivity(), "Impossivel atribuir valor", Toast.LENGTH_SHORT).show();
                }

            }
            onModifyFragment listener = (onModifyFragment) activity;
            listener.saveAllModifications(customer);
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        verifyGPSStatus();
    }

    @Override
    public void onConnectionSuspended(int i) {
        googleApiClient.connect();
    }


    public interface onModifyFragment {
        void saveAllModifications(Customer customer);
    }

    @Override
    public void onStart() {
        super.onStart();
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
                connectionResult.startResolutionForResult(getActivity(),
                        REQUEST_ERRO_PLAY_SERVICES);
            } catch (IntentSender.SendIntentException siex) {
                siex.printStackTrace();
            }

        } else {
            //exhibitErrorMessage(this,connectionResult.getErrorCode());
            Toast.makeText(getActivity(), "Erro unsolved", Toast.LENGTH_SHORT).show();
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
            Toast.makeText(getActivity(), R.string.gps_error, Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Method get the current location soon after request permission from user.
     *
     * @see {@link com.example.dell.fichacadastral#onActivityResult(int , int , Intent )}
     */
    private void getCurrentLocation() {
        //Verify if the application have permissions of user.
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 0);

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
        super.onRequestPermissionsResult(requestCode, permission, grantResults);
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            getCurrentLocation();
        }else{
            Toast.makeText(getActivity(), R.string.gps_error, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * This method overrides a abstract method of the interface ResultCallback.
     *
     * @param locationSettingsResult
     * @see {@link @link com.example.dell.fichacadastral#onActivityResult(int, int, Intent)}
     */

    @Override
    public void onResult(@NonNull LocationSettingsResult locationSettingsResult) {
        final Status status = locationSettingsResult.getStatus();
        switch (status.getStatusCode()) {
            case LocationSettingsStatusCodes.SUCCESS:
                getCurrentLocation();
                break;
            case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                //Prevent confirmation message from being displayed more than once.
                if (shouldExhibitDialog) {
                    try {
                        status.startResolutionForResult(getActivity(),
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




}
