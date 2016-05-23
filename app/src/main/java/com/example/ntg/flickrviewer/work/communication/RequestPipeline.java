package com.example.ntg.flickrviewer.work.communication;

import android.content.Context;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class RequestPipeline {

  private static RequestPipeline ourInstance;
  private RequestQueue requestQueue;
  private static Context ctx;

  public static RequestPipeline getInstance(Context context) {
    if (ourInstance == null) {
      synchronized (RequestPipeline.class) {
        if (ourInstance == null) {
          ourInstance = new RequestPipeline(context);
        }
      }
    }
    return ourInstance;
  }

  private RequestPipeline(Context context) {
    ctx = context;
    requestQueue = getRequestQueue();
  }

  public RequestQueue getRequestQueue() {
    if (requestQueue == null) {
      requestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
    }
    return requestQueue;
  }

  public <T> void addToRequestQueue(Request<T> req) {
    req.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS * 2, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
    getRequestQueue().add(req);
  }
}
