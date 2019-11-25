package com.example.androidsmartcitytraveller;

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GetNearbyPlacesData  extends AsyncTask<Object, String, List<String>> {

    List<String> googlePlacesData=new ArrayList<>();
    GoogleMap mMap;
    List<String> url = new ArrayList<>();
    Context context;
    String route;
    Polyline line;
    PolylineOptions lineOptions=new PolylineOptions();


    @Override
    protected List<String> doInBackground(Object... objects) {

        mMap=(GoogleMap)objects[0];
        url=(List<String>) objects[1];
        context=(Context)objects[2];
        route=(String)objects[3];

        DownloadUrl downloadUrl=new DownloadUrl();

        for(int i=0;i<url.size();i++)
        {
            try{
                googlePlacesData.add(downloadUrl.readUrl(url.get(i)));
               // return googlePlacesData;
            }catch(IOException e)
            {
                e.printStackTrace();
            }


        }
        Log.i("google--------",googlePlacesData.toString());
        return googlePlacesData ;
    }


    @Override
    protected void onPostExecute(List<String> s) {
        List<HashMap<String, String>> nearbyPlacesList=new ArrayList<HashMap<String, String>>();
        List<HashMap<String, String>> nearbyPlacesList2= new ArrayList<HashMap<String, String>>();
        List<HashMap<String, String>> nearbyPlacesList3=new ArrayList<HashMap<String, String>>();


        for(int i=0;i<s.size();i++) {

            DataParser parser = new DataParser(context);
            if(route=="route1") {
                nearbyPlacesList = parser.parse(s.get(i), route);
                showNearbyPlaces(nearbyPlacesList);
            }
           else if(route=="route2") {
                nearbyPlacesList2 = parser.parse(s.get(i), route);
                showNearbyPlaces(nearbyPlacesList2);
            }
            else  {
                nearbyPlacesList3 = parser.parse(s.get(i), route);
                showNearbyPlaces(nearbyPlacesList3);
            }
            }


    }

    public void showNearbyPlaces(List<HashMap<String, String>> nearbyPlaceList)
    {

        List<LatLng> points=new ArrayList<>();

        DataSharing dataSharing=new DataSharing(context);
        String currentlat=dataSharing.retrieve("currentlat");
        String currentlng=dataSharing.retrieve("currentlng");


       // points.add(new LatLng(Double.parseDouble(currentlat),Double.parseDouble(currentlng)));
        for (int i = 0; i < nearbyPlaceList.size(); i++) {
        MarkerOptions markerOptions = new MarkerOptions();
        HashMap<String, String> googlePlace = nearbyPlaceList.get(i);


        String placeName = googlePlace.get("place_Name");
        String vicinity = googlePlace.get("vicinity");
        double lat = Double.parseDouble(googlePlace.get("lat"));
        double lng = Double.parseDouble(googlePlace.get("lng"));

        LatLng latLng = new LatLng(lat, lng);

          //  points.add(new LatLng(Double.parseDouble(currentlat),Double.parseDouble(currentlng)));
        points.add(latLng);
            //points.add(new LatLng(Double.parseDouble(currentlat),Double.parseDouble(currentlng)));


        markerOptions.position(latLng);
        markerOptions.title(placeName + " : " + vicinity);
        markerOptions.icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));


        mMap.addMarker(markerOptions);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(11f));

        }

          lineOptions.addAll(points);
        lineOptions.width(4);
        lineOptions.color(Color.RED);
        line = mMap.addPolyline(lineOptions);

        Log.i("Points---------",lineOptions.toString());

    }
}
