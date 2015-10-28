package com.thinkmobiles.bodega.controllers;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.activities.MainActivity;
import com.thinkmobiles.bodega.adapters.SlidingMenuAdapter;
import com.thinkmobiles.bodega.api.AllLevelsModel;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.IndexFragment;
import com.thinkmobiles.bodega.fragments.LevelTwoFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 21.10.15.
 */
public class SlidingMenuController implements AdapterView.OnItemClickListener {

    private MainActivity mActivity;
    private SlidingMenu mSlidingMenu;
    private ListView mMenu;
    private SlidingMenuAdapter mAdapter;
    private AllLevelsModel mAllLevelsModel;
    private List<ItemWrapper> mEntries;
    private FragmentNavigator mFragmentNavigator;

    public SlidingMenuController(MainActivity _activity, AllLevelsModel _allLevelsModel) {
        mActivity = _activity;
        mAllLevelsModel = _allLevelsModel;
        mFragmentNavigator = mActivity.getFragmentNavigator();
    }

    public void attachSlidingMenu() {
        initMenu();
        findUi();
        initMenuEntries();
        initHeaders();
        initAdapter();
        setListeners();
    }

    private void initMenu() {
        mSlidingMenu = new SlidingMenu(mActivity);
        mSlidingMenu.setMode(SlidingMenu.LEFT);
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        mSlidingMenu.setBackgroundColor(mActivity.getResources().getColor(R.color.black)); // no white line..
        mSlidingMenu.setTouchmodeMarginThreshold((int) mActivity.getResources().getDimension(R.dimen.slidingmenu_swipe_threshold));
        //mSlidingMenu.setShadowWidthRes(R.dimen.slidingmenu_shadow_width);
        mSlidingMenu.setFadeEnabled(false);
        //mSlidingMenu.setFadeDegree(0.3f);
        int screenWidthPx = mActivity.getResources().getDisplayMetrics().widthPixels;
        mSlidingMenu.setBehindWidth((int) (screenWidthPx * 0.333));
        mSlidingMenu.attachToActivity(mActivity, SlidingMenu.SLIDING_CONTENT);
        mSlidingMenu.setMenu(R.layout.sliding_menu);
        mSlidingMenu.setSlidingEnabled(true);
    }

    private void findUi() {
        mMenu = (ListView) mSlidingMenu.findViewById(R.id.lvMenu_SM);
    }

    private void initMenuEntries() {
        mEntries = new ArrayList<>();
        List<ItemWrapper> levelList = mAllLevelsModel.getAllLevelsList();
        getLevels(levelList);
    }

    private void getLevels(List<ItemWrapper> levelList) {
        if (levelList != null
                && !levelList.isEmpty()
                && levelList.get(0).getLevelNumber() < 4
                && levelList.size() > 1) {
            for (ItemWrapper item : levelList) {
                if (!Constants.COMPANIA_ID.equals(item.getId())) {
                    mEntries.add(item);
                    if (!Constants.TANQUES_ID.equals(item.getId())
                            && !Constants.LOGOTIPOS_ID.equals(item.getId())
                            && !Constants.COLUMNAS_ID.equals(item.getId())
                            && !Constants.ARTICULOS_DE_USO_ID.equals(item.getId())) {
                        getLevels(item.getInnerLevel());
                    }
                }
            }
        }
    }

    private void initHeaders() {
        ItemWrapper headerEntry = new ItemWrapper();
        headerEntry.setId(Constants.INICIO_ID);
        headerEntry.setName(mActivity.getString(R.string.inicio));
        headerEntry.setLevelNumber(1);

        ItemWrapper footerEntry = new ItemWrapper();
        footerEntry.setId(Constants.ENVIOS_ID);
        footerEntry.setName(mActivity.getString(R.string.envios));
        footerEntry.setLevelNumber(1);

        View headerView = LayoutInflater.from(mActivity).inflate(R.layout.sliding_menu_header, mMenu, false);
        View footerView = LayoutInflater.from(mActivity).inflate(R.layout.sliding_menu_footer, mMenu, false);
        mMenu.addHeaderView(headerView, headerEntry, true);
        mMenu.addFooterView(footerView, footerEntry, true);
    }

    private void initAdapter() {
        mAdapter = new SlidingMenuAdapter(mActivity);
        mAdapter.setData(mEntries);
        mMenu.setAdapter(mAdapter);
    }

    private void setListeners() {
        mMenu.setOnItemClickListener(this);
    }

    public void toggle() {
        mSlidingMenu.toggle();
    }

    public void close() {
        mSlidingMenu.showContent();
    }

    public boolean isShown() {
        return mMenu.isShown();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ItemWrapper entry = (ItemWrapper) parent.getItemAtPosition(position);

        switch (entry.getLevelNumber()) {
            case Constants.LEVEL_FIRST:
                changeFirstLevelFragment(entry);
                break;
            case Constants.LEVEL_SECOND:
                if (mFragmentNavigator.checkSecondLevelFragmentOnThirdLvl(entry, false))
                    break;
                else
                    /////
                break;
            case Constants.LEVEL_THIRD:
                //
                break;
        }
        Toast.makeText(mActivity, entry.getName() + " "
                + entry.getId() + " "
                + entry.getLevelNumber(), Toast.LENGTH_SHORT).show();
        mSlidingMenu.toggle();
    }

    private void changeFirstLevelFragment(ItemWrapper _entry) {
        switch (_entry.getId()) {
            case Constants.INICIO_ID:
                mFragmentNavigator.showFragmentWithoutBackStack(IndexFragment.newInstance());
                break;
            case Constants.ENVIOS_ID:
                //
                break;
            case Constants.LOGISTICA_ID:
                //
                break;
            default:
                mFragmentNavigator.showFragmentWithoutBackStack(LevelTwoFragment
                                .newInstance(_entry));
        }
    }
}
