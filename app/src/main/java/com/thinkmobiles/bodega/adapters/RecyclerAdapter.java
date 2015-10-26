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
 * Created by illia on 26.10.15.
 */
public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<ItemWrapper> mItems;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View _view){
            super(_view);
            this.textView = (TextView) _view.findViewById(R.id.tvText_IR);
            this.imageView = (ImageView) _view.findViewById(R.id.ivImage_IR);
        }
    }

//    public void add(int position, String item) {
//        mLinks.add(position, item);
//        notifyItemInserted(position);
//    }
//
    public ItemWrapper getItem(int position) {
        return mItems.get(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecyclerAdapter(Context _ctx, List<ItemWrapper> _items) {
        mContext = _ctx;
        mItems = _items;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_recycler, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ItemWrapper item = mItems.get(i);
        viewHolder.textView.setText(item.getName());
        Glide.with(mContext)
                .load(ApiManager.getPath(mContext) + item.getIcon())
                .fitCenter()
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}