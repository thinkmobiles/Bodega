package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;

import java.util.List;

/**
 * Created by illia on 22.10.15.
 */
public class GridAdapter extends BaseAdapter {

    private List<ItemWrapper> mItems;
    private Context mContext;

    public GridAdapter(Context _ctx) {
        this.mContext = _ctx;
    }

    public void setItems(List<ItemWrapper> _items) {
        this.mItems = _items;
        notifyDataSetChanged();
    }

    public List<ItemWrapper> getItems() {
        return mItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public ItemWrapper getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_grid, null);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ItemWrapper item = mItems.get(position);
        viewHolder.textView.setText(styleName(item.getName()));
        Glide.with(mContext)
                .load(ApiManager.getPath(mContext) + item.getIcon())
                .fitCenter()
                .into(viewHolder.imageView);
        return convertView;
    }

    class ViewHolder {
        ImageView imageView;
        TextView textView;

        public ViewHolder(View _view){
            this.textView = (TextView) _view.findViewById(R.id.tvText_IG);
            this.imageView = (ImageView) _view.findViewById(R.id.ivImage_IG);
        }
    }

    private String styleName(String name) {
        if (name.contains(" "))
            name = name.substring(0, name.indexOf(" ")) + "\n" + name.substring(name.indexOf(" ") + 1);
        return name;
    }
}
