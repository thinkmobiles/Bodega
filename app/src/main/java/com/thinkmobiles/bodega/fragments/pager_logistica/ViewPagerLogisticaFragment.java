package com.thinkmobiles.bodega.fragments.pager_logistica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTitleStrip;
import android.support.v4.view.ViewPager;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.LogiscticaPagerAdapter;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 28.10.15.
 */
public class ViewPagerLogisticaFragment extends BaseFragment {

    private ItemWrapper mItem;
    private ViewPager mPager;
    private PagerTitleStrip mTitleStrip;
    private LogiscticaPagerAdapter mAdapter;

    private List<ProductWrapper> prods;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new ViewPagerLogisticaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_logistica);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItem = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findUi();
        generateDummyProducts();
        setupPager();
//        setListeners();
//        setContainers();
//        setUpData();
    }

    private void findUi() {
        mPager = $(R.id.vpPager_FPL);
        mTitleStrip = $(R.id.ptsTitleStrip_FPL);
    }

    private void generateDummyProducts() {
        // TODO delete wne data is ok
        prods = new ArrayList<>(mItem.getProductList());
        ProductWrapper p1 = new ProductWrapper();
        ProductWrapper p2 = new ProductWrapper();
        ProductWrapper p3 = new ProductWrapper();
        p1.setImageDescription("1 " + getString(R.string.lorem_ipsum));
        p2.setImageDescription("2 " + getString(R.string.lorem_ipsum));
        p3.setImageDescription("3 " + getString(R.string.lorem_ipsum));
        prods.add(p1);
        prods.add(p2);
        prods.add(p3);
    }

    private void setupPager() {
        mTitleStrip.setTextColor(ContextCompat.getColor(getApplicationContext(),R.color.black));
        mAdapter = new LogiscticaPagerAdapter(getFragmentManager());
        mAdapter.setData(prods);
        mPager.setAdapter(mAdapter);
    }
}
