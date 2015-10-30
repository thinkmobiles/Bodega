package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.extras.ExtrasFragment;
import com.thinkmobiles.bodega.fragments.pager_logistica.ViewPagerLogisticaFragment;

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
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
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
            mItem = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
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
            llBottomPagerContainer.setVisibility(View.VISIBLE);
            switch (mItem.getId()) {
                case Constants.LOGISTICA_ID:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.llBottomPagerContainer_FD, ViewPagerLogisticaFragment.newInstance(mItem))
                            .commit();
                    break;
            }
        }
        if (mExtrasContainerIsShown)
            llFragmentExtrasContainer.setVisibility(View.VISIBLE);
            switch (mItem.getId()) {
                case Constants.LOGISTICA_ID:
                    getFragmentManager().beginTransaction()
                            .replace(R.id.llFragmentExtrasContainer_FD, ExtrasFragment.newInstance(mItem))
                            .commit();
                    break;
            }
        if (mImageViewIsShown)
            ivLeftImage.setVisibility(View.VISIBLE);
    }

    private void setUpData() {
        setActionBarTitle(TextUtils.isEmpty(mItem.getName()) ? "" : mItem.getName());
//        String description = mItem.getDescription();
        // TODO fix later
        String description = mItem.getInnerLevel()
                .get(0)
                .getInnerLevel()
                .get(0)
                .getInnerLevel()
                .get(0)
                .getDescription();
        if (!TextUtils.isEmpty(description))
            tvDescription.setText(Html.fromHtml(description));
        else
            tvDescription.setText(getString(R.string.lorem_ipsum));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnVolver_FD:
                mFragmentNavigator.popBackStack();
                break;
            case R.id.btnAddEnvio_FD:
                Toast.makeText(mActivity.getApplicationContext(), "Add Envio", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
