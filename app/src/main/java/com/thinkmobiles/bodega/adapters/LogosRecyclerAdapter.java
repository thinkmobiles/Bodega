package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
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
 * Created by sasha on 30.10.2015.
 */
public class LogosRecyclerAdapter extends RecyclerView.Adapter<LogosRecyclerAdapter.ViewHolder> {

    private List<ItemWrapper> mItems;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View _view){
            super(_view);
            this.imageView = (ImageView) _view.findViewById(R.id.iv_image_ILR);
            this.textView = (TextView) _view.findViewById(R.id.tv_title_ILR);
        }
    }

    public ItemWrapper getItem(int position) {
        return mItems.get(position);
    }

    public LogosRecyclerAdapter(Context _ctx, List<ItemWrapper> _items) {
        mContext = _ctx;
        mItems = _items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_logos_recycler, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        viewHolder.textView.setText(mItems.get(i).getProductList().get(0).getName());
        Glide.with(mContext)
                .load(ApiManager.getPath(mContext) + mItems.get(i).getProductList().get(0).getImageSmall())
                .fitCenter()
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

}
