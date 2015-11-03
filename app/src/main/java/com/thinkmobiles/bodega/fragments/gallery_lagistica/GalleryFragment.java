package com.thinkmobiles.bodega.fragments.gallery_lagistica;

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
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.utils.ItemClickSupport;

import java.util.List;

/**
 * Created by sasha on 27.10.2015.
 */
public class GalleryFragment extends BaseFragment implements View.OnClickListener {

    private ItemWrapper mItemWrapper;
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private List<String> mImageList;
    private List<ItemWrapper> mInnerLevel;
    private RecyclerView.LayoutManager mLayoutManager;
    private boolean horizontal, isInContainer;

    public static BaseFragment newInstance(ItemWrapper _parentItem, boolean _horizontal, boolean _isInContainer) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        args.putBoolean(Constants.EXTRA_FLAG_1, _horizontal);
        args.putBoolean(Constants.EXTRA_FLAG_2, _isInContainer);
        BaseFragment fragment = new GalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = args.getParcelable(Constants.EXTRA_ITEM);
            Log.d("qqq", mItemWrapper.getName());
            horizontal = args.getBoolean(Constants.EXTRA_FLAG_1);
            isInContainer = args.getBoolean(Constants.EXTRA_FLAG_2);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkArgument();
        if (isInContainer) {
            setContentView(R.layout.fragment_gallery_description);
        } else {
            setContentView(R.layout.fragment_gallery);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        initData();
        setBtnListeners();
        setRedBackground();
        serLayoutManager();
        setRecyclerAdapter();
        setUpRecycler();
        setListeners();
    }

    private void initData() {
        setActionBarTitle(TextUtils.isEmpty(mItemWrapper.getName()) ? "" : mItemWrapper.getName());
        switch (mItemWrapper.getId()) {
            case Constants.EJEMPLOS_ID:
                mImageList = getImagesFromGalleriesImages();
                break;
            case Constants.CON_VOLUMEN_ID:
            case Constants.LONA_ID:
            case Constants.SPRAY_ID:
            case Constants.TEXTIL_ID:
            case Constants.TELA_DE_SACO_ID:
            case Constants.LEYENDA_ID:
            case Constants.PAPEL_PINTADO_ID:
                mImageList = getImagesFromOptionsImages();
                break;
        }
        mInnerLevel = mItemWrapper.getInnerLevel();
    }

    private void setBtnListeners() {
        if (!isInContainer) {
            $(R.id.btnVolver_FG).setOnClickListener(this);
            $(R.id.btnAddEnvio_FG).setOnClickListener(this);
        }
    }

    private void findView() {
        mRecyclerView = $(R.id.rvRecycler_FG);
    }

    private void serLayoutManager() {
        if (!horizontal) {
            mLayoutManager = new GridLayoutManager(mActivity.getApplicationContext(), 4);
        } else {
            mLayoutManager = new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        }
    }

    private void setRecyclerAdapter() {
        if (!horizontal) {
            if (mImageList != null)
                mAdapter = new GalleryRecycleAdapter(mActivity.getApplicationContext(), mImageList);
        } else {
            mAdapter = new LogosRecyclerAdapter(mActivity.getApplicationContext(), mInnerLevel);
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
                mFragmentNavigator.showFragment(ViewGalleryFragment.newInstance(mItemWrapper, position, false));
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

    public List<String> getImagesFromOptionsImages() {
        return mItemWrapper.getInnerLevel().get(0).getProductList().get(0).getOptionsImages();
    }

    public List<String> getImagesFromGalleriesImages() {
        return mItemWrapper.getInnerLevel().get(0).getProductList().get(0).getGalleriesImages();
    }
}