package com.ezbus.tracking;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.ezbus.R;
import com.ezbus.client.BuyTicketActivity;
import com.ezbus.main.SharedPref;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MapFragment extends Fragment implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener,
        GoogleMap.OnInfoWindowClickListener {

    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1234;
    private static final float DEFAULT_ZOOM = 15f;
    private static final LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    private AutoCompleteTextView mSearchText;
    private Boolean mLocationPermissionsGranted = false;
    private GoogleMap mMap;
    private boolean start = false;
    private String idStart, idDest, nomeStart, nomeDest;
    private List<Marker> markers = new ArrayList<>();
    private SharedPref sharedpref;


    public MapFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        sharedpref = new SharedPref(getContext());
        super.onCreate(savedInstanceState);
        getLocationPermission();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mSearchText = view.findViewById(R.id.input_search);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        MapView mapView = view.findViewById(R.id.map);
        mapView.onCreate(savedInstanceState);
        mapView.onResume();
        mapView.getMapAsync(this);
        initSearch();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (sharedpref.isClient()) mMap.setOnInfoWindowClickListener(this);

        if (mLocationPermissionsGranted) {
            getDeviceLocation();

            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(true);
            mMap.setPadding(0,160,0,0);
        }

        FirebaseDatabase.getInstance().getReference("/map").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear();
                markers.removeAll(markers);
                for (DataSnapshot child : dataSnapshot.child("stops").getChildren()) {
                    String lat = child.child("position").child("coordX").getValue().toString();
                    String lon = child.child("position").child("coordY").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    String id = child.child("id").getValue().toString();
                    double latitude = Double.parseDouble(lat);
                    double longitude = Double.parseDouble(lon);
                    LatLng cod = new LatLng(latitude, longitude);
                    Marker stop = mMap.addMarker(new MarkerOptions()
                            .position(cod)
                            .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_fermata))
                            .visible(mMap.getCameraPosition().zoom>12)
                            .title(name)
                            .snippet(id)
                            .anchor(0.5f, 0.5f));
                    markers.add(stop);
                }
                for (DataSnapshot child : dataSnapshot.child("bus").getChildren()) {
                    String lat = child.child("position").child("coordX").getValue().toString();
                    String lon = child.child("position").child("coordY").getValue().toString();
                    String name = child.child("name").getValue().toString();
                    double latitude = Double.parseDouble(lat);
                    double longitude = Double.parseDouble(lon);
                    LatLng cod = new LatLng(latitude, longitude);
                    Marker bus = mMap.addMarker(new MarkerOptions()
                            .position(cod)
                            .icon(bitmapDescriptorFromVector(getContext(), R.drawable.ic_bus))
                            .visible(mMap.getCameraPosition().zoom>12)
                            .title(name)
                            .anchor(0.5f, 0.5f));
                    markers.add(bus);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mMap.setOnCameraMoveListener(() -> {
            CameraPosition cameraPosition = mMap.getCameraPosition();
            for(Marker m:markers) {
                m.setVisible(cameraPosition.zoom>12);
            }
        });
    }

    private BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, R.drawable.ic_backgroundmap);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 40, vectorDrawable.getIntrinsicHeight() + 20);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }

    private void getDeviceLocation(){
        FusedLocationProviderClient mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(getActivity());

        try {
            if (mLocationPermissionsGranted) {
                final Task<Location> location = mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Location currentLocation = task.getResult();
                        if (currentLocation!=null)
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),
                                    "My Location");
                    }
                });
            }
        } catch (SecurityException e) {
        }
    }

    private void moveCamera(LatLng latLng, String title){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, MapFragment.DEFAULT_ZOOM));

        /*if (!title.equals("My Location")) {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
            //Per ora non aggiungo marker
        }*/
    }

    private void getLocationPermission(){
        String[] permissions = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};

        if (ContextCompat.checkSelfPermission(getActivity(),
                FINE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            if(ContextCompat.checkSelfPermission(getActivity(),
                    COURSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                mLocationPermissionsGranted = true;
            } else {
                ActivityCompat.requestPermissions(getActivity(),
                        permissions,
                        LOCATION_PERMISSION_REQUEST_CODE);
            }
        } else {
            ActivityCompat.requestPermissions(getActivity(),
                    permissions,
                    LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mLocationPermissionsGranted = false;

        switch(requestCode){
            case LOCATION_PERMISSION_REQUEST_CODE:{
                if(grantResults.length > 0){
                    for (int grantResult : grantResults) {
                        if (grantResult != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionsGranted = false;
                            return;
                        }
                    }
                    mLocationPermissionsGranted = true;
                }
            }
        }
    }

    private void initSearch() {
        GoogleApiClient mGoogleApiClient = new GoogleApiClient
                .Builder(this.getContext())
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(getActivity(),this)
                .build();

        AutocompleteMap autocompleteMap = new AutocompleteMap(getContext(), mGoogleApiClient, LAT_LNG_BOUNDS, null);

        mSearchText.setAdapter(autocompleteMap);

        mSearchText.setOnEditorActionListener((textView, actionId, keyEvent) -> {
            if(actionId == EditorInfo.IME_ACTION_SEARCH
                    || actionId == EditorInfo.IME_ACTION_DONE
                    || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                    || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER){

                geoLocate();
            }

            return false;
        });
    }

    private void geoLocate(){
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(getContext());
        List<Address> list = new ArrayList<>();
        try {
            list = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {

        }

        if (list.size() > 0) {
            Address address = list.get(0);
            moveCamera(new LatLng(address.getLatitude(), address.getLongitude()),
                    address.getAddressLine(0));
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onInfoWindowClick(Marker m) {
        if (m.getSnippet()!=null) {
            if (!start) {
                idStart = m.getSnippet();
                nomeStart = m.getTitle();
                start = true;
                Toast.makeText(getActivity(), "Partenza impostata", Toast.LENGTH_SHORT).show();
            }
            else {
                idDest = m.getSnippet();
                nomeDest = m.getTitle();
                AlertDialog.Builder choice = new AlertDialog.Builder(this.getActivity());
                choice.setTitle("Vuoi acquistare un biglietto?");
                choice.setMessage("Da "+nomeStart+" a "+nomeDest).setPositiveButton("Si", (dialog, id) -> {
                    //Scelta confermata
                    Intent intent = new Intent(getActivity(), BuyTicketActivity.class);
                    intent.putExtra("Start", idStart);
                    intent.putExtra("Dest", idDest);
                    startActivity(intent);
                    start = false;
                })
                .setNegativeButton("No", (dialog, id) -> {
                    //Operazione annullata
                });
                choice.show();
                start = false;
            }
        }
    }

}