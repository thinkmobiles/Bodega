package com.thinkmobiles.bodega.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiService;
import com.thinkmobiles.bodega.api.ItemWrapper;

import java.util.ArrayList;

/**
 * Created by BogDan on 10/19/2015.
 */
public class SplashActivity extends AppCompatActivity {

    private static final String LOG_TAG = "SplashActivitySDK";

    private TextView tvProgress;
    private boolean errorDialogIsShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvProgress = (TextView) findViewById(R.id.tvProgress_AS);
    }

    @Override
    protected void onResume() {
        super.onResume();

        registerReceiver();
        startDownload();
    }

    private void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION_DOWNLOAD_SUCCESS);
        intentFilter.addAction(Constants.ACTION_DOWNLOAD_FAILED);
        intentFilter.addAction(Constants.ACTION_FILE_DOWNLOADED);
        registerReceiver(mBroadcastReceiver, intentFilter);
    }

    private void startDownload() {
        startService(new Intent(this, ApiService.class));
    }

    private void runMainActivity(Intent intent) {
        Intent sendIntent = new Intent(this, MainActivity.class);
        ArrayList<ItemWrapper> allLevelsModel = intent.getParcelableArrayListExtra(Constants.ALL_LEVELS_MODEL_ARG);
        int size = allLevelsModel.size();
        sendIntent.putExtra(Constants.ALL_LEVELS_MODEL_ARG, allLevelsModel);
        startActivity(sendIntent);
        finish();
    }

    private synchronized void showCheckConnectionDialog() {
        if (!errorDialogIsShown) {
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

    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(LOG_TAG, action);
            switch (action) {
                case Constants.ACTION_DOWNLOAD_SUCCESS:
                    runMainActivity(intent);
                    break;
                case Constants.ACTION_DOWNLOAD_FAILED:
                    showCheckConnectionDialog();
                    break;
                case Constants.ACTION_FILE_DOWNLOADED:
                    tvProgress.setText(intent.getStringExtra(Constants.DOWNLOADED_FILE_ARG));
                    break;
            }
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(mBroadcastReceiver);
    }
}
