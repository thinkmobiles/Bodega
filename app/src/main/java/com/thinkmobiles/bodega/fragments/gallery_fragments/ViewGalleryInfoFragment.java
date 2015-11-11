package com.thinkmobiles.bodega.fragments.gallery_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageButton;
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
    private ImageButton ibAddEnvio;
    private WebView tvInformation;
   private TextView tvName;
    private String mSmallImage, mBigImage, mInformation, mName;

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
                .load(ApiManager.getPath(getApplicationContext()) + mBigImage)
                .fitCenter()
                .into(ivBigImage);

        tvInformation.loadData(mInformation, "text/html", "utf-8");

        if(mItemWrapper.getId().equals(Constants.ARTICULOS_DE_USO_ID)) {
            ibAddEnvio.setVisibility(View.VISIBLE);
            ivSmallImage.setVisibility(View.GONE);
        } else {
            Glide.with(getApplicationContext())
                    .load(ApiManager.getPath(getApplicationContext()) + mSmallImage)
                    .fitCenter()
                    .into(ivSmallImage);
        }
        Log.d("qqq",mItemWrapper.getId());
        if (mItemWrapper.getId().equals(Constants.ARTICULOS_DE_USO_ID)){
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(mName);
        }
    }

    private void initData() {
        setActionBarTitle(TextUtils.isEmpty(mItemWrapper.getName()) ? "" : mItemWrapper.getName());
        mSmallImage = mItemWrapper.getInnerLevel().get(mPosition).getProductList().get(0).getImage();
        mBigImage = mItemWrapper.getInnerLevel().get(mPosition).getProductList().get(0).getImageSmall();
        mInformation = mItemWrapper.getInnerLevel().get(mPosition).getProductList().get(0).getDescription();
        mName = mItemWrapper.getInnerLevel().get(mPosition).getProductList().get(0).getName();
//                if (mInformation==null||mInformation.equals(""))
//            mInformation = mItemWrapper.getInnerLevel().get(mPosition).getInnerLevel().get(0).getProductList().get(0).getPackaging().get(0);
    }

    private void setBtnListeners() {
        $(R.id.btn_close_FVI).setOnClickListener(this);
        ibAddEnvio.setOnClickListener(this);
    }

    private void findView() {
        ivBigImage = $(R.id.ivBigImage_FVI);
        ivSmallImage = $(R.id.iv_small_image_FVI);
        tvInformation =$(R.id.wv_info_FVI);
        ibAddEnvio=$(R.id.btnAddEnvio_FVI);
        tvName = $ (R.id.tv_name_FVI);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_close_FVI:
                mActivity.onBackPressed();
                break;
            case R.id.btnAddEnvio_FVI:
                mFragmentNavigator.showEnviosDialog(mItemWrapper);
                break;
        }
    }
}
