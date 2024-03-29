package com.thinkmobiles.bodega.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.GridAdapter;
import com.thinkmobiles.bodega.api.ItemWrapper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 22.10.15.
 */
public class IndexFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private static IndexFragment mInstance;

    private GridView mGrid;
    private GridAdapter mAdapter;

    private List<ItemWrapper> mFirstLevel;

    public static BaseFragment getInstance() {
        if (mInstance == null)
            mInstance = new IndexFragment();
        return mInstance;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_index);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        setDefaultBackground();
        initData();
        findView();
        setUpGrid();
        initListeners();
    }

    private void initData() {
        setActionBarTitle("");
        mFirstLevel = mActivity.getAllLevelsList();
        List<ItemWrapper> validItems = new ArrayList<>();
        for (ItemWrapper item : mFirstLevel) {
            if (!TextUtils.isEmpty(item.getName())
                    && !TextUtils.isEmpty(item.getIcon())) {
                validItems.add(item);
            }
        }
        mFirstLevel = validItems;
    }

    private void findView() {
        mGrid = $(R.id.gvGrid_FI);
    }

    private void setUpGrid() {
        mAdapter = new GridAdapter(mActivity);
        mAdapter.setItems(mFirstLevel);
        mGrid.setAdapter(mAdapter);
    }

    private void initListeners() {
        mGrid.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemWrapper item = mAdapter.getItem(position);
        if (!Constants.LOGISTICA_ID.equals(item.getId()))
            mFragmentNavigator.showFragment(LevelTwoFragment.newInstance(item));
        else
            mFragmentNavigator.showFragment(DescriptionFragment.newInstance(item, true, true, false));
    }
}
