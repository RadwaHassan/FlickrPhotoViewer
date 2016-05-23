package com.example.ntg.flickrviewer.work.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ntg.flickrviewer.work.controller.Controller;
import com.example.ntg.flickrviewer.work.interfaces.OnLoadingFinished;
import com.example.ntg.flickrviewer.work.model.Photo;
import com.example.ntg.flickrviewer.work.views.recyclerView.PhotoAdapter;

import java.util.List;

public class BaseActivity extends AppCompatActivity implements OnLoadingFinished {

  protected PhotoAdapter adapter;
  protected ProgressDialog dialog;
  protected Controller controller;
  protected RecyclerView recyclerView;
  protected TextView emptyView;
  protected int page = 1;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
  }

  @Override
  public void onFinish(List<Photo> photoList) {
    if (photoList == null || photoList.size() == 0) {
      if (recyclerView != null) recyclerView.setVisibility(View.GONE);
      if (emptyView != null) emptyView.setVisibility(View.VISIBLE);
    } else {
      if (recyclerView != null) recyclerView.setVisibility(View.VISIBLE);
      if (emptyView != null) emptyView.setVisibility(View.GONE);

      if (page == 1)
        adapter.updateDataSet(photoList);
      else
        adapter.addToDataSet(photoList);
      adapter.setLoaded();
    }
    dialog.hide();
  }
}
