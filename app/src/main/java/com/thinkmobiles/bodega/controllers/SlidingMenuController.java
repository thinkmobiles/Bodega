package com.thinkmobiles.bodega.controllers;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
public class SlidingMenuController implements AdapterView.OnItemClickListener {

    private Activity mActivity;
    private SlidingMenu mSlidingMenu;
    private ListView mMenu;
    private SlidingMenuAdapter mAdapter;
    private AllLevelsModel mAllLevelsModel;
    private List<MenuEntry> mEntries;

    public SlidingMenuController(Activity _activity, AllLevelsModel _allLevelsModel) {
        mActivity = _activity;
        mAllLevelsModel = _allLevelsModel;
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
        getLevels(-1, "whatever");
    }

    private void getLevels(int level, String id) {
        List<ItemWrapper> levelList = mAllLevelsModel.getLevelByNum(level, id);
        if (levelList != null
                && level < 2
                && levelList.size() > 1) {
            level++;
            for (ItemWrapper item : levelList) {
                mEntries.add(new MenuEntry(item.getId(), item.getName(), level));
                if(!item.getId().equals(Constants.TANQUES_ID)
                        && !item.getId().equals(Constants.LOGOTIPOS_ID)
                        && !item.getId().equals(Constants.COLUMNAS_ID)
                        && !item.getId().equals(Constants.ARTICULOS_DE_USO_ID)) {
                    getLevels(level, item.getId());
                }
            }
        }
    }

    private void initHeaders() {
        MenuEntry footerEntry = new MenuEntry("SHIPMENT", "Envios", 0);
        MenuEntry headerEntry = new MenuEntry("INDEX", "Inicio", 0);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Log.d("OnItemClick", "Position is: " + position);
        MenuEntry entry = (MenuEntry) parent.getItemAtPosition(position);
        Toast.makeText(mActivity, entry.getName() + " "
                + entry.getId() + " "
                + entry.getLevel(), Toast.LENGTH_SHORT).show();
        mSlidingMenu.toggle();
    }
}
