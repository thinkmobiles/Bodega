package com.thinkmobiles.bodega.adapters;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cristaliza.mvc.models.estrella.Product;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.db.DBManager;
import com.thinkmobiles.bodega.db.daogen.Customer;
import com.thinkmobiles.bodega.db.daogen.OrderItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 04.11.15.
 */
public class EnviosProductsAdapter extends RecyclerView.Adapter<EnviosProductsAdapter.LocalesViewHolder> {

    private List<OrderItem> mData = new ArrayList<>();
    private Context mContext;

    public EnviosProductsAdapter(Context _context) {
        mContext = _context;
    }

    public void setData(List<OrderItem> mData) {
        if (mData != null) {
            this.mData = mData;
            notifyDataSetChanged();
        }
    }

    public List<OrderItem> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class LocalesViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageButton btnDelete;
        private ImageView ivIcon;

        public LocalesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvItemName_IEP);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteItem_IEP);
            ivIcon = (ImageView) itemView.findViewById(R.id.ivItemLogo_IEP);
        }
    }

    @Override
    public EnviosProductsAdapter.LocalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_envios_product, parent, false));
    }

    @Override
    public void onBindViewHolder(LocalesViewHolder holder, int position) {
        OrderItem order = mData.get(position);
        String title = order.getName();
        holder.title.setText(title);
        OnItemClickListener onItemClickListener = new OnItemClickListener(order);
        holder.btnDelete.setOnClickListener(onItemClickListener);
        String path = ApiManager.getPath(mContext) + order.getIcon();
        Glide.with(holder.ivIcon.getContext()).load(path).fitCenter().into(holder.ivIcon);
    }

    private class OnItemClickListener implements View.OnClickListener {

        private OrderItem orderItem;

        public OnItemClickListener(OrderItem orderItem) {
            this.orderItem = orderItem;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnDeleteItem_IEP:
                    DBManager.getInstance(mContext).deleteOrder(orderItem);
                    int index = mData.indexOf(orderItem);
                    mData.remove(index);
                    notifyItemRemoved(index);
                    break;
            }
        }
    }
}
