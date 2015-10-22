package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cristaliza.mvc.models.estrella.Item;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.utils.BitmapCreator;

import java.util.List;

/**
 * Created by illia on 22.10.15.
 */
public class GridAdapter extends BaseAdapter {

    private List<Item> mItems;
    private Context mContext;

    public GridAdapter(Context _ctx) {
        this.mContext = _ctx;
    }

    public void setItems(List<Item> _items) {
        this.mItems = _items;
        notifyDataSetChanged();
    }

    public List<Item> getItems() {
        return mItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public Item getItem(int position) {
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
        Item item = mItems.get(position);
        viewHolder.textView.setText(styleName(item.getName()));
        viewHolder.imageView.setImageBitmap(BitmapCreator.getBitmap(ApiManager.getPath() + item.getIcon()));
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
