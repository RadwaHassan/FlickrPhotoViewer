package com.example.ntg.flickrviewer.work.model;

public class Photo {

  public Photo(){

  }

  public Photo(String id, String title, String farm,
               String server, String secret, String owner) {
    super();
    this.id = id;
    this.title = title;
    this.farm = farm;
    this.server = server;
    this.secret = secret;
    this.owner = owner;
  }

  private String id;

  private String owner;

  private String title;

  private String farm;

  private String server;

  private String secret;

  private String imgUrl;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getFarm() {
    return farm;
  }

  public void setFarm(String farm) {
    this.farm = farm;
  }

  public String getServer() {
    return server;
  }

  public void setServer(String server) {
    this.server = server;
  }

  public String getSecret() {
    return secret;
  }

  public void setSecret(String secret) {
    this.secret = secret;
  }

  public String getImgUrl() {
    return imgUrl;
  }

  public void setImgUrl(String imgUrl) {
    this.imgUrl = imgUrl;
  }
}
