package com.example.ntg.flickrviewer.work.views.recyclerView;

import android.content.Context;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ntg.flickrviewer.R;
import com.example.ntg.flickrviewer.work.interfaces.OnItemClickListener;
import com.example.ntg.flickrviewer.work.interfaces.OnLoadMoreListener;
import com.example.ntg.flickrviewer.work.model.Photo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
  private List<Photo> photos;
  private Context context;
  private OnItemClickListener listener;
  private OnLoadMoreListener moreListener;
  private boolean hasMorePhotos;
  private boolean loading;


  public PhotoAdapter(Context context, OnItemClickListener listener,
                      boolean hasMorePhotos, RecyclerView recyclerView) {
    super();
    this.context = context;
    this.listener = listener;
    this.hasMorePhotos = hasMorePhotos;
    photos = new ArrayList<>();

    recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
              @Override
              public void onScrolled(RecyclerView recyclerView,
                                     int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItems = recyclerView.getLayoutManager().getItemCount();
                int lastVisibleItem = ((LinearLayoutManager)recyclerView.getLayoutManager()).findLastVisibleItemPosition();
                if (!loading && (totalItems <= (lastVisibleItem + 1)) && moreListener != null) {
                  moreListener.onLoadMore();
                  loading = true;
                }
              }
            });
  }

  @Override
  public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.primary_photo_view, parent, false);
    return new PhotoHolder(itemView);
  }

  @Override
  public void onBindViewHolder(PhotoHolder holder, final int position) {
    holder.title.setText(TextUtils.isEmpty(photos.get(position).getTitle()) ?
            context.getResources().getString(R.string.no_title) : photos.get(position).getTitle());
    holder.moreIcon.setVisibility(hasMorePhotos ? View.VISIBLE : View.GONE);

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      Picasso.with(context).load(photos.get(position).getImgUrl())
              .placeholder(context.getResources().getDrawable(R.drawable.flickr_placeholder, context.getTheme()))
              .tag(context).into(holder.imageView);
    } else {
      Picasso.with(context).load(photos.get(position).getImgUrl())
              .placeholder(context.getResources().getDrawable(R.drawable.flickr_placeholder))
              .tag(context).into(holder.imageView);
    }

    holder.itemView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        if (listener != null)
          listener.onItemClick(photos.get(position).getOwner());
      }
    });


  }

  @Override
  public int getItemCount() {
    return photos.isEmpty() ? 0 : photos.size();
  }

  public void setOnLoadMoreListener(OnLoadMoreListener moreListener) {
    this.moreListener = moreListener;
  }

  public void updateDataSet(List<Photo> photoList) {
    photos = photoList;
    notifyDataSetChanged();
  }

  public void addToDataSet(List<Photo> photoList) {
    photos.addAll(photoList);
    notifyDataSetChanged();
  }

  public void setLoaded() {
    loading = false;
  }
}
