package com.example.ntg.flickrviewer.work.interfaces;


import com.example.ntg.flickrviewer.work.model.Photo;

import java.util.List;

public interface OnLoadingFinished {

  void onFinish(List<Photo> photoList);

}
