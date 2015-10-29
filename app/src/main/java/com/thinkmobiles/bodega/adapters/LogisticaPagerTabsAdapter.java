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
 * Created by illia on 29.10.15.
 */
public class LogisticaPagerTabsAdapter extends RecyclerView.Adapter<LogisticaPagerTabsAdapter.ViewHolder> {

    private List<TabItem> mItems;
    private Context mContext;

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public ViewHolder(View _view){
            super(_view);
            this.textView = (TextView) _view.findViewById(R.id.tvTab_IPT);
        }
    }

    public TabItem getItem(int position) {
        return mItems.get(position);
    }

    public LogisticaPagerTabsAdapter(Context _ctx, List<TabItem> _items) {
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
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_pager_tab, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        TabItem item = mItems.get(i);
        viewHolder.textView.setText(item.name);
        if (item.isSelected)
            viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.pager_tab_grey));
        else
            viewHolder.textView.setBackgroundColor(ContextCompat.getColor(mContext, R.color.transparent));
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }
}
