package com.thinkmobiles.bodega.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.pager_logistica.PagerItemGalleryFragment;

import java.util.List;

/**
 * Created by sasha on 29.10.2015.
 */
public class ItemGalleryPagerAdapter extends FragmentPagerAdapter {

    List<String> mItems;

    public ItemGalleryPagerAdapter(FragmentManager fm) {
        super(fm);
    }
    public void setData(List<String> _items) {
        this.mItems = _items;
    }

    @Override
    public Fragment getItem(int position) {
        return PagerItemGalleryFragment.newInstance(mItems.get(position));
    }

    @Override
    public int getCount() {
        return mItems.size();
    }
}
