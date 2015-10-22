package com.thinkmobiles.bodega.activities;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.Item;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.controllers.SlidingMenuController;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    private SlidingMenuController mSlidingMenuController;
    private ApiManager apiManager;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initApiManager();

        initSlidingMenu();
        initUI();
    }

    private void initApiManager() {
        apiManager = new ApiManager(getApplicationContext());
        apiManager.setPrepareCallback(new ApiManager.PrepareCallback() {
            @Override
            public void managerIsReady() {
                apiManager.getAllLevels();
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
