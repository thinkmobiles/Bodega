package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.thinkmobiles.bodega.R;

import java.util.List;

/**
 * Created by illia on 21.10.15.
 */
public class SlidingMenuAdapter extends BaseAdapter {

    private List<String> mItems;
    private Context mContext;

    public SlidingMenuAdapter(Context _ctx) {
        this.mContext = _ctx;
    }

    public void setData(List<String> _items) {
        this.mItems = _items;
        notifyDataSetChanged();
    }

    public List<String> getData() {
        return mItems;
    }

    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public String getItem(int position) {
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
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_menu, null);
            TextView textView = (TextView) convertView.findViewById(R.id.tvMenuItem_IM);
            viewHolder = new ViewHolder(textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.textView.setText(mItems.get(position));
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
        return super.getItemViewType(position);
    }

    @Override
    public int getViewTypeCount() {
        return 8;
    }
}
