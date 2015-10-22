package com.thinkmobiles.bodega.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cristaliza.mvc.models.estrella.Item;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.controllers.FragmentNavigator;
import com.thinkmobiles.bodega.controllers.SlidingMenuController;
import com.thinkmobiles.bodega.fragments.IndexFragment;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "ApiManagerSDK";

    private SlidingMenuController mSlidingMenuController;
    private FragmentNavigator mFragmentNavigator;
    private ApiManager mApiManager;
    private List<Item> mFirstLevel;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragmentNavgator();
        initApiManager();

        initSlidingMenu();
        initUI();
    }

    private void initFragmentNavgator() {
        mFragmentNavigator = new FragmentNavigator();
        mFragmentNavigator.register(getSupportFragmentManager(), R.id.container);
    }

    private void initApiManager() {
        mApiManager = new ApiManager(getApplicationContext());
        mApiManager.setPrepareCallback(new ApiManager.PrepareCallback() {
            @Override
            public void managerIsReady() {
                mApiManager.getAllLevels();
            }
        });
        mApiManager.setLoadDataCallback(new ApiManager.LoadDataCallback() {
            @Override
            public void dataIsLoaded() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadIndex();
                        dataIsReady();
                    }
                });
            }
        });
        apiManager.prepare();
    }

    private void dataIsReady() {
        mFirstLevel = apiManager.getFirstLevelList();
        Log.d(LOG_TAG, "loaded list: " + (mFirstLevel == null));
        if (mFirstLevel != null) {
            for (Item item : mFirstLevel) {
                Log.d(LOG_TAG, "" + item.getName());
                List<Item> secondLevelList = apiManager.getSecondLevelListById(item.getId());
                if (secondLevelList != null)
                    for (Item secondLevelItem : secondLevelList) {
                        Log.d(LOG_TAG, "\t--" + secondLevelItem.getName());
                        List<Item> thirdLevelList = apiManager.getThirdLevelListById(secondLevelItem.getId());
                        if (thirdLevelList != null)
                            for (Item thirdLevelItem : thirdLevelList) {
                                Log.d(LOG_TAG, "\t\t--" + thirdLevelItem.getName());
                                List<Item> fourthLevelList = apiManager.getFourthLevelListById(thirdLevelItem.getId());
                                if (fourthLevelList != null)
                                    for (Item fourthLevelItem : fourthLevelList) {
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

    private void loadIndex() {
        mFragmentNavigator.replaceFragment(IndexFragment.newInstance());
    }

    public FragmentNavigator getFragmentNavigator() {
        return mFragmentNavigator;
    }

    public ApiManager getApiManager() {
        return mApiManager;
    }

    public List<Item> getFirstLevel() {
        return mFirstLevel;
    }
}
