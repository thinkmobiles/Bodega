package com.thinkmobiles.bodega.fragments.envios;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by denis on 03.11.15.
 */
public class AddToEnviosFragment extends BaseFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_to_envios, container, false);
    }
}
