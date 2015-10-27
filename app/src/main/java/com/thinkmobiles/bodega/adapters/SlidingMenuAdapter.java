package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;

import java.util.List;

/**
 * Created by illia on 21.10.15.
 */
public class SlidingMenuAdapter extends BaseAdapter {

    private List<ItemWrapper> mItems;
    private Context mContext;

    public SlidingMenuAdapter(Context _ctx) {
        this.mContext = _ctx;
    }

    public void setData(List<ItemWrapper> _items) {
        this.mItems = _items;
        notifyDataSetChanged();
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
        ItemWrapper entry = mItems.get(position);
        ViewHolder viewHolder;
        if(convertView == null){
            if (entry.getLevelNumber() == Constants.LEVEL_FIRST)
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_lvl_one, parent, false);
            if (entry.getLevelNumber() == Constants.LEVEL_SECOND)
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_lvl_two, parent, false);
            if (entry.getLevelNumber() == Constants.LEVEL_THIRD)
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu_lvl_three, parent, false);
            TextView textView = (TextView) convertView.findViewById(R.id.tvMenuItem_IM);
            viewHolder = new ViewHolder(textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(entry.getName());
        return convertView;
    }

    class ViewHolder {
        TextView textView;

        public ViewHolder(TextView text){
            this.textView = text;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mItems.get(position).getLevelNumber();
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }
}
