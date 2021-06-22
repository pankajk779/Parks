package com.example.parks.data;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.parks.controller.AppController;
import com.example.parks.model.Activities;
import com.example.parks.model.Images;
import com.example.parks.model.OperatingHours;
import com.example.parks.model.Park;
import com.example.parks.model.StandardHours;
import com.example.parks.model.Topics;
import com.example.parks.util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    // this is where we are going to fetch the data.
   static List<Park> parkList=new ArrayList<>();

    public static void getParks(final AsyncResponse callback){
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, Util.url1, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("JSON", "onResponse: "+ response.toString());
                        try {
                            JSONArray jsonArray=response.getJSONArray("data");
                            Log.d("TAG", "onResponse: got JsonArray-data "+ Integer.toString(jsonArray.length()));
                            for (int i = 0; i <jsonArray.length() ; i++) {
                                Park park=new Park();
                                JSONObject jsonObject=jsonArray.getJSONObject(i);
                                park.setFullName(jsonObject.getString("fullName"));
                                park.setStates(jsonObject.getString("states"));
                                park.setDesignation(jsonObject.getString("designation"));
                                park.setUrl(jsonObject.getString("url"));
                                park.setName(jsonObject.getString("name"));
                                park.setDescription(jsonObject.getString("description"));
                                park.setLatitude(jsonObject.getString("latitude"));
                                park.setLongitude(jsonObject.getString("longitude"));

                                JSONArray activityArray = jsonObject.getJSONArray("activities");
                                List<Activities> activitiesList = new ArrayList<>();
                                for (int j = 0; j < activityArray.length() ; j++) {
                                    Activities activities = new Activities();
                                    activities.setId(activityArray.getJSONObject(j).getString("id"));
                                    activities.setName(activityArray.getJSONObject(j).getString("name"));

                                    activitiesList.add(activities);
                                }
                                park.setActivities(activitiesList);



                                try {
                                    JSONArray imageListArray = jsonObject.getJSONArray("images");


                                List<Images> list=new ArrayList<>();
                                for (int j = 0; j <imageListArray.length() ; j++) {
                                    Log.d("TAG3", "onResponse: inside of Repolistory.getData.images");
                                    Images images=new Images();
                                    images.setCredit(imageListArray.getJSONObject(j).getString("credit"));
                                    images.setTitle(imageListArray.getJSONObject(j).getString("title"));
                                    images.setUrl(imageListArray.getJSONObject(j).getString("url"));

                                    list.add(images);
                                }
                                park.setImages(list);
                                
                                try{
                                    JSONArray topicsArray=jsonObject.getJSONArray("topics");
                                    List<Topics> topicsList=new ArrayList<>();
                                    for (int k = 0; k <topicsArray.length(); k++) {
                                        JSONObject topicsObject=topicsArray.getJSONObject(k);
                                        Topics topics=new Topics();
                                        topics.setName(topicsObject.getString("name"));

                                        topicsList.add(topics);
                                    }
                                    park.setTopics(topicsList);
                                    
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: "+e.toString());
                                }
                                

                                try{
                                    JSONArray operatingHoursArray=jsonObject.getJSONArray("operatingHours");
                                    List<OperatingHours> operatingHoursList =new ArrayList<>();

                                    for (int j = 0; j <operatingHoursArray.length() ; j++) {
                                        OperatingHours operatingHours=new OperatingHours();
                                        StandardHours standardHours=new StandardHours();
                                        JSONObject jsonObject1=operatingHoursArray.getJSONObject(j);
                                        JSONObject standardHoursObject=jsonObject1.getJSONObject("standardHours");
                                        standardHours.setMonday(standardHoursObject.getString("monday"));
                                        standardHours.setTuesday(standardHoursObject.getString("tuesday"));
                                        standardHours.setWednesday(standardHoursObject.getString("wednesday"));
                                        standardHours.setThursday(standardHoursObject.getString("thursday"));
                                        standardHours.setFriday(standardHoursObject.getString("friday"));
                                        standardHours.setSunday(standardHoursObject.getString("sunday"));
                                        standardHours.setSaturday(standardHoursObject.getString("saturday"));
                                        operatingHours.setDescription(jsonObject1.getString("description"));
                                        Log.d("TAG1", "onResponse: "+ jsonObject1.getString("description"));
                                        operatingHours.setStandardHours(standardHours);


                                        operatingHoursList.add(operatingHours);
                                    }
                                    park.setOperatingHours(operatingHoursList);



                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: "+e.toString());
                                }

                                parkList.add(park);
                                }catch (Exception e){
                                    Log.d("TAG", "onResponse: "+ e.toString());
                                }


                            }
                            if(callback != null){callback.processPark(parkList);}
                        } catch (JSONException e) {
                            Log.d("TAG", "onResponse: " +e.toString());
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("JSON", "onErrorResponse: "+ error.toString());
            }
        });
        if(callback != null){callback.processPark(parkList);}
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
        Log.d("TAG","getParks: parkLIst size"+ Integer.toString(parkList.size()));

    }





}
