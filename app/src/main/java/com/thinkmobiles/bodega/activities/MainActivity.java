package com.thinkmobiles.bodega.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.AllLevelsModel;
import com.thinkmobiles.bodega.api.WrapperUtils;
import com.thinkmobiles.bodega.controllers.FragmentNavigator;
import com.thinkmobiles.bodega.controllers.SlidingMenuController;
import com.thinkmobiles.bodega.fragments.IndexFragment;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private SlidingMenuController mSlidingMenuController;
    private FragmentNavigator mFragmentNavigator;
    private AllLevelsModel allLevelsModel;
    private TextView tvMenuTitle;
    private ImageView ivBackgroundMain;

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
        WrapperUtils.printWrappedLevels(allLevelsModel.getAllLevelsList());
    }


    private void initSlidingMenu() {
        mSlidingMenuController = new SlidingMenuController(this, allLevelsModel);
        mSlidingMenuController.attachSlidingMenu();
    }

    private void initUI() {
        findViewById(R.id.ivMenuBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSlidingMenuController.toggleAsync();
            }
        });
        tvMenuTitle = (TextView) findViewById(R.id.tvMenuTitle);
        ivBackgroundMain = (ImageView) findViewById(R.id.ivBackgroundMain);
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

    public void setActionBarTitle(String _title) {
        tvMenuTitle.setText(_title);
    }

    public void setBackground(int _resId) {
        ivBackgroundMain.setImageResource(_resId);
    }

    @Override
    public void onBackPressed() {
        if (mSlidingMenuController.isShown())
            mSlidingMenuController.close();
        else
            super.onBackPressed();
    }
}
