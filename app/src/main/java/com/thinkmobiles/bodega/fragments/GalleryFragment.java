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
import android.support.v7.widget.LinearLayoutManager;

import com.thinkmobiles.bodega.adapters.LogosRecyclerAdapter;
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
    private RecyclerView.Adapter mAdapter;
    private List<String> mExtralImages;
    private List<ItemWrapper> mInerLevel;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean horizontal;

    public static BaseFragment newInstance(ItemWrapper _parentItem, boolean _horizontal) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        args.putBoolean(Constants.EXTRA_FLAG_1, _horizontal);
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
            mItemWrapper = args.getParcelable(Constants.EXTRA_ITEM);
            Log.d("qqq", mItemWrapper.getName());
            horizontal = args.getBoolean(Constants.EXTRA_FLAG_1);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        initData();
        setRedBackground();
        serLayoutManager();
        setRecyclerAdapter();
        setUpRecycler();
        setBtnListeners();
        setListeners();
    }

    private void initData() {
        setActionBarTitle(TextUtils.isEmpty(mItemWrapper.getName()) ? "" : mItemWrapper.getName());
        mExtralImages = mItemWrapper.getExtraImages();
        mInerLevel = mItemWrapper.getInnerLevel();

    }

    private void setBtnListeners() {
        $(R.id.btnVolver_FG).setOnClickListener(this);
        $(R.id.btnAddEnvio_FG).setOnClickListener(this);
    }

    private void findView() {
        mRecyclerView = $(R.id.rvRecycler_FG);
    }

    private void serLayoutManager() {
        if (!horizontal) {
            mLayoutManager = new GridLayoutManager(mActivity.getApplicationContext(), 4);
                    } else {
            mLayoutManager = new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL,false);
        }
    }

    private void setRecyclerAdapter() {
        if (!horizontal) {
            if (mExtralImages != null)
            mAdapter = new GalleryRecycleAdapter(mActivity.getApplicationContext(), mExtralImages);
        } else {
            mAdapter = new LogosRecyclerAdapter(mActivity.getApplicationContext(), mInerLevel);
        }

    }

    private void setUpRecycler() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListeners() {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                if (!horizontal)
                mFragmentNavigator.showFragment(ViewGalleryFragment.newInstance(mExtralImages, position, false));
                else {
                    mExtralImages = new ArrayList<String>();
                    for (ItemWrapper itemWrapper : mInerLevel) {
                        mExtralImages.add(itemWrapper.getProductList().get(0).getImageSmall());
                    }
                    mFragmentNavigator.showFragment(ViewGalleryFragment.newInstance(mExtralImages, position, false));
                }
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
