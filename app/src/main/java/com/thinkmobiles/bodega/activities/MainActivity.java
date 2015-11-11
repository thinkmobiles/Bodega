package com.thinkmobiles.bodega.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.api.WrapperUtils;
import com.thinkmobiles.bodega.controllers.FragmentNavigator;
import com.thinkmobiles.bodega.controllers.SlidingMenuController;
import com.thinkmobiles.bodega.fragments.IndexFragment;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private static final String LOG_TAG = "MainActivity";

    private SlidingMenuController mSlidingMenuController;
    private FragmentNavigator mFragmentNavigator;
    private ArrayList<ItemWrapper> allLevelsList;
    private TextView tvMenuTitle;
    private ImageView ivBackgroundMain;
    private long mBackPressedAt;

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

        if (savedInstanceState == null)
            loadIndexFragment();

        initSlidingMenu();
        initUI();
    }

    private void initFragmentNavigator() {
        mFragmentNavigator = new FragmentNavigator();
        mFragmentNavigator.register(getSupportFragmentManager(), R.id.container);
    }

    private void extractBundle() {
        allLevelsList = getIntent().getParcelableArrayListExtra(Constants.ALL_LEVELS_MODEL_ARG);
        WrapperUtils.printWrappedLevels(allLevelsList);
    }

    private void initSlidingMenu() {
        mSlidingMenuController = new SlidingMenuController(this, allLevelsList);
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

    private void loadIndexFragment() {
        mFragmentNavigator.replaceFragment(IndexFragment.getInstance());
    }

    public FragmentNavigator getFragmentNavigator() {
        return mFragmentNavigator;
    }

    public ArrayList<ItemWrapper> getAllLevelsList() {
        return allLevelsList;
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
        else {
            if (IndexFragment.getInstance().isVisible()) {
                if (mBackPressedAt + 2000 > System.currentTimeMillis()) {
                    super.onBackPressed();
                } else {
                    mBackPressedAt = System.currentTimeMillis();
                    Toast.makeText(this, getString(R.string.click_back), Toast.LENGTH_SHORT).show();
                }
            } else {
               if (!mFragmentNavigator.popBackStack())
                   mFragmentNavigator.showFragmentWithoutBackStack(IndexFragment.getInstance());
            }
        }
    }
}
