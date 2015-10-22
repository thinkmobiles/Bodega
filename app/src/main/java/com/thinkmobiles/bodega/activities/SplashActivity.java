package com.thinkmobiles.bodega.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.utils.SharedPrefUtils;

/**
 * Created by BogDan on 10/19/2015.
 */
public class SplashActivity extends Activity {

    private static final String LOG_TAG = "SplashActivitySDK";

    private TextView tvProgress;

    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvProgress = (TextView) findViewById(R.id.tvProgress_AS);

        runMainActivity();
//        initApiManager();
    }

    private void initApiManager() {
        apiManager = new ApiManager(getApplicationContext());
        apiManager.setPrepareCallback(new ApiManager.PrepareCallback() {
            @Override
            public void managerIsReady() {
                if (apiManager.needUpdate())
                    apiManager.downloadContent(eventListener);
                else
                    runMainActivity();
            }
        });
        apiManager.prepare();
    }

    private EventListener eventListener = new EventListener() {
        @Override
        public void onEvent(final Event event) {
            Log.d(LOG_TAG, event.getType() + " : " + event.getId() + " : " + event.getMessage());
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    performEventListenerAction(event);
                }
            });
        }
    };

    private void performEventListenerAction(Event event) {
        switch (event.getType()) {
            case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED:
                SharedPrefUtils.setLastUpdate(getApplicationContext(), apiManager.getLastModelUpdate());
                runMainActivity();
                break;
            case AppModel.ChangeEvent.ON_EXECUTE_ERROR:
                //restartApp();
                finish();
                break;
            case AppModel.ChangeEvent.DOWNLOAD_FILE_CHANGED:
                String progress = "Downloading " + event.getMessage() + "...";
                tvProgress.setText(progress);
                break;
        }
    }

    private void runMainActivity() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        eventListener = null;
    }

    /*private void showDialogClose() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.check_connection))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create()
                .show();
    }

    private void showDialogUpdate() {
        new AlertDialog.Builder(this)
                .setMessage(getString(R.string.want_update))
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        downloadContent();
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        openMainActivity();
                    }
                })
                .create()
                .show();
    }

    @Override
    public void onBackPressed() {
        if (!mIsLoadContent) {
            super.onBackPressed();
        }
    }*/
}
