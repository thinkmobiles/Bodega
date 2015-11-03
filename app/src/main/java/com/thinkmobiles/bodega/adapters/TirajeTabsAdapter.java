package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.utils.TabItem;

import java.util.List;

/**
 * Created by illia on 02.11.15.
 */
public class TirajeTabsAdapter extends RecyclerView.Adapter<TirajeTabsAdapter.ViewHolder> {

    private List<TabItem> mItems;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View _view){
            super(_view);
            this.textView = (TextView) _view.findViewById(R.id.tvTab_IVPTT);
        }
    }

    public TabItem getItem(int position) {
        return mItems.get(position);
    }

    public TirajeTabsAdapter(Context _ctx, List<TabItem> _items) {
        mContext = _ctx;
        mItems = _items;
    }

    public List<TabItem> getData() {
        return mItems;
    }

    public void selectItem(int _position) {
        invalidateSelection();
        mItems.get(_position).isSelected = true;
        notifyDataSetChanged();
    }

    private void invalidateSelection() {
        for (TabItem i : mItems)
            i.isSelected = false;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_view_pager_tab_tiraje, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        TabItem item = mItems.get(i);
        viewHolder.textView.setText(item.name);
        if (item.isSelected) {
            viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.black));
            viewHolder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.text_white));
        }
        else {
            viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.tiraje_tab_unselected));
            viewHolder.textView.setTextColor(ContextCompat.getColor(mContext, R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}