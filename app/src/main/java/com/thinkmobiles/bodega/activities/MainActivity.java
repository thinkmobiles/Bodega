package com.thinkmobiles.bodega.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.AllLevelsModel;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.ProductWrapper;
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

        printLevels(1, null);
    }

    private void printLevels(int level, String id) {
        List<ItemWrapper> itemsList = null;
        switch (level) {
            case 1:
                itemsList = allLevelsModel.getFirstLevelList();
                break;
            case 2:
                itemsList = allLevelsModel.getSecondLevelListById(id);
                break;
            case 3:
                itemsList = allLevelsModel.getThirdLevelListById(id);
                break;
            case 4:
                itemsList = allLevelsModel.getFourthLevelListById(id);
                break;
        }
        if (itemsList != null) {
            for (ItemWrapper item : itemsList) {
                String tab = "";
                for (int i=0; i<level; i++) {
                    tab += "\t";
                }
                Log.d(LOG_TAG, tab + item.getName());
                int nextLevel = level + 1;
                printLevels(nextLevel, item.getId());
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
