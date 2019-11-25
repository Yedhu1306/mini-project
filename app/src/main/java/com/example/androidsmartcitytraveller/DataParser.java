package com.example.androidsmartcitytraveller;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DataParser  {

    Context context;
    String duration="";
    Dbmanage dbmanage;

    List<HashMap<String, String>> placesList=new ArrayList<>();
    List<HashMap<String, String>> placesList2=new ArrayList<>();
    List<HashMap<String, String>> placesList3=new ArrayList<>();

    HashMap<String, String> placeMap=null;


    DataParser(Context context){
        this.context=context;
        dbmanage=new Dbmanage(context);
    }



    public List<HashMap<String, String>> parse(String jsonData, String route)
    {
        JSONArray jsonArray=null;
        JSONObject jsonObject;

        try {
            jsonObject=new JSONObject(jsonData);
            jsonArray=jsonObject.getJSONArray("results");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray,route);
    }

    private List<HashMap<String, String>> getPlaces(JSONArray jsonArray, String route){
        int count=jsonArray.length();
        Log.i("result---------",count+"");
        //  for(int i=0;i<3;i++)
       // {
            try {
                if(route=="route1") {
                    placeMap = getPlace((JSONObject) jsonArray.get(0));
                    dbmanage.addtask(placeMap.get("place_Name"), placeMap.get("lat"), placeMap.get("lng"));
                    placesList.add(placeMap);
                    return placesList;
                }
                else if (route=="route2") {
                    placeMap = getPlace((JSONObject) jsonArray.get(1));
                    dbmanage.addtask2(placeMap.get("place_Name"),placeMap.get("lat"),placeMap.get("lng"));
                    placesList2.add(placeMap);
                    return placesList2;
                }

                else {
                    placeMap = getPlace((JSONObject) jsonArray.get(2));
                    dbmanage.addtask3(placeMap.get("place_Name"),placeMap.get("lat"),placeMap.get("lng"));
                    placesList3.add(placeMap);
                    return placesList3;
                }


                //placesList.add(placeMap);
               // Log.i("placeList---->getPlaces",placesList.toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }
        //}
        return placesList;

    }



    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)  {
        HashMap<String, String> googlePlacesMap=new HashMap<>();
        String placeName="";
        String vicnity="";
        String latitude="";
        String longitude="";
        String reference="";

        try {

            if (!googlePlaceJson.isNull("name")) {
                placeName = googlePlaceJson.getString("name");
                Log.i("place List-----------`-",placeName);
            }
            if(!googlePlaceJson.isNull("vicinity")){
                vicnity=googlePlaceJson.getString("vicinity");
            }
            latitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude=googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");
            reference=googlePlaceJson.getString("reference");
            googlePlacesMap.put("place_Name",placeName);
          //  Toast.makeText(DataParser.this,placeName,Toast.LENGTH_SHORT).show();
            googlePlacesMap.put("vicinity",vicnity);
            googlePlacesMap.put("lat",latitude);
            googlePlacesMap.put("lng",longitude);
            googlePlacesMap.put("ref",reference);

             //  List<CustomList>l=dbmanage.getallTask();


         //   Toast.makeText(context, l.toString(), Toast.LENGTH_SHORT).show();



        }catch (JSONException e) {
            e.printStackTrace();
        }
        return googlePlacesMap;

    }





    public HashMap<String, String> parseDirections(String jsonData) {
        JSONArray jsonArray = null;
        JSONObject jsonObject;
        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs");


        } catch (JSONException e) {
            e.printStackTrace();
        }
        return getDuration(jsonArray);
    }

    private HashMap<String, String> getDuration(JSONArray googleDirectionsJson){
        HashMap<String, String> googleDirectionsMap=new HashMap<>();

        String distance="";
        String end="";

        Log.d("Json response",googleDirectionsJson.toString());
        try {

            duration=googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance=googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");
            end=googleDirectionsJson.getJSONObject(0).getString("end_address");
            googleDirectionsMap.put("end_address",end);
            googleDirectionsMap.put("duration",duration);
            googleDirectionsMap.put("distance",distance);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  googleDirectionsMap;

    }


}
