package com.example.ntg.flickrviewer.work.controller;

import android.content.Context;
import android.util.Log;

import com.example.ntg.flickrviewer.work.communication.ApiResponse;
import com.example.ntg.flickrviewer.work.communication.FlickrApi;
import com.example.ntg.flickrviewer.work.interfaces.OnLoadingFinished;
import com.example.ntg.flickrviewer.work.model.Photo;
import com.example.ntg.flickrviewer.work.util.Config;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Controller {
  private static final String TAG = Controller.class.getSimpleName();
  private static final String PHOTOS = "photos";
  private static final String PHOTO = "photo";
  private static final String TITLE = "title";
  private static final String ID = "id";
  private static final String FARM = "farm";
  private static final String SERVER = "server";
  private static final String SECRET = "secret";
  private static final String OWNER = "owner";

  public Controller(){}

  public void sendRequest(Context context, Config.SearchType type, String input, String pageNum,
                          final OnLoadingFinished listener) {
    FlickrApi.getInstance(context).searchPhotos(type, input, pageNum,
            new ApiResponse.Listener<String>() {
              @Override
              public void onResponse(String response) {
                if (response != null)
                  extractPhotos(response, listener);
              }
            }, new ApiResponse.ErrorListener() {
              @Override
              public void onErrorResponse(String message) {
                Log.e(TAG, "Error occured:  " + message);
              }
            });
  }




  public static void extractPhotos(String response, OnLoadingFinished listener) {
    //remove jsonFlickrApi( which is at the start of response
    // and ")" which is at the end
    String newResponse = response.substring(14, response.length() - 1);
    JSONArray photoArray = null;
    try {
      JSONObject jSONObject = new JSONObject(newResponse);
      JSONObject jSONObjectInner = jSONObject.getJSONObject(PHOTOS);
      photoArray = jSONObjectInner.getJSONArray(PHOTO);
    } catch (JSONException e) {
      Log.e(TAG, "Error creating JsonObject: " + e);
    }

    if (photoArray.length() == 0) {
      listener.onFinish(null);
      return;
    }


    List<Photo> photoList = new ArrayList<>();
    for (int i = 0 ; i < photoArray.length(); i ++) {
      JSONObject jsonObject = null;
      try {
        jsonObject = photoArray.getJSONObject(i);

        Photo photo = new Photo(jsonObject.getString(ID), jsonObject.getString(TITLE),
                jsonObject.getString(FARM), jsonObject.getString(SERVER),
                jsonObject.getString(SECRET), jsonObject.getString(OWNER));

        String imgUrl = "http://farm" + photo.getFarm() + ".static.flickr.com/"
                + photo.getServer() + "/" + photo.getId() + "_" + photo.getSecret() +
                "_s.jpg";

        photo.setImgUrl(imgUrl);
        photoList.add(photo);
        if (i == photoArray.length() -1) {
          listener.onFinish(photoList);
          return;
        }

      } catch (JSONException e) {
        Log.e(TAG, "Error parsing Json: " + e);
      }
    }
  }
}
