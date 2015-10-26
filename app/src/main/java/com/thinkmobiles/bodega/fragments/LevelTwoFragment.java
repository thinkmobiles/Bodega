package com.thinkmobiles.bodega.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.LinearLayoutManager;
import com.thinkmobiles.bodega.adapters.RecyclerAdapter;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.utils.ItemClickSupport;

import java.util.List;

/**
 * Created by illia on 26.10.15.
 */
public class LevelTwoFragment extends BaseFragment {

    private ImageView mHeadImage;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private RecyclerAdapter mAdapter;
    private List<ItemWrapper> mItems;
    private ItemWrapper mParentItem;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new LevelTwoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_level_two);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mParentItem = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initData();
        findView();
        startAnim();
        setUpData();
        setUpRecycler();
        setListeners();
    }

    private void initData() {
        for (ItemWrapper item : mActivity.getAllLevelsModel().getAllLevelsList()) {
            if (mParentItem.getId().equals(item.getId()))
                mItems = item.getInnerLevel();
        }
     }

    private void findView() {
        mHeadImage = $(R.id.ivHeadImage_FLT);
        mRecyclerView = $(R.id.rvRecycler_FLT);
    }

    private void startAnim() {
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(),
                R.anim.slide_top);
        RelativeLayout hiddenPanel = $(R.id.llBottomContainer_FLT);
        hiddenPanel.startAnimation(bottomUp);
        hiddenPanel.setVisibility(View.VISIBLE);
    }

    private void setUpData() {
        Glide.with(mActivity.getApplicationContext())
                .load(ApiManager.getPath(mActivity.getApplicationContext()) + mParentItem.getIcon())
                .fitCenter()
                .into(mHeadImage);
        mActivity.setActionBarTitle(mParentItem.getName());

    }

    private void setUpRecycler() {
        mLayoutManager  = new LinearLayoutManager(mActivity.getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
        mAdapter        = new RecyclerAdapter(mActivity.getApplicationContext(), mItems);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
    }

    private void setListeners() {
        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ItemWrapper item = mAdapter.getItem(position);
                Toast.makeText(mActivity.getApplicationContext(), item.getName() + " ", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
