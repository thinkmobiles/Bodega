package com.thinkmobiles.bodega.fragments.extras;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by illia on 29.10.15.
 */
public class ExtrasFragment extends BaseFragment {

    private ItemWrapper mItem;
    private RecyclerView rvExtras;
    private ImageView ivDownBtn;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new ExtrasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_extras);
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
//        generateDummyProducts();
//        generateTabs();
//        setupPager();
//        initListeners();
    }

    private void findUi() {
        rvExtras = $(R.id.rvExtras_FE);
        ivDownBtn = $(R.id.ivDownBtn_FE);
    }

    private void generateExtras() {

    }

    private void setUpRecycler() {

    }
}
