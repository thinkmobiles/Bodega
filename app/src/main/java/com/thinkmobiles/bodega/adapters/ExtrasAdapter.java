package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ExtraWrapper;

import java.util.List;

/**
 * Created by illia on 29.10.15.
 */
public class ExtrasAdapter extends RecyclerView.Adapter<ExtrasAdapter.ViewHolder> {

    private List<ExtraWrapper> mItems;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;
        ImageView ivPreview;
        ImageView ivPlayBtn;

        public ViewHolder(View _view){
            super(_view);
            this.textView = (TextView) _view.findViewById(R.id.tvItemText_IE);
            this.ivPreview = (ImageView) _view.findViewById(R.id.ivPreview_IE);
            this.ivPlayBtn = (ImageView) _view.findViewById(R.id.ivPlayBtn_IE);
        }
    }

    public ExtraWrapper getItem(int position) {
        return mItems.get(position);
    }

    public ExtrasAdapter(Context _ctx, List<ExtraWrapper> _items) {
        mContext = _ctx;
        mItems = _items;
    }

    public List<ExtraWrapper> getData() {
        return mItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_extras, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        ExtraWrapper item = mItems.get(i);
        // TODO fix later
        if (!TextUtils.isEmpty(item.getImage())
                && !TextUtils.isEmpty(item.getImageDescriprion())) {
            Picasso.with(mContext)
                    .load("file:" + ApiManager.getPath(mContext) + item.getImage())
//                    .fitCenter()
                    .into(viewHolder.ivPreview);
            viewHolder.ivPlayBtn.setVisibility(View.INVISIBLE);
            viewHolder.textView.setText(item.getImageDescriprion());
        }
        if (!TextUtils.isEmpty(item.getVideo())
                && !TextUtils.isEmpty(item.getVideoDescription())) {
            Picasso.with(mContext)
                    .load("http://img.youtube.com/vi/" + item.getVideo() + "/default.jpg")
//                    .fitCenter()
                    .into(viewHolder.ivPreview);
            viewHolder.ivPlayBtn.setVisibility(View.VISIBLE);
            viewHolder.textView.setText(item.getVideoDescription());
        }

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
