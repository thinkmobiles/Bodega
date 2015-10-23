package com.thinkmobiles.bodega.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.AllLevelsModel;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.controllers.FragmentNavigator;
import com.thinkmobiles.bodega.controllers.SlidingMenuController;
import com.thinkmobiles.bodega.fragments.IndexFragment;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ApiManagerSDK";

    private SlidingMenuController mSlidingMenuController;
    private FragmentNavigator mFragmentNavigator;

    private AllLevelsModel allLevelsModel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragmentNavigator();
        extractBundle();
        loadIndexFragment();

        initSlidingMenu();
        initUI();
    }

    private void initFragmentNavigator() {
        mFragmentNavigator = new FragmentNavigator();
        mFragmentNavigator.register(getSupportFragmentManager(), R.id.container);
    }

    private void extractBundle() {
        allLevelsModel = getIntent().getParcelableExtra(Constants.ALL_LEVELS_MODEL_ARG);
        List<ItemWrapper> firstLevelList = allLevelsModel.getFirstLevelList();
        if (firstLevelList != null) {
            for (ItemWrapper item : firstLevelList) {
                Log.d(LOG_TAG, "" + item.getName());
                List<ItemWrapper> secondLevelList = allLevelsModel.getSecondLevelListById(item.getId());
                if (secondLevelList != null)
                    for (ItemWrapper secondLevelItem : secondLevelList) {
                        Log.d(LOG_TAG, "\t--" + secondLevelItem.getName());
                        List<ItemWrapper> thirdLevelList = allLevelsModel.getThirdLevelListById(secondLevelItem.getId());
                        if (thirdLevelList != null)
                            for (ItemWrapper thirdLevelItem : thirdLevelList) {
                                Log.d(LOG_TAG, "\t\t--" + thirdLevelItem.getName());
                                List<ItemWrapper> fourthLevelList = allLevelsModel.getFourthLevelListById(thirdLevelItem.getId());
                                if (fourthLevelList != null)
                                    for (ItemWrapper fourthLevelItem : fourthLevelList) {
                                        Log.d(LOG_TAG, "\t\t\t--" + fourthLevelItem.getName());
                                    }
                            }
                    }
            }
        }
    }

    private void initSlidingMenu() {
        mSlidingMenuController = new SlidingMenuController(this);
        mSlidingMenuController.attachSlidingMenu();
    }

    private void initUI() {
        findViewById(R.id.ivMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingMenuController.toggle();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void loadIndexFragment() {
        mFragmentNavigator.replaceFragment(IndexFragment.newInstance());
    }

    public FragmentNavigator getFragmentNavigator() {
        return mFragmentNavigator;
    }

    public AllLevelsModel getAllLevelsModel() {
        return allLevelsModel;
    }
}
