package com.thinkmobiles.bodega.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.widget.TextView;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;
import com.thinkmobiles.bodega.utils.InetChecker;
import com.thinkmobiles.bodega.utils.SharedPrefUtils;

/**
 * Created by BogDan on 10/19/2015.
 */
public class SplashActivity extends Activity {

    private static final String LOG_TAG = "SplashActivitySDK";

    private TextView tvProgress;
    private boolean errorDialogIsShown = false;
    private boolean activityIsDestroyed = false;
    private ApiManager apiManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvProgress = (TextView) findViewById(R.id.tvProgress_AS);

        initApiManager();
        startDownload();
    }

    private void startDownload() {
        if (InetChecker.isInternetConnectionAvailable(this)) {
            apiManager.prepare();
        } else {
            showCheckConnectionDialog();
        }
    }

    private void initApiManager() {
        apiManager = new ApiManager(getApplicationContext());
        apiManager.setPrepareCallback(new ApiManager.PrepareCallback() {
            @Override
            public void managerIsReady() {
                if (apiManager.needUpdate()) {
                    apiManager.downloadContent(eventListener);
                } else {
                    apiManager.fetchAllLevels();
                }
            }

            @Override
            public void prepareFailed() {
                showCheckConnectionDialog();
            }

            @Override
            public void dataIsReady() {
                runMainActivity();
            }
        });
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
        switch (event.getId()) {
            case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                SharedPrefUtils.setLastUpdate(getApplicationContext(), apiManager.getLastModelUpdate());
                apiManager.fetchAllLevels();
                break;
            case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                showCheckConnectionDialog();
                break;
            case AppModel.ChangeEvent.DOWNLOAD_FILE_CHANGED_ID:
                String progress = "Downloading " + event.getMessage() + "...";
                tvProgress.setText(progress);
                break;
        }
    }

    private void runMainActivity() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                intent.putExtra(Constants.ALL_LEVELS_MODEL_ARG, apiManager.getAllLevelsModel());
                startActivity(intent);
                finish();
            }
        });
    }

    private synchronized void showCheckConnectionDialog() {
        if (!errorDialogIsShown && !activityIsDestroyed) {
            // SDK can produce a lot of errors at the same time, so we have to avoid such circumstances
            errorDialogIsShown = true;
            new AlertDialog.Builder(this)
                    .setMessage(getString(R.string.error_check_inet_connection))
                    .setPositiveButton(R.string.error_check_inet_connection_retry_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            errorDialogIsShown = false;
                            startDownload();
                        }
                    })
                    .setNegativeButton(R.string.error_check_inet_connection_exit_btn, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            errorDialogIsShown = false;
                            finish();
                        }
                    })
                    .setCancelable(false)
                    .create()
                    .show();
        }
    }

    @Override
    public void onBackPressed() {
        // Do nothing on this screen
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        activityIsDestroyed = true;
        eventListener = null;
    }
}
