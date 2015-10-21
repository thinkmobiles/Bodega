package com.thinkmobiles.bodega.controllers;

import android.app.Activity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.SlidingMenuAdapter;

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

    public SlidingMenuController(Activity _activity) {
        mActivity = _activity;
    }

    public void attachSlidingMenu() {
        initMenu();
        findUi();
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
    }

    private void initAdapter() {
        mAdapter = new SlidingMenuAdapter(mActivity);
        mAdapter.setData(getDummyItems());
        mMenu.setAdapter(mAdapter);
    }

    private void setListeners() {
        mMenu.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(mActivity, mAdapter.getItem(position), Toast.LENGTH_SHORT).show();
        mSlidingMenu.toggle();
    }

    public void toggle() {
        mSlidingMenu.toggle();
    }



    private List<String> getDummyItems() {
        List<String> list = new ArrayList<>();
        list.add("Hola");
        list.add("¿Como estas?");
        list.add("Que tengas un buen día");
        return list;
    }


}
