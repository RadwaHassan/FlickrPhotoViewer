package com.example.ntg.flickrviewer.work.views;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ntg.flickrviewer.R;
import com.example.ntg.flickrviewer.work.controller.Controller;
import com.example.ntg.flickrviewer.work.interfaces.OnItemClickListener;
import com.example.ntg.flickrviewer.work.interfaces.OnLoadMoreListener;
import com.example.ntg.flickrviewer.work.util.Config;
import com.example.ntg.flickrviewer.work.views.recyclerView.PhotoAdapter;

public class MainActivity extends BaseActivity implements OnItemClickListener {
  private static final String TAG = MainActivity.class.getSimpleName();
  private SearchView searchView;
  private String input;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    controller = new Controller();
    initializeView();
  }

  private void initializeView() {
    recyclerView = (RecyclerView) findViewById(R.id.primary_recyclerview);
    searchView = (SearchView) findViewById(R.id.custom_searchview);
    emptyView = (TextView)findViewById(R.id.empty_recyclerview);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    adapter = new PhotoAdapter(this, this, true, recyclerView);
    recyclerView.setAdapter(adapter);

    adapter.setOnLoadMoreListener(new OnLoadMoreListener() {
      @Override
      public void onLoadMore() {
        dialog.show();
        page ++;
        controller.sendRequest(MainActivity.this, Config.SearchType.KEYWORD,
                input, String.valueOf(page), MainActivity.this);
      }
    });


    searchView.requestFocus();
    searchView.setQueryHint(getResources().getString(R.string.keyword));
    searchView.setIconifiedByDefault(false);
    dialog = new ProgressDialog(this);
    dialog.setMessage(getResources().getString(R.string.loading));

    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
      @Override
      public boolean onQueryTextSubmit(String query) {
        input = query;
        if (!TextUtils.isEmpty(query)) {
          dialog.show();
          controller.sendRequest(MainActivity.this, Config.SearchType.KEYWORD, query, null, MainActivity.this);
        }
          return false;
      }

      @Override
      public boolean onQueryTextChange(String newText) {
        return false;
      }
    });

  }

  @Override
  public void onItemClick(String userID) {
    Intent intent = new Intent(MainActivity.this, SecondaryActivity.class);
    intent.putExtra(SecondaryActivity.USER_ID, userID);
    startActivity(intent);
  }

}
