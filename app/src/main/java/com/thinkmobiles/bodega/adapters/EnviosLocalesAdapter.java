package com.thinkmobiles.bodega.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.db.DBManager;
import com.thinkmobiles.bodega.db.daogen.Customer;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.fragments.envios.EnviosProductsFragment;
import com.thinkmobiles.bodega.utils.PDFSender;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 04.11.15.
 */
public class EnviosLocalesAdapter extends RecyclerView.Adapter<EnviosLocalesAdapter.LocalesViewHolder> {

    private List<Customer> mData = new ArrayList<>();
    private BaseFragment mBaseFragment;

    public EnviosLocalesAdapter(BaseFragment _baseFragment) {
        mBaseFragment = _baseFragment;
    }

    public void setData(List<Customer> _data) {
        if (mData != null) {
            this.mData = _data;
            notifyDataSetChanged();
        }
    }

    public List<Customer> getData() {
        return mData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public static class LocalesViewHolder extends RecyclerView.ViewHolder {

        private TextView title;
        private ImageButton btnDelete, btnView, btnSend;
        private RelativeLayout rlRoot;

        public LocalesViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.tvItemName_IEL);
            btnDelete = (ImageButton) itemView.findViewById(R.id.btnDeleteItem_IEL);
            btnView = (ImageButton) itemView.findViewById(R.id.btnViewItem_IEL);
            btnSend = (ImageButton) itemView.findViewById(R.id.btnSendItem_IEL);
            rlRoot = (RelativeLayout) itemView.findViewById(R.id.rlRootContainer_IEL);
        }
    }

    @Override
    public EnviosLocalesAdapter.LocalesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalesViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_envios_locale, parent, false));
    }

    @Override
    public void onBindViewHolder(LocalesViewHolder holder, int position) {
        Customer customer = mData.get(position);
        holder.title.setText(customer.getName());
        OnItemClickListener onItemClickListener = new OnItemClickListener(customer);
        holder.rlRoot.setOnClickListener(onItemClickListener);
        holder.btnDelete.setOnClickListener(onItemClickListener);
        holder.btnSend.setOnClickListener(onItemClickListener);
        holder.btnView.setOnClickListener(onItemClickListener);
    }

    private class OnItemClickListener implements View.OnClickListener {

        private Customer customer;

        public OnItemClickListener(Customer customer) {
            this.customer = customer;
        }

        @Override
        public void onClick(View v) {
            DBManager dbManager = DBManager.getInstance(mBaseFragment.getContext());
            switch (v.getId()) {
                case R.id.btnDeleteItem_IEL:
                    int index = mData.indexOf(customer);
                    dbManager.deleteCustomer(customer);
                    mData.remove(index);
                    notifyItemRemoved(index);
                    break;
                case R.id.btnViewItem_IEL:
                case R.id.rlRootContainer_IEL:
                    mBaseFragment.getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.container, EnviosProductsFragment.newInstance(customer.getId()))
                            .addToBackStack(EnviosLocalesAdapter.class.getSimpleName())
                            .commit();
                    break;
                case R.id.btnSendItem_IEL:
                    PDFSender.sendPDFsFromEnvio(mBaseFragment.getActivity(), customer.getId());
                    break;
            }
        }
    }
}
