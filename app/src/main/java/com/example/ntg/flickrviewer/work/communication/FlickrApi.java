package com.example.ntg.flickrviewer.work.communication;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.ntg.flickrviewer.work.util.Config;

import org.json.JSONObject;

public class FlickrApi {
  private static final String TAG = FlickrApi.class.getSimpleName();

  private static FlickrApi ourInstance;
  private static Context appCtx;
  private static final String BASE_URL = "https://api.flickr.com/services/" +
          "rest/?method=flickr.photos.search";
  private static final String URL = BASE_URL + "&api_key=" + Config.FLICKR_API_KEY;

  public static FlickrApi getInstance(Context context) {
    if (ourInstance == null) {
      synchronized (RequestPipeline.class) {
        if (ourInstance == null)
          ourInstance = new FlickrApi(context);
      }
    }
    return ourInstance;
  }

  private FlickrApi(Context context) {
    appCtx = context;
  }

  public void searchPhotos(Config.SearchType type, String input, String pageNum, final ApiResponse.Listener<String> listener,
                           final ApiResponse.ErrorListener errorListener) {

    String url = null;
    switch (type) {
      case KEYWORD:
        url = URL + "&text=" + input + "&format=json";
        break;
      case OWNER:
        url = URL + "&user_id=" + input + "&format=json";
        break;
    }

    if (!TextUtils.isEmpty(pageNum))
      url = url + "&page=" + pageNum;

    StringRequest searchRequest = new StringRequest(url,
            new Response.Listener<String>() {
      @Override
      public void onResponse(String response) {
        listener.onResponse(response);
      }
    }, new Response.ErrorListener() {
      @Override
      public void onErrorResponse(VolleyError error) {
        if (error.networkResponse != null)
          errorListener.onErrorResponse(error.networkResponse.toString());
      }
    });

    RequestPipeline.getInstance(appCtx).addToRequestQueue(searchRequest);
  }
}
