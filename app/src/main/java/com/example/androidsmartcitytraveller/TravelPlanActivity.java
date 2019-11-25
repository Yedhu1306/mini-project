package com.example.androidsmartcitytraveller;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.FragmentActivity;

public class TravelPlanActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Dbmanage dbmanage;
    PolylineOptions lineOptions;
    Polyline line;
    ImageView travelloc_prev,getTravelloc_next;
    TextView travelloc_name;
    int loc_pos=0;
    List<CustomList> route_list=new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_plan);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        travelloc_name=(TextView)findViewById(R.id.travelloc_name);
        travelloc_prev=(ImageView) findViewById(R.id.travelloc_prev);
        getTravelloc_next=(ImageView)findViewById(R.id.travelloc_next);

        dbmanage = new Dbmanage(TravelPlanActivity.this);
        lineOptions = new PolylineOptions();


        route_list = dbmanage.getallTask("final_placestable");

        travelloc_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev_loc();
            }
        });

        getTravelloc_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nextLoc();
            }
        });

    }

    private void nextLoc() {

        if (loc_pos < route_list.size()) {


            travelloc_name.setText((loc_pos+1)+". "+route_list.get(loc_pos).placename);
            double lat = Double.parseDouble(route_list.get(loc_pos).lat);
            double lng = Double.parseDouble(route_list.get(loc_pos).lng);

            LatLng latLng=new LatLng(lat,lng);
            String address = route_list.get(loc_pos).placename;
            Log.i(address,"----LAT"+lat+"----Lng"+lng);
            //   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14f),4000,null);
            // moveCamera(latLng, 15f, address);
           // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            loc_pos++;

            Log.i(address,"----LAT"+lat+"----Lng"+lng);
        }

        else {
            Toast.makeText(this, "Already on Last Location", Toast.LENGTH_SHORT).show();
        }


    }


    private void prev_loc() {

        if (loc_pos >=0) {

            loc_pos--;
            travelloc_name.setText((loc_pos+1)+". "+route_list.get(loc_pos).placename);
            double lat = Double.parseDouble(route_list.get(loc_pos).lat);
            double lng = Double.parseDouble(route_list.get(loc_pos).lng);
            LatLng latLng=new LatLng(lat,lng);
            String address = route_list.get(loc_pos).placename;
            // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),14f),4000,null);
            //moveCamera(latLng, 15f, address);
          //  mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));


            Log.i(address,"----LAT"+lat+"----Lng"+lng);
        }

        else {
            Toast.makeText(this, "Already on First Location", Toast.LENGTH_SHORT).show();
        }


    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        List<CustomList> lists = new ArrayList<>();

        lists = dbmanage.getallTask("final_placestable");

        mMap = googleMap;
        if (mMap!=null)
            mMap.clear();

        if (lists != null) {
            List<LatLng> points = new ArrayList<>();

            for (int i = 0; i < lists.size(); i++) {
                MarkerOptions markerOptions = new MarkerOptions();
                String placename = lists.get(i).placename;
                double lat = Double.parseDouble(lists.get(i).lat);
                double lng = Double.parseDouble(lists.get(i).lng);
                LatLng latLng = new LatLng(lat, lng);

                markerOptions.position(latLng);
                markerOptions.title(placename);
                markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));

                points.add(latLng);

                mMap.addMarker(markerOptions);
               // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,11));
            }
            lineOptions.addAll(points);
            lineOptions.width(4);
            lineOptions.color(Color.RED);
            line = mMap.addPolyline(lineOptions);


        }
    }

    @Override
    public void onBackPressed() {
        Intent i=new Intent(TravelPlanActivity.this,TravelActivity.class);
        startActivity(i);
    }
}
