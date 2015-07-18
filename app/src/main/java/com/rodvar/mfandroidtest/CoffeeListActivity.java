package com.rodvar.mfandroidtest;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.rodvar.mfandroidtest.adapter.CoffeeShopAdapter;
import com.rodvar.mfandroidtest.model.Venue;

import java.text.DateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;


public class CoffeeListActivity extends ActionBarActivity implements LocationListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final long LOCATION_INTERVAL = 10000l;
    private static final long LOCATION_FASTEST_INTERVAL = 5000l;
    private CoffeeShopAdapter coffeeShopAdapter;

    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private boolean isRequestingLocationUpdates;
    private Location currentLocation;
    private String lastUpdateTime;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.buildGoogleApiClient();
        this.createLocationRequest();
        setContentView(R.layout.activity_coffee_list);
        ListView listView = (ListView) this.findViewById(R.id.shopList);
        this.coffeeShopAdapter = new CoffeeShopAdapter(this, fakeList());
        listView.setAdapter(this.coffeeShopAdapter);
    }

    private List<Venue> fakeList() {
        List<Venue> list = new LinkedList<Venue>();
        list.add(new Venue("Pepe", "91 Goulburn St", Long.valueOf(23l)));
        list.add(new Venue("Pregfa", "231 Tuvieja en tanga", Long.valueOf(233l)));
        list.add(new Venue("Largartoide", "91 Marriot St", Long.valueOf(354l)));
        list.add(new Venue("Coffeee puto", "343 Marquick St", Long.valueOf(53l)));
        return list;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_coffee_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onConnected(Bundle connectionHint) {
        Log.d("LOCATION", "Connected!");
        this.currentLocation = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        this.startLocationUpdates();
    }

    @Override
    public void onLocationChanged(Location location) {
        currentLocation = location;
        lastUpdateTime = DateFormat.getTimeInstance().format(new Date());
        Toast.makeText(this, "OK!", Toast.LENGTH_SHORT).show();
        // TODO update
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.checkGPS();
        if (googleApiClient.isConnected() && !isRequestingLocationUpdates)
            this.startLocationUpdates();
        else
            googleApiClient.connect();
    }

    private void checkGPS() {
        if (!((LocationManager) this.getSystemService(Context.LOCATION_SERVICE))
                .isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent gpsOptionsIntent = new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(gpsOptionsIntent);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.stopLocationUpdates();
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                googleApiClient, locationRequest, this);
        this.isRequestingLocationUpdates = true;
    }

    protected void stopLocationUpdates() {
        try {
            LocationServices.FusedLocationApi.removeLocationUpdates(
                    googleApiClient, this);
            this.isRequestingLocationUpdates = false;
        } catch (Exception e) {
            Log.e("LOCATION", "Failed", e);
        }
    }

    protected void createLocationRequest() {
        this.locationRequest = new LocationRequest();
        this.locationRequest.setInterval(LOCATION_INTERVAL);
        this.locationRequest.setFastestInterval(LOCATION_FASTEST_INTERVAL);
        this.locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); //BALANCED_POWER_ACCURACY
    }

    protected synchronized void buildGoogleApiClient() {
        this.googleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        // TODO
        Log.w("LOCATION", "CONNECTION FAILED");
    }


    @Override
    public void onConnectionSuspended(int i) {
        // TODO
        Log.w("LOCATION", "CONNECTION SUSPENDED");
    }
}
