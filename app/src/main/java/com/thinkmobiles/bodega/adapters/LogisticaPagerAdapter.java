package com.thinkmobiles.bodega.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.pager_logistica.ViewPagerLogisticaItemFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 28.10.15.
 */
public class LogisticaPagerAdapter extends FragmentPagerAdapter {

    private List<ProductWrapper> mProducts;

    public LogisticaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<ProductWrapper> _products) {
        this.mProducts = _products;
    }

    @Override
    public Fragment getItem(int position) {
        return ViewPagerLogisticaItemFragment.newInstance((ArrayList<ProductWrapper>)mProducts, position);
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }
}
