package com.thinkmobiles.bodega.fragments.gallery_fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by sasha on 29.10.2015.
 */
public class PagerItemGalleryFragment extends BaseFragment {

    private TextView tvName;
    private WebView wvInformation;
    private ImageView ivImage;

    private String mImageItem;
    private String mName;
    private String mInformation;

    public static BaseFragment newInstance(String _image, String _name, String _information) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_FLAG_1, _image);
        args.putString(Constants.EXTRA_FLAG_2, _name);
        args.putString(Constants.EXTRA_FLAG_3, _information);
        BaseFragment fragment = new PagerItemGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mImageItem = args.getString(Constants.EXTRA_FLAG_1);
            mName = args.getString(Constants.EXTRA_FLAG_2);
            mInformation = args.getString(Constants.EXTRA_FLAG_3);
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_item_galiery);
        checkArgument();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        setData();
    }

    private void findView() {
        tvName = $(R.id.tv_name_FVPIG);
        ivImage = $(R.id.iv_image_FVPIG);
        wvInformation = $(R.id.wv_information_FVPIG);

    }

    private void setData() {

        Glide.with(getApplicationContext())
                .load(ApiManager.getPath(getApplicationContext()) + mImageItem)
                .fitCenter()
                .into(ivImage);

        if (mName != null) {
            tvName.setVisibility(View.VISIBLE);
            tvName.setText(mName);
        }

        if (mInformation != null) {
            wvInformation.setVisibility(View.VISIBLE);
            wvInformation.loadData(mInformation, "text/html", "utf-8");
        }
    }
}
