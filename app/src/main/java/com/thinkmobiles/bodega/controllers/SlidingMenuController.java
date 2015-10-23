package com.thinkmobiles.bodega.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.SlidingMenuAdapter;
import com.thinkmobiles.bodega.api.AllLevelsModel;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.MenuEntry;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 21.10.15.
 */
public class SlidingMenuController implements AdapterView.OnItemClickListener, View.OnClickListener {

    private Activity mActivity;
    private SlidingMenu mSlidingMenu;
    private ListView mMenu;
    private SlidingMenuAdapter mAdapter;
    private AllLevelsModel mAllLevelsModel;
    private List<MenuEntry> mEntries;
    private TextView mHeadeEntry;
    private TextView mFooterEntry;

    public SlidingMenuController(Activity _activity, AllLevelsModel _allLevelsModel) {
        mActivity = _activity;
        mAllLevelsModel = _allLevelsModel;
    }

    public void attachSlidingMenu() {
        initMenu();
        findUi();
        initMenuEntries();
        initAdapter();
        setListeners();
    }

    private void initMenu() {
        mSlidingMenu = new SlidingMenu(mActivity);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setTouchmodeMarginThreshold((int) mActivity.getResources().getDimension(R.dimen.slidingmenu_swipe_threshold));
        mSlidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        mSlidingMenu.setFadeEnabled(true);
        mSlidingMenu.setFadeDegree(0.3f);
        int screenWidthPx = mActivity.getResources().getDisplayMetrics().widthPixels;
        mSlidingMenu.setBehindWidth((int) (screenWidthPx * 0.333));
        mSlidingMenu.attachToActivity(mActivity, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.sliding_menu);
        mSlidingMenu.setSlidingEnabled(true);
    }

    private void findUi() {
        mMenu = (ListView) mSlidingMenu.findViewById(R.id.lvMenu_SM);
        mHeadeEntry = (TextView) mSlidingMenu.findViewById(R.id.tvMenuHeader_SM);
        mFooterEntry = (TextView) mSlidingMenu.findViewById(R.id.tvMenuFooter_SM);
    }

    private void initMenuEntries() {
        mEntries = new ArrayList<>();
        List<ItemWrapper> firstLevelList = mAllLevelsModel.getFirstLevelList();
        if (firstLevelList != null) {
            for (ItemWrapper item : firstLevelList) {
                mEntries.add(new MenuEntry(item.getId(), item.getName(), Constants.LEVEL_FIRST));
                List<ItemWrapper> secondLevelList = mAllLevelsModel.getSecondLevelListById(item.getId());
                if (secondLevelList != null)
                    for (ItemWrapper secondLevelItem : secondLevelList) {
                        mEntries.add(new MenuEntry(secondLevelItem.getId(), secondLevelItem.getName(), Constants.LEVEL_SECOND));
                        List<ItemWrapper> thirdLevelList = mAllLevelsModel.getThirdLevelListById(secondLevelItem.getId());
                        if (thirdLevelList != null)
                            for (ItemWrapper thirdLevelItem : thirdLevelList) {
                                mEntries.add(new MenuEntry(thirdLevelItem.getId(), thirdLevelItem.getName(), Constants.LEVEL_THIRD));
                            }
                    }
            }
        }
    }

    private void initAdapter() {
        mAdapter = new SlidingMenuAdapter(mActivity);
        mAdapter.setData(mEntries);
        mMenu.setAdapter(mAdapter);
    }

    private void setListeners() {
        mMenu.setOnItemClickListener(this);
        mFooterEntry.setOnClickListener(this);
        mHeadeEntry.setOnClickListener(this);
    }

    public void toggle() {
        mSlidingMenu.toggle();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mActivity, mAdapter.getItem(position).getId() + " " + mAdapter.getItem(position).getLevel(), Toast.LENGTH_SHORT).show();
        mSlidingMenu.toggle();
    }

    @Override
    public void onClick(View v) {
        mSlidingMenu.toggle();
        switch (v.getId()) {
            case R.id.tvMenuHeader_SM:
                Toast.makeText(mActivity, mActivity.getString(R.string.inicio), Toast.LENGTH_SHORT).show();
                break;
            case R.id.tvMenuFooter_SM:
                Toast.makeText(mActivity, mActivity.getString(R.string.envios), Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
