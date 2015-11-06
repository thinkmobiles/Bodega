package com.thinkmobiles.bodega.fragments.gallery_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;


/**
 * Created by sasha on 04.11.2015.
 */
public class ViewGalleryInfoFragment extends BaseFragment implements View.OnClickListener{

    private ItemWrapper mItemWrapper;
    private int mPosition;
    private ImageView ivSmallImage, ivBigImage;
    private TextView tvInformation;
    private String mSmalImage, mBigImage, mInformation;

    public static BaseFragment newInstance(ItemWrapper _parentItem, int _position) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        args.putInt(Constants.EXTRA_FLAG_1, _position);
        BaseFragment fragment = new ViewGalleryInfoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = (ItemWrapper) args.getParcelable(Constants.EXTRA_ITEM);
            mPosition = args.getInt(Constants.EXTRA_FLAG_1);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_info);
        checkArgument();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        findView();
        setBtnListeners();
        setUpData();
    }

    private void setUpData() {
        Glide.with(getApplicationContext())
                .load(ApiManager.getPath(getApplicationContext()) + mSmalImage)
                .fitCenter()
                .into(ivSmallImage);

        Glide.with(getApplicationContext())
                .load(ApiManager.getPath(getApplicationContext()) + mBigImage)
                .fitCenter()
                .into(ivBigImage);

        tvInformation.setText(mInformation);
    }

    private void initData() {
        mSmalImage = mItemWrapper.getInnerLevel().get(mPosition).getProductList().get(0).getImage();
        mBigImage = mItemWrapper.getInnerLevel().get(mPosition).getProductList().get(0).getImageSmall();
        mInformation = mItemWrapper.getInnerLevel().get(mPosition).getDescription();
    }

    private void setBtnListeners() {
        $(R.id.btn_close_FVI).setOnClickListener(this);
    }

    private void findView() {
        ivBigImage = $(R.id.ivBigImage_FVI);
        ivSmallImage = $(R.id.iv_small_image_FVI);
        tvInformation =$(R.id.tv_info_FVI);
    }

    @Override
    public void onClick(View v) {
        mFragmentNavigator.popBackStack();
    }
}
