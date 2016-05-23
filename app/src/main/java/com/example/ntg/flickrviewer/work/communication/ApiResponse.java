package com.example.ntg.flickrviewer.work.communication;

public class ApiResponse {

  public interface Listener<String> {
    void onResponse(String response);
  }

  public interface ErrorListener {
    void onErrorResponse(String message);
  }
}
