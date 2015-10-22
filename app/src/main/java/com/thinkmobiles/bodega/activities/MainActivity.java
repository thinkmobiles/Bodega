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

    private SlidingMenuController mSlidingMenuController;
    private FragmentNavigator mFragmentNavigator;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragmentNAvgator();
        initApiManager();

        initSlidingMenu();
        initUI();
        loadIndex();
    }

    private void initFragmentNAvgator() {
        mFragmentNavigator = new FragmentNavigator();
        mFragmentNavigator.register(getSupportFragmentManager(), R.id.container);
    }

    private void initApiManager() {
        final ApiManager apiManager = new ApiManager(getApplicationContext());
        apiManager.setPrepareCallback(new ApiManager.PrepareCallback() {
            @Override
            public void managerIsReady() {
                List<Item> firstLevel = apiManager.getFirstList();
                Log.d("logs", "" + firstLevel.size());
            }
        });
        apiManager.prepare();
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

    private void loadIndex() {
        mFragmentNavigator.replaceFragment(IndexFragment.newInstance());
    }

    public FragmentNavigator getFragmentNavigator() {
        return mFragmentNavigator;
    }
}
