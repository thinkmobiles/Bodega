package com.thinkmobiles.bodega.fragments.gallery_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.GalleryRecycleAdapter;

import android.support.v7.widget.LinearLayoutManager;

import com.thinkmobiles.bodega.adapters.LevelTwoLinearLayoutManager;
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
    private ImageButton ibPrev, ibNext;

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
            case Constants.GALERIA_DE_ACERO_ID:
            case Constants.GALERIA_DE_COBRE_ID:
            case Constants.FICHA_TACNICA_DE_COLUMNAS_ID:
            case Constants.FICHA_TACNICA_DE_BANDEJAS_ID:
            case Constants.TOLDOS_ID:
            case Constants.VINILIS_ID:
            case Constants.GRAFICAS_ID:
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
        } else {
            ibNext.setOnClickListener(this);
            ibPrev.setOnClickListener(this);
        }
    }

    private void findView() {
        mRecyclerView = $(R.id.rvRecycler_FG);
        if (isInContainer) {
            ibNext = $(R.id.btn_next_FGD);
            ibPrev = $(R.id.btn_prev_FGD);
        }
    }

    private void serLayoutManager() {
        if (!horizontal) {
            switch (mItemWrapper.getId()) {
                case Constants.TOLDOS_ID:
                case Constants.VINILIS_ID:
                case Constants.GRAFICAS_ID:
                    mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                    break;
                default:
                    mLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
                    break;
            }
        } else {
//            mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
            mLayoutManager = new LevelTwoLinearLayoutManager(getApplicationContext(), LevelTwoLinearLayoutManager.HORIZONTAL, false);
        }
    }

    private void setRecyclerAdapter() {
        if (!horizontal) {
            if (mImageList != null)
                mAdapter = new GalleryRecycleAdapter(getApplicationContext(), mImageList);
        } else {
            if (isInContainer) {
                mAdapter = new LogosRecyclerAdapter(getApplicationContext(), mInnerLevel, true);
            } else {
                mAdapter = new LogosRecyclerAdapter(getApplicationContext(), mInnerLevel, false);
            }
        }
    }

    private void setUpRecycler() {

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        if (isInContainer) {
            mRecyclerView.addOnScrollListener(new OnScrollListener() {
                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    setVisibilityArrows();
                }
            });
        }
    }

    private void setListeners() {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                switch (mItemWrapper.getId()) {
                    case Constants.DIBOND_ID:
                    case Constants.AZULEJO_ID:
                    case Constants.MESA_VUELTA_ID:
                    case Constants.COLONIAL_ID:
                    case Constants.LEYENDA1_ID:
                    case Constants.CAJA_DE_CERVEZAS_ID:
                    case Constants.PALET_ID:
                    case Constants.CHOPO_ID:
                    case Constants.TELA_DE_SACO1_ID:
                    case Constants.HAMACA_ID:
                    case Constants.LONA_MICROPERFORADA_ID:
                    case Constants.ARTICULOS_DE_USO_ID:
                        mFragmentNavigator.showFragment(ViewGalleryInfoFragment.newInstance(mItemWrapper, position));
                        break;
                    default:
                        mFragmentNavigator.showFragment(ViewGalleryPagerFragment.newInstance(mItemWrapper, position, false));
                        break;
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
                mFragmentNavigator.showEnviosDialog(mItemWrapper);
                //Toast.makeText(mActivity.getApplicationContext(), "Add Envio", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_next_FGD:
                scrollRecyclerView(true);
                break;
            case R.id.btn_prev_FGD:
                scrollRecyclerView(false);
                break;
        }
    }

    private void scrollRecyclerView(boolean _scrollNext) {
        LinearLayoutManager llm = (LinearLayoutManager) mLayoutManager;
        if (_scrollNext)
            mRecyclerView.smoothScrollToPosition(llm.findLastVisibleItemPosition() + 1);
        else mRecyclerView.smoothScrollToPosition(llm.findFirstVisibleItemPosition() - 1);
        setVisibilityArrows();
    }

    public List<String> getImagesFromOptionsImages() {
        return mItemWrapper.getInnerLevel().get(0).getProductList().get(0).getOptionsImages();
    }

    public List<String> getImagesFromGalleriesImages() {
        return mItemWrapper.getInnerLevel().get(0).getProductList().get(0).getGalleriesImages();
    }

    private void setVisibilityArrows() {
        LinearLayoutManager llm = (LinearLayoutManager) mLayoutManager;

        if (llm.findFirstVisibleItemPosition() == 0) {
            ibNext.setVisibility(View.VISIBLE);
            ibPrev.setVisibility(View.INVISIBLE);
        } else if (llm.findLastVisibleItemPosition() == mInnerLevel.size() - 1) {
            ibPrev.setVisibility(View.VISIBLE);
            ibNext.setVisibility(View.INVISIBLE);
        } else {
            ibPrev.setVisibility(View.VISIBLE);
            ibNext.setVisibility(View.VISIBLE);
        }
    }
}
