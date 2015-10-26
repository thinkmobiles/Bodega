package com.thinkmobiles.bodega.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;

/**
 * Created by sasha on 26.10.2015.
 */
public class CharacteristicsFragment extends BaseFragment {

    private TextView mInformation;
    private RecyclerView mRecyclerView;
    private ItemWrapper mParentItem;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_characteristics);
        checkArgument();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
    }

    private void findView() {
        mInformation = $(R.id.tv_information_FC);
        mRecyclerView = $(R.id.rvRecycler_FC);
    }
    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mParentItem = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
        }
    }

}
