package com.thinkmobiles.bodega.fragments.gallery_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.ItemGalleryPagerAdapter;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sasha on 28.10.2015.
 */
public class ViewGalleryPagerFragment extends BaseFragment implements View.OnClickListener, ViewPager.OnPageChangeListener {

    private boolean mTopBarIsShown;

    private List<String> mImageList, mNamesList, mInformationList;
    private ItemWrapper mItemWrapper;
    private int position;
    private FrameLayout flTopBarButtonsContainer;
    private ImageButton ibPrev, ibNext, ibClose, ibPrevV, ibNextV, ibCloseV;
    private ViewPager mViewPager;
    private ItemGalleryPagerAdapter mAdapter;


    public static BaseFragment newInstance(ItemWrapper _itemWrapper, int _position, boolean _topBarIsShown) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _itemWrapper);
        args.putInt(Constants.EXTRA_FLAG_1, _position);
        args.putBoolean(Constants.EXTRA_FLAG_2, _topBarIsShown);
        BaseFragment fragment = new ViewGalleryPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = (ItemWrapper) args.getParcelable(Constants.EXTRA_ITEM);
            position = args.getInt(Constants.EXTRA_FLAG_1);
            mTopBarIsShown = args.getBoolean(Constants.EXTRA_FLAG_2);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_gallery);
        checkArgument();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        findView();
        setRedBackground();
        setVisibilityArrows();
        setBtnListeners();
        setContainers();
        setupPager();
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
                mImageList = getImagesFromGalleryImages();
                break;

            case Constants.TANQUES_ID:
            case Constants.LOGOTIPOS_ID:
            case Constants.ARTICULOS_DE_USO_ID:
                createListsLogo();
                break;

            case Constants.CON_VOLUMEN_ID:
            case Constants.LONA_ID:
            case Constants.LEYENDA_ID:
            case Constants.SPRAY_ID:
            case Constants.TEXTIL_ID:
            case Constants.TELA_DE_SACO_ID:
            case Constants.PAPEL_PINTADO_ID:
                mImageList = getImagesFromOptionsImages();
                break;
            case Constants.LOGISTICA_ID:
                createListsLogistica();
                break;
        }
    }

    private void findView() {
        flTopBarButtonsContainer = $(R.id.flButtonsContainer_FVG);
        mViewPager = $(R.id.vp_item_gallery_FVG);
        ibPrev = $(R.id.btn_previous_image_FVG);
        ibNext = $(R.id.btn_next_image_FVG);
        ibClose = $(R.id.btn_close_FVG);
        ibPrevV = $(R.id.btn_previous_image_view_FVG);
        ibNextV = $(R.id.btn_next_image_view_FVG);
        ibCloseV = $(R.id.btn_close_view_FVG);
    }

    private void setContainers() {
        if (mTopBarIsShown) {
            flTopBarButtonsContainer.setVisibility(View.VISIBLE);
            ibClose.setVisibility(View.GONE);
            ibCloseV.setVisibility(View.GONE);
        }
    }

    private void setBtnListeners() {
        if (mItemWrapper.getId().equals(Constants.LOGOTIPOS_ID)) {
            flTopBarButtonsContainer.setVisibility(View.VISIBLE);
            ibClose.setVisibility(View.INVISIBLE);
            ibCloseV.setVisibility(View.INVISIBLE);
            $(R.id.btnVolver_FVG).setOnClickListener(this);
            $(R.id.btnAddEnvio_FVG).setOnClickListener(this);
        }
        ibClose.setOnClickListener(this);
        ibPrev.setOnClickListener(this);
        ibNext.setOnClickListener(this);
    }

    private void setupPager() {
        mAdapter = new ItemGalleryPagerAdapter(getChildFragmentManager());
        mAdapter.setData(mImageList, mNamesList, mInformationList, mItemWrapper.getId());
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(this);
        mViewPager.setCurrentItem(position);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnVolver_FVG:
                mActivity.onBackPressed();
                break;
            case R.id.btnAddEnvio_FVG:
                mFragmentNavigator.showEnviosDialog(mItemWrapper);
                break;
            case R.id.btn_close_FVG:
                mActivity.onBackPressed();
                break;
            case R.id.btn_previous_image_FVG:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
                break;
            case R.id.btn_next_image_FVG:
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                break;
        }
    }

    private void setVisibilityArrows() {
        if (mImageList.size() == 1) {
            ibNext.setVisibility(View.GONE);
            ibPrev.setVisibility(View.GONE);
            ibNextV.setVisibility(View.GONE);
            ibPrevV.setVisibility(View.GONE);
        } else if (mViewPager.getCurrentItem() == 0) {
            ibNext.setVisibility(View.VISIBLE);
            ibPrev.setVisibility(View.GONE);
            ibNextV.setVisibility(View.VISIBLE);
            ibPrevV.setVisibility(View.GONE);
        } else if (mViewPager.getCurrentItem() + 1 == mImageList.size()) {
            ibPrev.setVisibility(View.VISIBLE);
            ibNext.setVisibility(View.GONE);
            ibPrevV.setVisibility(View.VISIBLE);
            ibNextV.setVisibility(View.GONE);
        } else {
            ibPrev.setVisibility(View.VISIBLE);
            ibNext.setVisibility(View.VISIBLE);
            ibPrevV.setVisibility(View.VISIBLE);
            ibNextV.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageSelected(int position) {
        setVisibilityArrows();
        this.position = position;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    private List<String> getImagesFromOptionsImages() {
        return mItemWrapper.getInnerLevel().get(0).getProductList().get(0).getOptionsImages();
    }

    private List<String> getImagesFromGalleryImages() {
        return mItemWrapper.getInnerLevel().get(0).getProductList().get(0).getGalleriesImages();
    }

    private void createListsLogistica() {
        mImageList = new ArrayList<String>();
        mNamesList = new ArrayList<String>();
        mInformationList = new ArrayList<String>();
        for (ProductWrapper productWrapper : mItemWrapper.getProductList()) {
            //TODO correct when updated information
            mImageList.add(productWrapper.getImage());
//            mNamesList.add(" \n");
//            mInformationList.add(" \n");
        }
    }

    private void createListsLogo() {
        mImageList = new ArrayList<String>();
        mNamesList = new ArrayList<String>();
        mInformationList = new ArrayList<String>();
        for (ItemWrapper itemWrapper : mItemWrapper.getInnerLevel()) {
            mImageList.add(itemWrapper.getProductList().get(0).getImageSmall());
            mNamesList.add(itemWrapper.getProductList().get(0).getName());
            mInformationList.add(itemWrapper.getInnerLevel().get(0).getDescription());
        }
    }
}
