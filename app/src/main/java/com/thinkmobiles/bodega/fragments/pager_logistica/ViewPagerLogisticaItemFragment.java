package com.thinkmobiles.bodega.fragments.pager_logistica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.fragments.gallery_fragments.ViewGalleryPagerFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 28.10.15.
 */
public class ViewPagerLogisticaItemFragment extends BaseFragment {

    private TextView tvText;
    private ImageButton ivImage;

    private List<ProductWrapper> mProductList;
    private int mPosition;

    public static BaseFragment newInstance(ArrayList<ProductWrapper> _productList, int _position) {
        Bundle args = new Bundle();
        args.putParcelableArrayList(Constants.EXTRA_PRODUCT, _productList);
        args.putInt(Constants.EXTRA_FLAG_1, _position);
        BaseFragment fragment = new ViewPagerLogisticaItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_item_logistica);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mProductList = args.getParcelableArrayList(Constants.EXTRA_PRODUCT);
            mPosition = args.getInt(Constants.EXTRA_FLAG_1);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findUi();
        setData();
        setListener();
    }

    private void setListener() {
        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("qqq", "click111");
                ItemWrapper itemWrapper = new ItemWrapper();
                itemWrapper.setProductList(mProductList);
                itemWrapper.setId(Constants.LOGISTICA_ID);
                mFragmentNavigator.showFragment(ViewGalleryPagerFragment.newInstance(itemWrapper, mPosition, false));
            }
        });
    }

    private void findUi() {
        tvText = $(R.id.tvText_FILP);
        ivImage = $(R.id.ivImage_FILP);
    }

    private void setData() {
//        String text = mProductList.get(mPosition).getImageDescription();
//        if (!TextUtils.isEmpty(text))
//            tvText.setText(text);
        Glide.with(getApplicationContext())
            .load(ApiManager.getPath(getApplicationContext()) + mProductList.get(mPosition).getImage())
            .fitCenter()
            .into(ivImage);
    }
}
