package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;

/**
 * Created by sasha on 28.10.2015.
 */
public class ViewGalleryFragment extends BaseFragment implements View.OnClickListener {

    private boolean mTopBarIsShown;

    private ItemWrapper mItemWrapper;
    private ImageView mImageView;
    private FrameLayout flTopBarButtonsContainer;


    public static BaseFragment newInstance(ItemWrapper _parentItem, boolean _topBarIsShown) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        args.putBoolean(Constants.EXTRA_FLAG_1, _topBarIsShown);
        BaseFragment fragment = new ViewGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_gallery);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
            mTopBarIsShown = args.getBoolean(Constants.EXTRA_FLAG_1);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        setBtnListeners();
        setContainers();
    }

    private void setContainers() {
        if (mTopBarIsShown)
            flTopBarButtonsContainer.setVisibility(View.VISIBLE);
    }

    private void setBtnListeners() {
        $(R.id.btn_close_FVG).setOnClickListener(this);
        $(R.id.btn_previous_image_FVG).setOnClickListener(this);
        $(R.id.btn_next_image_FVG).setOnClickListener(this);
        $(R.id.btnVolver_FVG).setOnClickListener(this);
        $(R.id.btnAddEnvio_FVG).setOnClickListener(this);
    }

    private void findView() {
        mImageView = $(R.id.iv_image_FVG);
        flTopBarButtonsContainer = $(R.id.flButtonsContainer_FVG);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btnVolver_FVG:
                mFragmentNavigator.popBackStack();
                break;
            case R.id.btnAddEnvio_FVG:
                Toast.makeText(mActivity.getApplicationContext(), "Add Envio", Toast.LENGTH_SHORT).show();
                break;
            case R.id.btn_close_FVG:
            break;
            case R.id.btn_previous_image_FVG:
            break;
            case R.id.btn_next_image_FVG:
            break;
        }
    }
}
