package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.extras.ExtrasFragment;
import com.thinkmobiles.bodega.fragments.gallery_fragments.GalleryFragment;
import com.thinkmobiles.bodega.fragments.pager_logistica.ViewPagerLogisticaFragment;
import com.thinkmobiles.bodega.fragments.pager_tiraje.ViewPagerTirajeFragment;

/**
 * Created by illia on 27.10.15.
 */
public class DescriptionFragment extends BaseFragment implements View.OnClickListener {

    private TextView tvDescription;
    private ItemWrapper mItem;
    private boolean mBottomContainerIsShown;
    private boolean mExtrasContainerIsShown;
    private boolean mImageViewIsShown;

    private LinearLayout llBottomPagerContainer;
    private LinearLayout llFragmentExtrasContainer;
    private ImageView ivLeftImage;

    public static BaseFragment newInstance(ItemWrapper _parentItem, boolean _bottomContainerIsShown,
                                           boolean _extrasContainerIsShown, boolean _imageViewIsShown) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        args.putBoolean(Constants.EXTRA_FLAG_1, _bottomContainerIsShown);
        args.putBoolean(Constants.EXTRA_FLAG_2, _extrasContainerIsShown);
        args.putBoolean(Constants.EXTRA_FLAG_3, _imageViewIsShown);
        BaseFragment fragment = new DescriptionFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_description);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItem = args.getParcelable(Constants.EXTRA_ITEM);
            mBottomContainerIsShown = args.getBoolean(Constants.EXTRA_FLAG_1);
            mExtrasContainerIsShown = args.getBoolean(Constants.EXTRA_FLAG_2);
            mImageViewIsShown = args.getBoolean(Constants.EXTRA_FLAG_3);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setRedBackground();
        findUi();
        setListeners();
        setContainers();
        setUpData();
    }

    private void findUi() {
        tvDescription = $(R.id.tvDescription_FD);
        llBottomPagerContainer = $(R.id.llBottomPagerContainer_FD);
        llFragmentExtrasContainer = $(R.id.llFragmentExtrasContainer_FD);
        ivLeftImage = $(R.id.ivLeftImage_FD);
    }

    private void setListeners() {
        $(R.id.btnVolver_FD).setOnClickListener(this);
        $(R.id.btnAddEnvio_FD).setOnClickListener(this);
    }

    private void setContainers() {
        if (mBottomContainerIsShown) {
            switch (mItem.getId()) {
                case Constants.LOGISTICA_ID:
                    showBottomPager(ViewPagerLogisticaFragment.newInstance(mItem));
                    break;
                case Constants.TANQUES_ID:
                    showBottomPager(GalleryFragment.newInstance(mItem, true, true));
                    break;
                case Constants.TIRAGE_ID:
                    showBottomPager(ViewPagerTirajeFragment.newInstance(mItem));
                    break;
            }
        }
        if (mExtrasContainerIsShown)
            showExtras(mItem);
        if (mImageViewIsShown)
            showDescriptionImage();
    }

    private void setUpData() {
        setActionBarTitle(TextUtils.isEmpty(mItem.getName()) ? "" : mItem.getName());
        String description;
        // TODO fix later
        switch (mItem.getId()) {
            case Constants.LOGISTICA_ID:
                description = mItem.getInnerLevel().get(0).getInnerLevel().get(0).getInnerLevel().get(0).getDescription();
                break;
            case Constants.TIRAGE_ID:
            case Constants.ENFIRADORES_ID:
            case Constants.COMPRESSOR_ID:
                description = mItem.getInnerLevel().get(0).getInnerLevel().get(0).getDescription();
                break;
            default:
                description = mItem.getDescription();
                break;
        }

        if (!TextUtils.isEmpty(description))
            tvDescription.setText(Html.fromHtml(description));
        else
            tvDescription.setText(getString(R.string.lorem_ipsum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVolver_FD:
                mActivity.onBackPressed();
                break;
            case R.id.btnAddEnvio_FD:
                mFragmentNavigator.showEnviosDialog(mItem);
                break;
        }
    }

    private void showBottomPager(BaseFragment _fragment) {
        llBottomPagerContainer.setVisibility(View.VISIBLE);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.llBottomPagerContainer_FD, _fragment)
                .commit();
    }

    private void showExtras(ItemWrapper _item) {
        llFragmentExtrasContainer.setVisibility(View.VISIBLE);
        getChildFragmentManager().beginTransaction()
                .replace(R.id.llFragmentExtrasContainer_FD, ExtrasFragment.newInstance(_item))
                .commit();
    }

    private void showDescriptionImage() {
        ivLeftImage.setVisibility(View.VISIBLE);
        Glide.with(getApplicationContext())
                .load(ApiManager.getPath(getApplicationContext()) + mItem.getIcon())
                .fitCenter()
                .into(ivLeftImage);
    }
}
