package com.example.ntg.flickrviewer.work.util;

public class Config {

  public static final String FLICKR_API_KEY = "f9d5af6d2358c6d2e0224fcb60ed88cf";
  public static final String FLICKR_API_SECRET = "a0e63350423fb9f5";


  public enum SearchType {
    KEYWORD("keyword"), OWNER("owner");
    private String value;

    SearchType(String value) {
        this.value = value;
    }
    public String getValue() {
      return this.value;
    }

  }
}
