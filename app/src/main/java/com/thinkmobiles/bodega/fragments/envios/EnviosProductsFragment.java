package com.thinkmobiles.bodega.fragments.envios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.EnviosProductsAdapter;
import com.thinkmobiles.bodega.db.DBManager;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.utils.PDFSender;

/**
 * Created by denis on 30.10.15.
 */
public class EnviosProductsFragment extends BaseFragment implements View.OnClickListener {

    // UI
    private RecyclerView rvLocales;
    private TextView btnDelete;
    private TextView btnBack;
    private Button btnEnviar;

    private long mCustomerId;

    public static BaseFragment newInstance(long _customerId) {
        Bundle args = new Bundle();
        args.putLong(Constants.EXTRA_ID, _customerId);
        BaseFragment fragment = new EnviosProductsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envios_products);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mCustomerId = args.getLong(Constants.EXTRA_ID);
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findUI();
        setListeners();
        initRecyclerView();

        setRedBackground();
        String title = DBManager.getInstance(getApplicationContext()).getCustomerById(mCustomerId).getName();
        setActionBarTitle(title);
    }

    private void findUI() {
        rvLocales = $(R.id.rvEnvios_FE);
        btnDelete = $(R.id.tvVaciar_IE);
        btnBack = $(R.id.tvVolver_FEP);
        btnEnviar = $(R.id.btnEnviar_FE);
    }

    private void initRecyclerView() {
        EnviosProductsAdapter mAdapter = new EnviosProductsAdapter(getApplicationContext());
        mAdapter.setData(DBManager.getInstance(getApplicationContext()).getOrders(mCustomerId));
        rvLocales.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocales.setItemAnimator(new DefaultItemAnimator());
        rvLocales.setAdapter(mAdapter);
    }

    private void setListeners() {
        btnDelete.setOnClickListener(this);
        btnBack.setOnClickListener(this);
        btnEnviar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvVaciar_IE:
                DBManager dbManager = DBManager.getInstance(getApplicationContext());
                dbManager.deleteAllOrders(mCustomerId);
                ((EnviosProductsAdapter)rvLocales.getAdapter()).setData(dbManager.getOrders(mCustomerId));
                break;
            case R.id.tvVolver_FEP:
                mFragmentNavigator.popBackStack();
                break;
            case R.id.btnEnviar_FE:
                PDFSender.sendPDFsFromEnvio(getActivity(), mCustomerId);
                break;
        }
    }
}