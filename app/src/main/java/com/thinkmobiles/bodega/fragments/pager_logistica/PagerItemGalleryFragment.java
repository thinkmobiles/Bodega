package com.thinkmobiles.bodega.fragments.pager_logistica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
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

    private TextView tvText;
    private ImageView ivImage;

    private String mImageItem;

    public static BaseFragment newInstance(String _item) {
        Bundle args = new Bundle();
        args.putString(Constants.EXTRA_PRODUCT, _item);
        BaseFragment fragment = new PagerItemGalleryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_item_galiery);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mImageItem =  args.getString(Constants.EXTRA_PRODUCT);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        setData();
    }

    private void findView() {
        tvText = $(R.id.tv_name_FVPIG);
        ivImage = $(R.id.iv_image_FVPIG);
    }

    private void setData() {

        Glide.with(getApplicationContext())
                .load(ApiManager.getPath(getApplicationContext()) + mImageItem)
                .fitCenter()
                .into(ivImage);
        Log.d("qqq"," page Item:"+ mImageItem);
    }
}
