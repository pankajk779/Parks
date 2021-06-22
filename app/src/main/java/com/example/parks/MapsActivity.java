package com.example.parks;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.parks.adapter.ParkRecyclerViewAdapter;
import com.example.parks.data.AsyncResponse;
import com.example.parks.data.Repository;
import com.example.parks.model.Park;
import com.example.parks.model.ParkViewModel;
import com.example.parks.util.Util;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Park> parkList;
    String TAG_MAIN=this.getClass().getSimpleName();
    private ParkViewModel parkViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        parkViewModel=new ViewModelProvider(this)
                .get(ParkViewModel.class);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        final SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);


        BottomNavigationView bottomNavigationView =findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment=null;
                int id=item.getItemId();
                if(id==R.id.Map_nav_menu){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.map,mapFragment)
                            .commit();
                }
                else
                if(id==R.id.parks_nav_menu){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.map,ParksFragment.newInstance())
                            .commit();

                }else
                if(id== R.id.listView2){
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.map, ListsFragment.newInstance())
                            .commit();
                    Toast.makeText(getApplicationContext(),"Sorry! but the code in background here is getting errors.",Toast.LENGTH_LONG).show();

                }
                return true;
            }

        });



    }//end of onCreate

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG_MAIN, "onMapReady: is running");
        Toast.makeText(getApplicationContext(),"Hi, internet is required to check the app. Thanks.",Toast.LENGTH_LONG).show();

        mMap=googleMap;
        mMap.clear();
        parkList=new ArrayList<>();
        parkList.clear();
        PopulateMap();
    }

    private void PopulateMap() {

        Repository.getParks(new AsyncResponse() {
            @Override
            public void processPark(List<Park> parks) {
                parkList=parks;
                Log.d("TAG", "processPark: is running");
                for(Park park:parks) {
                    Log.d("processPark", "processPark: for loop is running");
                    LatLng gurgaon=new LatLng(Double.parseDouble(park.getLatitude()),Double.parseDouble(park.getLongitude()));
                    mMap.addMarker(new MarkerOptions().position(gurgaon).title(park.getFullName()));
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gurgaon,6));
                    Log.d("TAG", "processPark:designation  " + park.getDesignation());
                }
                parkViewModel.setParkList(parkList);
                if(parkList.size()!=0)
                Log.d("TAG", "processPark: " + parkList.size());


            }
        });
    }


}
