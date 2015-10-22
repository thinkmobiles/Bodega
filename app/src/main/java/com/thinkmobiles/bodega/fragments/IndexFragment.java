package com.thinkmobiles.bodega.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.GridView;

import com.cristaliza.mvc.models.estrella.Item;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.GridAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 22.10.15.
 */
public class IndexFragment extends BaseFragment {

    private GridView mGrid;
    private GridAdapter mAdapter;

    private List<Item> mFirstLevel;

    public static BaseFragment newInstance() {
        return new IndexFragment();
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

        initData();
        findView();
        setUpGrid();
    }

    private void initData() {
        mFirstLevel = mActivity.getFirstLevel();
        List<Item> validItems = new ArrayList<>();
        for (Item item : mFirstLevel) {
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
}
