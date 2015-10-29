package com.thinkmobiles.bodega.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.pager_logistica.PagerItemLogisticaFrag;

import java.util.List;

/**
 * Created by illia on 28.10.15.
 */
public class LogiscticaPagerAdapter extends FragmentPagerAdapter {

    private List<ProductWrapper> mProducts;

    public LogiscticaPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<ProductWrapper> _products) {
        this.mProducts = _products;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerItemLogisticaFrag.newInstance(mProducts.get(position));
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "OPCION " + (1 + position);
    }
}
