package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.thinkmobiles.bodega.R;

/**
 * Created by denis on 30.10.15.
 */
public class EnviosLocalesFragment extends BaseFragment {

    public static EnviosLocalesFragment newInstance() {
        return new EnviosLocalesFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_envios);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setActionBarTitle(getString(R.string.envios_caps));
    }

    private void findUI() {
        
    }

}