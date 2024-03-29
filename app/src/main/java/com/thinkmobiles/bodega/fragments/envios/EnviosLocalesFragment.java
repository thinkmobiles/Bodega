package com.thinkmobiles.bodega.fragments.envios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.EnviosLocalesAdapter;
import com.thinkmobiles.bodega.adapters.EnviosProductsAdapter;
import com.thinkmobiles.bodega.db.DBManager;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by denis on 30.10.15.
 */
public class EnviosLocalesFragment extends BaseFragment implements View.OnClickListener {

    // UI
    private RecyclerView rvLocales;
    private TextView tvDelete;

    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        BaseFragment fragment = new EnviosLocalesFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envios_locales);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setActionBarTitle(getString(R.string.envios_caps));
        setRedBackground();
        findUI();
        setListeners();
        initRecyclerView();
    }

    private void findUI() {
        rvLocales = $(R.id.rvEnvios_FE);
        tvDelete = $(R.id.tvVaciar_IE);
    }

    private void initRecyclerView() {
        EnviosLocalesAdapter mAdapter = new EnviosLocalesAdapter(this);
        mAdapter.setData(DBManager.getInstance(getApplicationContext()).getCustomers());
        rvLocales.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvLocales.setItemAnimator(new DefaultItemAnimator());
        rvLocales.setAdapter(mAdapter);
    }

    private void setListeners() {
        tvDelete.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tvVaciar_IE:
                DBManager dbManager = DBManager.getInstance(getApplicationContext());
                dbManager.deleteAllCustomers();
                ((EnviosLocalesAdapter) rvLocales.getAdapter()).setData(dbManager.getCustomers());
                break;
        }
    }

}