package com.thinkmobiles.bodega.fragments.pager_tiraje;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.TirajePagerAdapter;
import com.thinkmobiles.bodega.adapters.TirajeTabsAdapter;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.utils.ItemClickSupport;
import com.thinkmobiles.bodega.utils.TabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 31.10.15.
 */
public class ViewPagerTirajeFragment extends BaseFragment implements ItemClickSupport.OnItemClickListener, View.OnClickListener {

    private ItemWrapper mItem;
    private ViewPager vpPager;
    private RecyclerView rvTabs;
    private List<TabItem> mTabs;
    private ImageView ivBtnUp;
    private ImageView ivBtnDown;
    private ImageView ivImage;

    private TirajeTabsAdapter mTabsAdapter;
    private TirajePagerAdapter mPagerAdapter;

    private List<ProductWrapper> mProducts;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new ViewPagerTirajeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_tiraje);
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
        generateProducts();
        generateTabs();
        setupPager();
        initListeners();
    }

    private void findUi() {
        vpPager = $(R.id.vpPager_FVPT);
        rvTabs = $(R.id.rvRecyclerTabs_FVPT);

        ivBtnUp = $(R.id.ivBtnUp_FVPT);
        ivBtnDown = $(R.id.ivBtnDown_FVPT);
        ivImage = $(R.id.ivImage_FVPT);
    }

    private void generateProducts() {
        mProducts = new ArrayList<>();
        List<String> options = mItem.getInnerLevel().get(0).getProductList().get(0).getOptions();
        List<String> optionImages = mItem.getInnerLevel().get(0).getProductList().get(0).getOptionsImages();
        if (optionImages != null) {
            for (int i = 0; i < optionImages.size(); i++) {
                ProductWrapper p = new ProductWrapper();
                p.setImage(optionImages.get(i));
                if (options != null)
                    p.setImageDescription(options.get(i));
                else
                    p.setImageDescription("1 " + getString(R.string.lorem_ipsum));
                mProducts.add(p);
            }
        }
    }
    private void generateTabs() {
        mTabs = new ArrayList<>();
        for (int i=0; i < mProducts.size(); i++) {
            TabItem item = new TabItem();
            item.name = "" + (i+1);
            if (i==0)
                item.isSelected = true;
            mTabs.add(item);
        }
    }

    private void setupPager() {
        mTabsAdapter = new TirajeTabsAdapter(getApplicationContext(), mTabs);
        rvTabs.setHasFixedSize(true);
        rvTabs.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));
        rvTabs.setItemAnimator(new DefaultItemAnimator());
        rvTabs.setAdapter(mTabsAdapter);

        mPagerAdapter = new TirajePagerAdapter(getChildFragmentManager());
        mPagerAdapter.setData(mProducts);
        vpPager.setAdapter(mPagerAdapter);

        checkActiveBtns();
        changeImage(0);
    }

    private void initListeners() {
        ItemClickSupport.addTo(rvTabs).setOnItemClickListener(this);
        vpPager.addOnPageChangeListener(mPageChangeListener);
        ivBtnUp.setOnClickListener(this);
        ivBtnDown.setOnClickListener(this);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        vpPager.setCurrentItem(position, true);
    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            //
        }

        @Override
        public void onPageSelected(int position) {
            setSelectedTab(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //
        }
    };

    private void setSelectedTab(int position) {
        mTabsAdapter.selectItem(position);
        rvTabs.scrollToPosition(position);
        checkActiveBtns();
        changeImage(position);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivBtnUp_FVPT:
                vpPager.setCurrentItem(vpPager.getCurrentItem() - 1);
                break;
            case R.id.ivBtnDown_FVPT:
                vpPager.setCurrentItem(vpPager.getCurrentItem() + 1);
                break;
        }
    }

    private void checkActiveBtns() {
        if (vpPager.getCurrentItem() == 0)
            ivBtnUp.setVisibility(View.INVISIBLE);
        else
            ivBtnUp.setVisibility(View.VISIBLE);

        if (vpPager.getCurrentItem()
                == (mProducts.size() - 1))
            ivBtnDown.setVisibility(View.INVISIBLE);
        else
            ivBtnDown.setVisibility(View.VISIBLE);

    }

    private void changeImage(int _position) {
        Glide.with(getApplicationContext())
                .load(ApiManager.getPath(getApplicationContext()) + mProducts.get(_position).getImage())
                .into(ivImage);
    }
}