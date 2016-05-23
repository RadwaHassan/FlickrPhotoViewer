package com.example.ntg.flickrviewer.work.views.recyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ntg.flickrviewer.R;

public class PhotoHolder extends RecyclerView.ViewHolder {

  public ImageView imageView;
  public TextView title;
  public ImageView moreIcon;

  public PhotoHolder(View itemView) {
    super(itemView);
    imageView = (ImageView) itemView.findViewById(R.id.primary_imageview);
    title = (TextView) itemView.findViewById(R.id.primary_title);
    moreIcon = (ImageView) itemView.findViewById(R.id.icon_arrow);
  }


}
