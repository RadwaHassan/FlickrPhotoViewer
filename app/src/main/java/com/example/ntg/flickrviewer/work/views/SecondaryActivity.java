package com.example.ntg.flickrviewer.work.views;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;

import com.example.ntg.flickrviewer.R;
import com.example.ntg.flickrviewer.work.controller.Controller;
import com.example.ntg.flickrviewer.work.interfaces.OnLoadMoreListener;
import com.example.ntg.flickrviewer.work.util.Config;
import com.example.ntg.flickrviewer.work.views.recyclerView.PhotoAdapter;

public class SecondaryActivity extends BaseActivity {
  private static final String TAG = SecondaryActivity.class.getSimpleName();
  public static final String USER_ID = "userID";

  private String userId = null;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_secondary);
    if (getIntent() != null && getIntent().hasExtra(USER_ID))
      userId = getIntent().getStringExtra(USER_ID);
    controller = new Controller();
    initializeView();
  }

  private void initializeView() {
    recyclerView = (RecyclerView) findViewById(R.id.secondary_recyclerview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new PhotoAdapter(this, null, false, recyclerView);
    recyclerView.setAdapter(adapter);

    adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore() {
        dialog.show();
        page ++;
        controller.sendRequest(SecondaryActivity.this, Config.SearchType.OWNER,
                userId, String.valueOf(page), SecondaryActivity.this);
      }
    });

    //to create back button
    final ActionBar supportActionBar = getSupportActionBar();
    if (supportActionBar != null) {
      supportActionBar.setHomeButtonEnabled(true);
      supportActionBar.setDisplayHomeAsUpEnabled(true);
    }


    dialog = new ProgressDialog(this);
    dialog.setMessage(getResources().getString(R.string.loading));
    dialog.show();

    controller.sendRequest(SecondaryActivity.this, Config.SearchType.OWNER, userId, null, SecondaryActivity.this);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == android.R.id.home) onBackPressed();
    return super.onOptionsItemSelected(item);
  }

}
