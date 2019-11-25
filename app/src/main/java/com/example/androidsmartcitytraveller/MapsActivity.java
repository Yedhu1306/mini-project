package com.example.androidsmartcitytraveller;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener{

    int PROXIMITY_RADIUS=10000;
    static int i=0;
    List<CustomList> list;
    Dbmanage dbmanage;
    ImageView ic_prev,ic_next;
    FloatingActionButton confirm;
    List<CustomList> route_list=new ArrayList<>();



    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Toast.makeText(this, "Map is Ready", Toast.LENGTH_SHORT).show();
        if (mLocationPermissionGranted) {
            getDeviceLocation();
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMyLocationButtonEnabled(false);
            init();

        }
    }

    private ImageView mGps;
    private AutoCompleteTextView mSearchText;
    public static final String TAG = "MapActivity";
    private static final String FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION;
    private static final String COARSE_LOCATION = Manifest.permission .ACCESS_COARSE_LOCATION;
    private Boolean mLocationPermissionGranted = false;
    private static  final int LOCATION_REQUEST_CODE = 1234;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private GoogleMap mMap;
    private PlaceAutocompleteAdapter mPlaceAutoCompleteAdapter;
    private GoogleApiClient mGoogleApiClient;
    private static  final LatLngBounds LAT_LNG_BOUNDS=new LatLngBounds(new LatLng(-40,-168),new LatLng(71,136));
    private GeoDataClient geoDataClient;

    int arr[]=new int[]{0,1,2};
    String route_array[]=new String[]{"Route1","Route2","Route3"};
    int position =0,location_position=0;
    double current_lat ;
    double current_lng ;
    TextView routename,loc_name;
    ImageView loc_prev,loc_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        list=new ArrayList<>();
        dbmanage = new Dbmanage(getApplicationContext());
        //Toast.makeText(this,"HelloOnCreate",Toast.LENGTH_SHORT).show();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mSearchText = (AutoCompleteTextView) findViewById(R.id.input_search);
        mGps = (ImageView)findViewById(R.id.ic_gps);
        getLocationPermission();
        geoDataClient = Places.getGeoDataClient(this,null);
        routename=(TextView)findViewById(R.id.route);
        loc_name=(TextView)findViewById(R.id.travelloc_name);
        ic_next=(ImageView)findViewById(R.id.ic_next);
        ic_prev=(ImageView)findViewById(R.id.ic_prev);

        loc_prev=(ImageView)findViewById(R.id.travelloc_prev);
        loc_next=(ImageView)findViewById(R.id.travelloc_next);

        ic_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev(view);
            }
        });
        ic_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                next(view);
            }
        });

        confirm=(FloatingActionButton)findViewById(R.id.confirm);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(routename.getText().toString().equals("Route1")) {
                    List<CustomList> l=new ArrayList<>();
                     l = dbmanage.getallTask("placestable");

                     for (int i=0;i<l.size();i++){
                         dbmanage.addtask4(l.get(i).placename,l.get(i).lat,l.get(i).lng);
                     }
                   // for (CustomList a : l) {
                     //   dbmanage.addtask4(a.placename,a.lat,a.lng);
                    //}



                    //Toast.makeText(MapsActivity.this, "Your Route1 is saved in Travel Plan", Toast.LENGTH_SHORT).show();
                }

               else if(routename.getText().toString().equals("Route2")) {
                    List<CustomList> l=new ArrayList<>();
                     l = dbmanage.getallTask("placestable2");
                    for (int i=0;i<l.size();i++){
                        dbmanage.addtask4(l.get(i).placename,l.get(i).lat,l.get(i).lng);
                    }
                   // Toast.makeText(MapsActivity.this, "Your Route2 is saved in Travel Plan", Toast.LENGTH_SHORT).show();
                }

                else  {
                    List<CustomList> l=new ArrayList<>();
                     l = dbmanage.getallTask("placestable3");
                    for (int i=0;i<l.size();i++){
                        dbmanage.addtask4(l.get(i).placename,l.get(i).lat,l.get(i).lng);
                    }

                }
                Intent intent=new Intent(MapsActivity.this,GifActivity.class);
                startActivity(intent);

            }
        });




        loc_prev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prev_location(view);
            }
        });

        loc_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                next_location(view);
            }
        });





    }



    private void getDeviceLocation(){
        Log.d(TAG,"get device location: getting the current device location");
        mFusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(this);
        try{
            if(mLocationPermissionGranted){
                Task location=mFusedLocationProviderClient.getLastLocation();
                location.addOnCompleteListener(new OnCompleteListener() {

                    @Override
                    public void onComplete(@NonNull Task task) {
                        if(task.isSuccessful()){
                            Log.d(TAG,"on complete: found Location!");
                            Location currentLocation=(Location) task.getResult();
                            moveCamera(new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude()),15f,"My Location");
                        }else{
                            Log.d(TAG,"on complete: current Location is null");
                            Toast.makeText(MapsActivity.this,"Unable to get current Location", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        }catch(SecurityException e){
            Log.e(TAG,"get device location: security exception"+e.getMessage());
        }

    }
    private void moveCamera(LatLng latLng, float zoom, String title){
        Log.d(TAG,"Move camera: moving the camera to:Lat: "+latLng.latitude+", lng: "+latLng.longitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));

        DataSharing dataSharing=new DataSharing(getApplicationContext());
        dataSharing.add("currentlat",latLng.latitude+"");
        dataSharing.add("currentlng",latLng.longitude+"");
        if(!title.equals("My Location"))
        {
            MarkerOptions options = new MarkerOptions()
                    .position(latLng)
                    .title(title);
            mMap.addMarker(options);
        }
        hideSoftKeyboard();

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        // Toast.makeText(this,"Start",Toast.LENGTH_SHORT).show();
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case LOCATION_REQUEST_CODE: {
                if (grantResults.length > 0) {
                    for (int i = 0; i < grantResults.length; i++) {
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                            mLocationPermissionGranted = false;
                            return;
                        }
                    }
                    // Toast.makeText(this,"HelloRequestPermissionResult",Toast.LENGTH_SHORT).show();
                    mLocationPermissionGranted = true;
                    initMap();
                }
            }

        }
    }

    private void initMap()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapsActivity.this);
        //Toast.makeText(this,"HelloInitMap",Toast.LENGTH_SHORT).show();
    }

    private void getLocationPermission()
    {
        String[] permissions = {FINE_LOCATION,COARSE_LOCATION};
        if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this.getApplicationContext(),
                    COARSE_LOCATION)== PackageManager.PERMISSION_GRANTED)
            {
                mLocationPermissionGranted = true;
                initMap();
            }
            else
            {
                ActivityCompat.requestPermissions(this,permissions,LOCATION_REQUEST_CODE);
            }
            //Toast.makeText(this,"GetLocationPermission",Toast.LENGTH_SHORT).show();
        }
        else
        {
            ActivityCompat.requestPermissions(this,permissions,LOCATION_REQUEST_CODE);
        }

    }



    private void init() {
        Log.d(TAG, "init:initializing");
        mGoogleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        mPlaceAutoCompleteAdapter = new PlaceAutocompleteAdapter(this,geoDataClient,LAT_LNG_BOUNDS,null);
        mSearchText.setAdapter(mPlaceAutoCompleteAdapter);
        mSearchText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_ACTION_DONE
                        || keyEvent.getAction() == KeyEvent.ACTION_DOWN
                        || keyEvent.getAction() == KeyEvent.KEYCODE_ENTER) {
                    Log.i("----Inside init()---","------");

                    geoLocate();

                }
                return false;
            }
        });
        mGps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG,"onclick: clicked GPS icon");
                getDeviceLocation();
            }
        });
        hideSoftKeyboard();
    }




    private void prev(View view) {

        if(position >=0)
        position--;
        switch (position){
            case 0:

                //ROUTE_1
                location_position=0;
                loc_name.setText("");
                route_list=dbmanage.getallTask("placestable");
                Log.i("route1-prev---------",route_list+"");
               // Toast.makeText(this, routename.getText().toString(), Toast.LENGTH_SHORT).show();
                 routename.setText("Route1");
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),12.5f,address.getAddressLine(0));
                tourist_places(new LatLng(current_lat,current_lng));

              /*  loc_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_location(view);
                    }
                });

                loc_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next_location(view);
                    }
                });
*/
                if(mMap!=null)
                    mMap.clear();
                break;

            case 1:

               //ROUTE-2

                location_position=0;
                loc_name.setText("");
                route_list=dbmanage.getallTask("placestable2");
                routename.setText(route_array[position]);
                Log.i("route2prev---------",route_list+"");
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),12.5f,address.getAddressLine(0));
                tourist_places2(new LatLng(current_lat,current_lng));

            /*    loc_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_location(view);
                    }
                });

                loc_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next_location(view);
                    }
                });

*/

                if(mMap!=null)
                    mMap.clear();
                break;

            case 2:

                 location_position=0;
                 loc_name.setText("");
                route_list=dbmanage.getallTask("placestable3");
                Log.i("route3prev---------",route_list+"");
                routename.setText(route_array[position]);
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),12.5f,address.getAddressLine(0));
                tourist_places3(new LatLng(current_lat,current_lng));

           /*     loc_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_location(view);
                    }
                });

                loc_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next_location(view);
                    }
                });
*/

                if(mMap!=null)
                    mMap.clear();

                break;
            default:
                Toast.makeText(this, "Already on First Route", Toast.LENGTH_SHORT).show();


        }

    }

    private void next(View view) {
        if(position <=2)
        position++;
        switch (position){
            case 0:
                location_position=0;
                loc_name.setText("");
                route_list=dbmanage.getallTask("placestable");
                Log.i("route1next---------",route_list+"");
                      routename.setText(route_array[position]);
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),12.5f,address.getAddressLine(0));
                tourist_places(new LatLng(current_lat,current_lng));

              /*  loc_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_location(view);
                    }
                });

                loc_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next_location(view);
                    }
                });
*/
                if(mMap!=null)
                    mMap.clear();

                break;

            case 1:

                location_position=0;
                route_list=dbmanage.getallTask("placestable2");
                loc_name.setText("");
                routename.setText(route_array[position]);
                Log.i("route2next---------",route_list+"");
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),12.5f,address.getAddressLine(0));
                tourist_places2(new LatLng(current_lat,current_lng));

               /* loc_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_location(view);
                    }
                });

                loc_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next_location(view);
                    }
                });
*/
                if(mMap!=null)
                    mMap.clear();

                break;

            case 2:

                location_position=0;
               route_list=dbmanage.getallTask("placestable3");
            //    Log.i("route3next---------",route_list.get(0).placename+"");
                routename.setText(route_array[position]);
                loc_name.setText("");
                moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),12.5f,address.getAddressLine(0));
                tourist_places3(new LatLng(current_lat,current_lng));

              /*  loc_prev.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        prev_location(view);
                    }
                });

                loc_next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        next_location(view);
                    }
                });
*/
                if(mMap!=null)
                    mMap.clear();

                break;
            default:
                Toast.makeText(this, "Already on Last Route", Toast.LENGTH_SHORT).show();


        }



    }



    private void prev_location(View view) {


        if (location_position >= 0) {
            location_position--;

            Log.i("prev-loc---------",route_list+"");

            loc_name.setText((location_position+1)+". "+route_list.get(location_position).placename);
            double lat = Double.parseDouble(route_list.get(location_position).lat);
            double lng = Double.parseDouble(route_list.get(location_position).lng);
            LatLng latLng=new LatLng(lat,lng);
            String address = route_list.get(location_position).placename;
           // mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat,lng),14f),4000,null);
             //moveCamera(latLng, 15f, address);
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));


            Log.i(address,"----LAT"+lat+"----Lng"+lng);


        }

        else {
            Toast.makeText(this, "Already on First Location", Toast.LENGTH_SHORT).show();
        }


                }


    private void next_location(View view) {

        if (location_position <route_list.size()) {


            Log.i("next-loc---------",route_list+"");

            loc_name.setText((location_position+1)+". "+route_list.get(location_position).placename);
            double lat = Double.parseDouble(route_list.get(location_position).lat);
            double lng = Double.parseDouble(route_list.get(location_position).lng);

            LatLng latLng=new LatLng(lat,lng);
            String address = route_list.get(location_position).placename;
            Log.i(address,"----LAT"+lat+"----Lng"+lng);
         //   mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,14f),4000,null);
            // moveCamera(latLng, 15f, address);
           // mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));

            Log.i(address,"----LAT"+lat+"----Lng"+lng);
            location_position++;
        }

        else {
            Toast.makeText(this, "Already on Last Location", Toast.LENGTH_SHORT).show();
        }


    }



    List<Address> addressList = new ArrayList<>();
    Address address;
    private void geoLocate() {
        //Toast.makeText(this,"Hello",Toast.LENGTH_SHORT).show();
        Log.d(TAG, "geolocate : geolocating");
        String searchString = mSearchText.getText().toString();

        Geocoder geocoder = new Geocoder(MapsActivity.this);

        try {
            addressList = geocoder.getFromLocationName(searchString, 1);
        } catch (IOException e) {
            Log.d(TAG, "geoLocate: IOException: " + e.getMessage());
        }
        if (addressList.size() > 0) {
             address = addressList.get(0);
            Log.d(TAG, "geoLocate: found a location! " + address.toString());

            if(mMap!=null){
                mMap.clear();
                dbmanage.deletetask();
            }

            moveCamera(new LatLng(address.getLatitude(),address.getLongitude()),15f,address.getAddressLine(0));
            routename.setText("Route1");
            route_list=dbmanage.getallTask("placestable");
            tourist_places(new LatLng(address.getLatitude(),address.getLongitude()));

        }
    }


    private void tourist_places(LatLng latLng)
    {
        current_lat = latLng.latitude;
         current_lng = latLng.longitude;
        // GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();

        Object dataTransfer[] = new Object[4];
        List<String> url = new ArrayList<>();

        DataSharing dataSharing = new DataSharing(MapsActivity.this);
        HashMap<String, String> tourist = new HashMap<>();
        tourist.put("restaurant", dataSharing.retrieve("restaurant"));
        tourist.put("amusement_park", dataSharing.retrieve("amusement_park"));
        tourist.put("shopping_mall", dataSharing.retrieve("shopping_mall"));

        Log.i("tourist-------->", tourist.toString());


        int time = Integer.parseInt(dataSharing.retrieve("time"));

        for (Map.Entry<String, String> entry : tourist.entrySet()) {

            if (entry.getValue().equalsIgnoreCase("yes")) {

                url.add(getUrl(current_lat, current_lng, entry.getKey()));

            }
        }


        List<String> newPlaces = new ArrayList<>();
        newPlaces.add("museum");
        newPlaces.add("zoo");
        newPlaces.add("church");
        newPlaces.add("cafe");
        newPlaces.add("hindu_temple");
        newPlaces.add("art_gallery");
        newPlaces.add("aquarium");
        for (String i : newPlaces) {
            if (url.size() < 3 && time > 120 && time <= 180) {
                url.add(getUrl(current_lat, current_lng, i));
            }
            if(time>180&&time<=360&&url.size()<5)
            {
                url.add(getUrl(current_lat, current_lng, i));
            }
            if(time>360&&time<=600&&url.size()<7)
            {
                url.add(getUrl(current_lat, current_lng, i));
            }

        }

        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = getApplicationContext();
        dataTransfer[3]="route1";

        new GetNearbyPlacesData().execute(dataTransfer);


     /*   list = dbmanage.getallTask("placestable");

        for (CustomList c : list) {
            Log.i("-----------", c.lat + " " + c.lng + "" + c.placename);
            Toast.makeText(this, c.lat + " " + c.lng + "" + c.placename, Toast.LENGTH_SHORT).show();

        }
*/
    }

    private void tourist_places2(LatLng latLng)
    {
        double current_lat = latLng.latitude;
        double current_lng = latLng.longitude;
        // GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();

        Object dataTransfer[] = new Object[4];
        List<String> url = new ArrayList<>();

        DataSharing dataSharing = new DataSharing(MapsActivity.this);
        HashMap<String, String> tourist = new HashMap<>();
        tourist.put("restaurant", dataSharing.retrieve("restaurant"));
        tourist.put("amusement_park", dataSharing.retrieve("amusement_park"));
        tourist.put("shopping_mall", dataSharing.retrieve("shopping_mall"));

        Log.i("tourist-------->", tourist.toString());


        int time = Integer.parseInt(dataSharing.retrieve("time"));

        for (Map.Entry<String, String> entry : tourist.entrySet()) {

            if (entry.getValue().equalsIgnoreCase("yes")) {

                url.add(getUrl(current_lat, current_lng, entry.getKey()));

            }
        }


        List<String> newPlaces = new ArrayList<>();
        newPlaces.add("hindu_temple");
        newPlaces.add("museum");
        newPlaces.add("art_gallery");
        newPlaces.add("church");
        newPlaces.add("zoo");
        newPlaces.add("cafe");
        newPlaces.add("aquarium");

        for (String i : newPlaces) {
            if (url.size() < 3 && time > 120 && time <= 180) {
                url.add(getUrl(current_lat, current_lng, i));
            }
            if(time>180&&time<=360&&url.size()<5)
            {
                url.add(getUrl(current_lat, current_lng, i));
            }
            if(time>360&&time<=600&&url.size()<7)
            {
                url.add(getUrl(current_lat, current_lng, i));
            }

        }

        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = getApplicationContext();
        dataTransfer[3]="route2";

        new GetNearbyPlacesData().execute(dataTransfer);


    /*    list = dbmanage.getallTask();

        for (CustomList c : list) {
            Log.i("-----------", c.lat + " " + c.lng + "" + c.placename);
            Toast.makeText(this, c.lat + " " + c.lng + "" + c.placename, Toast.LENGTH_SHORT).show();
*/

    }




    private void tourist_places3(LatLng latLng)
    {
        double current_lat = latLng.latitude;
        double current_lng = latLng.longitude;
        // GetNearbyPlacesData getNearbyPlacesData=new GetNearbyPlacesData();

        Object dataTransfer[] = new Object[4];
        List<String> url = new ArrayList<>();

        DataSharing dataSharing = new DataSharing(MapsActivity.this);
        HashMap<String, String> tourist = new HashMap<>();
        tourist.put("restaurant", dataSharing.retrieve("restaurant"));
        tourist.put("amusement_park", dataSharing.retrieve("amusement_park"));
        tourist.put("shopping_mall", dataSharing.retrieve("shopping_mall"));

        Log.i("tourist-------->", tourist.toString());


        int time = Integer.parseInt(dataSharing.retrieve("time"));

        for (Map.Entry<String, String> entry : tourist.entrySet()) {

            if (entry.getValue().equalsIgnoreCase("yes")) {

                url.add(getUrl(current_lat, current_lng, entry.getKey()));

            }
        }


        List<String> newPlaces = new ArrayList<>();
        newPlaces.add("hindu_temple");
        newPlaces.add("museum");
        newPlaces.add("zoo");
        newPlaces.add("church");
        newPlaces.add("cafe");
        newPlaces.add("art_gallery");
        newPlaces.add("aquarium");
        for (String i : newPlaces) {
            if (url.size() < 3 && time > 120 && time <= 180) {
                url.add(getUrl(current_lat, current_lng, i));
            }
            if(time>180&&time<=360&&url.size()<5)
            {
                url.add(getUrl(current_lat, current_lng, i));
            }
            if(time>360&&time<=600&&url.size()<7)
            {
                url.add(getUrl(current_lat, current_lng, i));
            }

        }

        dataTransfer[0] = mMap;
        dataTransfer[1] = url;
        dataTransfer[2] = getApplicationContext();
        dataTransfer[3]="route3";

        new GetNearbyPlacesData().execute(dataTransfer);


      /*  list = dbmanage.getallTask("placetavle");

        for (CustomList c : list) {
            Log.i("-----------", c.lat + " " + c.lng + "" + c.placename);
            Toast.makeText(this, c.lat + " " + c.lng + "" + c.placename, Toast.LENGTH_SHORT).show();

        }
*/
    }



    private String getUrl(double latitude, double longitude, String nearbyPlace)
    {
        StringBuilder googlePlaceUrl=new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlaceUrl.append("location="+latitude+","+longitude);
        googlePlaceUrl.append("&radius="+PROXIMITY_RADIUS);
        googlePlaceUrl.append("&type="+nearbyPlace);
        googlePlaceUrl.append("&sensor=true");
        googlePlaceUrl.append("&rankBy=distance");
        googlePlaceUrl.append("&key=AIzaSyDxWJimc24XJ7QNlBpJWVDeg9QSNmKILmc");
        return googlePlaceUrl.toString();
    }

//AIzaSyBeSGxsYJwLGS0oRPa7sVYlPl_jreTzesk
    private void hideSoftKeyboard()
    {
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }


}

