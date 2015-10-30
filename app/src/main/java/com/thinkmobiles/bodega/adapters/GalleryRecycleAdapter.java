package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;

import java.util.List;

/**
 * Created by sasha on 27.10.2015.
 */
public class GalleryRecycleAdapter extends RecyclerView.Adapter<GalleryRecycleAdapter.ViewHolder> {

    private List<String> mItems;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View _view){
            super(_view);
            this.imageView = (ImageView) _view.findViewById(R.id.ivImage_IGR);
        }
    }

    public String getItem(int position) {
        return mItems.get(position);
    }

    public GalleryRecycleAdapter(Context _ctx, List<String> _items) {
        mContext = _ctx;
        mItems = _items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_recicler, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        Glide.with(mContext)
                .load(ApiManager.getPath(mContext) + mItems.get(i))
                .fitCenter()
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}