package com.thinkmobiles.bodega.fragments.envios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by denis on 30.10.15.
 */
public class EnviosLocalesFragment extends BaseFragment implements View.OnClickListener {


    public static BaseFragment newInstance() {
        Bundle args = new Bundle();
        BaseFragment fragment = new AddToEnviosFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envios);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setActionBarTitle(getString(R.string.envios_caps));
        setRedBackground();
        findUI();
        setListeners();
    }

    private void setListeners() {
    }


    private void findUI() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

        }
    }

}