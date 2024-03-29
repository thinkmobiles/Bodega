package com.thinkmobiles.bodega.fragments.pager_logistica;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.LogisticaPagerAdapter;
import com.thinkmobiles.bodega.adapters.LogisticaTabsAdapter;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.fragments.gallery_fragments.ViewGalleryPagerFragment;
import com.thinkmobiles.bodega.utils.ItemClickSupport;
import com.thinkmobiles.bodega.utils.TabItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 28.10.15.
 */
public class ViewPagerLogisticaFragment extends BaseFragment implements ItemClickSupport.OnItemClickListener, ViewPager.OnClickListener {

    private ItemWrapper mItem;
    private ViewPager mPager;
    private LogisticaPagerAdapter mPagerAdapter;
    private LogisticaTabsAdapter mTabsAdapter;
    private RecyclerView rvTabsView;
    private List<TabItem> mTabs;
    private int mCurrentItem;

    private List<ProductWrapper> mProds;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new ViewPagerLogisticaFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_view_pager_logistica);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItem = args.getParcelable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findUi();
        generateDummyProducts();
        generateTabs();
        setupPager();
        initListeners();
    }

    private void findUi() {
        mPager = $(R.id.vpPager_FVPL);
        rvTabsView = $(R.id.rvRecyclerTabs_FVPL);
    }

    private void generateDummyProducts() {
        // TODO delete wne data is ok
        mProds = new ArrayList<>();
        List<String> options = mItem.getInnerLevel().get(0).getInnerLevel().get(0).getProductList().get(0).getOptions();
        List<String> optionImages = mItem.getInnerLevel().get(0).getInnerLevel().get(0).getProductList().get(0).getOptionsImages();
        if (optionImages != null) {
            for (int i = 0; i < optionImages.size(); i++) {
                ProductWrapper p = new ProductWrapper();
                p.setImage(optionImages.get(i));
                if (options != null)
                    p.setImageDescription(options.get(i));
                else
                    p.setImageDescription("1 " + getString(R.string.lorem_ipsum));
                mProds.add(p);
            }
        }
    }

    private void generateTabs() {
        mTabs = new ArrayList<>();
        for (int i=0; i < mProds.size(); i++) {
            TabItem item = new TabItem();
            item.name = getString(R.string.opcion) + " " + (i+1);
            if (i==0)
                item.isSelected = true;
            mTabs.add(item);
        }
    }

    private void setupPager() {
        mTabsAdapter = new LogisticaTabsAdapter(getApplicationContext(), mTabs);
        rvTabsView.setHasFixedSize(true);
        rvTabsView.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rvTabsView.setAdapter(mTabsAdapter);

        mPagerAdapter = new LogisticaPagerAdapter(getChildFragmentManager());
        mPagerAdapter.setData(mProds);
        mPager.setAdapter(mPagerAdapter);
        mPager.setOnClickListener(this);
    }

    private void initListeners() {
        ItemClickSupport.addTo(rvTabsView).setOnItemClickListener(this);
        mPager.addOnPageChangeListener(mPageChangeListener);
    }

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        setSelectedTab(position);
        mPager.setCurrentItem(position, true);

    }

    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            if (mCurrentItem != position) {
                setSelectedTab(position);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            //
        }
    };

    private void setSelectedTab(int position) {
        mCurrentItem = position;
        mTabsAdapter.selectItem(position);
        rvTabsView.scrollToPosition(position);
    }

    @Override
    public void onClick(View v) {
        mFragmentNavigator.showFragment(ViewGalleryPagerFragment.newInstance(mItem, mCurrentItem, false));
    }
}
