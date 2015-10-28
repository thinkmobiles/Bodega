package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;

/**
 * Created by sasha on 28.10.2015.
 */
public class ViewGalleryFragment extends BaseFragment implements View.OnClickListener {

    private ItemWrapper mItemWrapper;
    private ImageView mImageView;

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
        setContentView(R.layout.fragment_view_gallery);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        setBtnListeners();
    }

    private void setBtnListeners() {
        $(R.id.btn_close_FVG).setOnClickListener(this);
        $(R.id.btn_previous_image_FVG).setOnClickListener(this);
        $(R.id.btn_next_image_FVG).setOnClickListener(this);
    }

    private void findView() {
        mImageView = $(R.id.iv_image_FVG);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_close_FVG:
                mActivity.onBackPressed();
                break;
            case R.id.btn_previous_image_FVG:
                break;
            case R.id.btn_next_image_FVG:
                break;
        }
    }
}
