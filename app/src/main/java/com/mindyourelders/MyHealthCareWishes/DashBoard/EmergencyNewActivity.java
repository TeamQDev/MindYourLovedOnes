package com.mindyourelders.MyHealthCareWishes.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.mindyourelders.MyHealthCareWishes.HomeActivity.R;
import com.mindyourelders.MyHealthCareWishes.InsuranceHealthCare.SpecialistAdapter;
import com.mindyourelders.MyHealthCareWishes.model.Emergency;
import com.mindyourelders.MyHealthCareWishes.model.Specialist;

import java.util.ArrayList;

public class EmergencyNewActivity extends AppCompatActivity implements View.OnClickListener,OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, LocationListener {
    Context context=this;
    ImageView imgBack;
    GoogleApiClient googleApiClient;
    LocationRequest locationRequest;
    GoogleMap mMap;
    SupportMapFragment mapFragment;
    static final int MY_PERMISSIONS_REQUEST_LOCATION=100;
    Marker LoactionMarker;
    RelativeLayout rlMaps;
    ArrayList<Emergency> EmergencyList;
    ArrayList<Specialist> specialistList;
    ListView lvEmergency,lvPrimary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency);
        initUI();
        getEmergencyData();
        getPhysicianData();
        Intent intent=getIntent();
        if (intent.getExtras()!=null)
        {
            String flag=intent.getExtras().getString("Flag");
            if (flag.equals("Map"))
            {
                checkRuntimePermission();
                mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
                mapFragment.getMapAsync(this);
                rlMaps.setVisibility(View.VISIBLE);
            }else if(flag.equals("NoMap")){
                rlMaps.setVisibility(View.GONE);
            }
        }
        initUI();
        initListener();
        setEmergencyAdapter();
        setSpecilistAdapter();
    }

    private void setSpecilistAdapter() {
        SpecialistAdapter specialistAdapter=new SpecialistAdapter(context,specialistList);
        lvPrimary.setAdapter(specialistAdapter);
    }

    private void getPhysicianData() {
        specialistList=new ArrayList<>();

        Specialist P1=new Specialist();
        P1.setName("Dr. John");
        P1.setType("Orthopedic");
        P1.setAddress("#203,10 los Street, los Angeles, California.");
        P1.setImage(R.drawable.doct);
        P1.setOfficePhone("789-789-5236");

        specialistList.add(P1);
    }

    private void setEmergencyAdapter() {
        EmergencyAdapter emergencyAdapter=new EmergencyAdapter(context,EmergencyList);
        lvEmergency.setAdapter(emergencyAdapter);
    }

    private void getEmergencyData() {
        EmergencyList=new ArrayList<>();

        Emergency P1=new Emergency();
        P1.setName("Mary Charlo");
        P1.setRelationType("Daughter");
        P1.setAddress("#203,10 Downing Street, los Angeles, California.");
        P1.setImage(R.drawable.profile_rounds);
        P1.setPhone("987-789-5236");

        Emergency P2=new Emergency();
        P2.setName("Chuck Charlo");
        P2.setRelationType("Wife");
        P2.setAddress("#203,10 Downing Street, los Angeles, California.");
        P2.setImage(R.drawable.circular_profile);
        P2.setPhone("789-789-5236");

        EmergencyList.add(P1);
        EmergencyList.add(P2);
    }

    private void initListener() {
        imgBack.setOnClickListener(this);
    }

    private void initUI() {
        imgBack= (ImageView) findViewById(R.id.imgBack);
        rlMaps= (RelativeLayout) findViewById(R.id.rlMaps);
        lvEmergency= (ListView) findViewById(R.id.lvEmergency);
        lvPrimary= (ListView) findViewById(R.id.lvPrimary);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.imgBack:
                finish();
                break;
        }
    }

    private boolean checkRuntimePermission() {
        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Asking user if explanation is needed
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

       /* MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(new LatLng(18.477091, 73.890686));
        markerOptions.title("Kondhawa");
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_ROSE));

        MarkerOptions marker = new MarkerOptions();
        marker.position(new LatLng(17.691401, 74.000938));
        marker.title("Satara");
        marker.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN));

        mMap.addMarker(markerOptions);
        mMap.addMarker(marker);*/

        /*CameraPosition cameraPosition = new CameraPosition.Builder().target(
                new LatLng(18.477091, 73.890686)).zoom(12).build();

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
        buildGoogleApiClient();

    }

    private void buildGoogleApiClient() {
        googleApiClient = new GoogleApiClient.Builder(this).addConnectionCallbacks(this).addApi(LocationServices.API).build();
        googleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(1000);
        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Check Permissions Now
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_REQUEST_LOCATION);
        } else {
          /*  int off = 0;
            try {
                off = Settings.Secure.getInt(getContentResolver(), Settings.Secure.LOCATION_MODE);
            } catch (Settings.SettingNotFoundException e) {
                e.printStackTrace();
            }
            if(off==0){
                Intent onGPS = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(onGPS);
            }*/
            // permission has been granted, continue as usual
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
        }



    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onLocationChanged(Location location) {
        double lat=location.getLatitude();
        double longi=location.getLongitude();

        if(LoactionMarker!=null)
        {
            LoactionMarker.remove();
        }

        LatLng latLng = new LatLng(lat, longi);
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.position(latLng);
        markerOptions.title("Current Location");
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.loc_marker));
        mMap.addMarker(markerOptions);

        /*Projection projection = mMap.getProjection();
        LatLng markerPosition = markerOptions.getPosition();
        Point markerPoint = projection.toScreenLocation(markerPosition);
        LatLng targetPosition = projection.fromScreenLocation(markerPoint);*/
      /*

        Point targetPoint = new Point(markerPoint.x, markerPoint.y - mapFragment.getHeight() / 2);

        mMap.animateCamera(CameraUpdateFactory.newLatLng(targetPosition), 1000, null);*/


        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(10));

        if (googleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(googleApiClient, this);
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // Permission was granted.
                    if (ContextCompat.checkSelfPermission(this,
                            android.Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                        if (googleApiClient == null) {
                            buildGoogleApiClient();
                        }
                        mMap.setMyLocationEnabled(true);
                    }

                } else {

                    // Permission denied, Disable the functionality that depends on this permission.
                    Toast.makeText(this, "permission denied", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other permissions this app might request.
            //You can add here other case statements according to your requirement.
        }
    }
}
