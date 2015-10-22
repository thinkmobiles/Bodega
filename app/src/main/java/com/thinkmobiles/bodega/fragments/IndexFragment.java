package com.thinkmobiles.bodega.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.thinkmobiles.bodega.R;

/**
 * Created by illia on 22.10.15.
 */
public class IndexFragment extends BaseFragment {

    private ImageView ivCalidad;
    private ImageView ivLogistica;
    private ImageView ivInstalacion;
    private ImageView ivImagen;
    private ImageView ivImplantacion;
    private ImageView ivPlv;

    public static BaseFragment newInstance() {
        return new IndexFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_index);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        mAdapter = new MyInsuranceCardAdapter(mActivity);
        findView();
        setUpIcons();
//        initActionBar();
//        initListeners();
//        downloadData();
//        setUpList();
    }

    private void findView() {
        ivCalidad       = $(R.id.ivCalidad_FI);
        ivLogistica     = $(R.id.ivLogistica_FI);
        ivInstalacion   = $(R.id.ivInstalacion_FI);
        ivImagen        = $(R.id.ivImagen_FI);
        ivImplantacion  = $(R.id.ivImplantacion_FI);
        ivPlv           = $(R.id.ivPlv_FI);
    }

    private void setUpIcons() {

    }
}
