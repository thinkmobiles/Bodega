package com.thinkmobiles.bodega.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.thinkmobiles.bodega.fragments.gallery_fragments.PagerItemGalleryFragment;

import java.util.List;

/**
 * Created by sasha on 29.10.2015.
 */
public class ItemGalleryPagerAdapter extends FragmentPagerAdapter {

    private List<String> mImageList, mNamesList, mInformationList;
    private String itemWrapperId;
    private String sName, sInformation;

    public ItemGalleryPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setData(List<String> _itemsList, List<String> _namesList, List<String> _informationList, String _itemWrapperId) {
        this.mImageList = _itemsList;
        this.mNamesList = _namesList;
        this.mInformationList = _informationList;
        this.itemWrapperId = _itemWrapperId;
    }

    @Override
    public Fragment getItem(int position) {
        if (mNamesList != null) {
            sName = mNamesList.get(position);
                    }
        if (mInformationList != null) {
            sInformation = mInformationList.get(position);
        }
        return PagerItemGalleryFragment.newInstance(mImageList.get(position), sName, sInformation, itemWrapperId);
    }

    @Override
    public int getCount() {
        return mImageList.size();
    }
}
