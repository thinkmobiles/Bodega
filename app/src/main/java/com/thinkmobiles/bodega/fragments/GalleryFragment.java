package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.GalleryRecycleAdapter;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.utils.ItemClickSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasha on 27.10.2015.
 */
public class GalleryFragment extends BaseFragment implements View.OnClickListener {

    private ItemWrapper mItemWrapper;
    private RecyclerView mRecyclerView;
    private GalleryRecycleAdapter mAdapter;
    private List<String> extralImages;
    private GridLayoutManager mGridLayoutManager;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_gallery);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
            Log.d("qqq", mItemWrapper.getName());
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        initData();
        setRedBackground();
        setUpRecycler();
        setBtnListeners();
        setListeners();
    }

    private void initData() {
        setActionBarTitle(TextUtils.isEmpty(mItemWrapper.getName()) ? "" : mItemWrapper.getName());
        extralImages = mItemWrapper.getExtraImages();
    }

    private void setBtnListeners() {
        $(R.id.btnVolver_FG).setOnClickListener(this);
        $(R.id.btnAddEnvio_FG).setOnClickListener(this);
    }

    private void findView() {
        mRecyclerView = $(R.id.rvRecycler_FG);
    }

    private void setUpRecycler() {

        mGridLayoutManager = new GridLayoutManager(mActivity.getApplicationContext(), 4);
        mAdapter = new GalleryRecycleAdapter(mActivity.getApplicationContext(), extralImages);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListeners() {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                mFragmentNavigator.showFragment(ViewGalleryFragment.newInstance(extralImages, position, false));
            }
        });
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnVolver_FG:
                mFragmentNavigator.popBackStack();
                break;
            case R.id.btnAddEnvio_FG:
                Toast.makeText(mActivity.getApplicationContext(), "Add Envio", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
