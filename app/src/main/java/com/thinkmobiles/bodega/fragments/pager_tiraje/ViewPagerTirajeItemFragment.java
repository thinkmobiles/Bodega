package com.thinkmobiles.bodega.fragments.pager_tiraje;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by illia on 31.10.15.
 */
public class ViewPagerTirajeItemFragment extends BaseFragment {

    private TextView tvDescriprion;

    private ProductWrapper mProduct;

    public static BaseFragment newInstance(ProductWrapper _product) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_PRODUCT, _product);
        BaseFragment fragment = new ViewPagerTirajeItemFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_item_tiraje);
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
        tvDescriprion = $(R.id.tvDescription_FVPIT);
    }

    private void setData() {
        String text = mProduct.getImageDescription();
        tvDescriprion.setText(Html.fromHtml(text));
    }
}
