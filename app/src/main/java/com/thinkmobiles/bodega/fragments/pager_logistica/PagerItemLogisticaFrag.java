package com.thinkmobiles.bodega.fragments.pager_logistica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by illia on 28.10.15.
 */
public class PagerItemLogisticaFrag extends BaseFragment {

    private TextView tvText;
    private ImageView ivImage;

    private ProductWrapper mProduct;

    public static BaseFragment newInstance(ProductWrapper _product) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_PRODUCT, _product);
        BaseFragment fragment = new PagerItemLogisticaFrag();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_item_logistica_pager);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mProduct = (ProductWrapper) args.getSerializable(Constants.EXTRA_PRODUCT);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findUi();
        setData();
    }

    private void findUi() {
        tvText = $(R.id.tvText_FILP);
        ivImage = $(R.id.ivImage_FILP);
    }

    private void setData() {
        String text = mProduct.getImageDescription();
        if (!TextUtils.isEmpty(text))
            tvText.setText(text);

        //TODO fix this whe data arrives
        if ((text.charAt(0) + "").equals("1"))
            Glide.with(getApplicationContext())
                .fromResource()
                .load(R.drawable.logistica_camion01_ldpi)
                .fitCenter()
                .into(ivImage);
        if ((text.charAt(0) + "").equals("2"))
            Glide.with(getApplicationContext())
                    .fromResource()
                    .load(R.drawable.logistica_camion02_ldpi)
                    .fitCenter()
                    .into(ivImage);
        if ((text.charAt(0) + "").equals("3"))
            Glide.with(getApplicationContext())
                    .fromResource()
                    .load(R.drawable.logistica_camion03_ldpi)
                    .fitCenter()
                    .into(ivImage);
    }
}
