package com.thinkmobiles.bodega.api;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.utils.InetChecker;
import com.thinkmobiles.bodega.utils.SharedPrefUtils;

/**
 * Created by denis on 30.10.15.
 */
public class ApiService extends IntentService {

    private ApiManager apiManager;
    private static final String LOG_TAG = "ApiService";
    private boolean alreadyExecuted = false;

    public ApiService() {
        super("ApiService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(LOG_TAG, "onCreate");

    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // Return all pending intent from query
        if (alreadyExecuted)
            return;
        alreadyExecuted = true;
        Log.d(LOG_TAG, "OnHandleIntent");
        initApiManager();
        startDownload();
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
                sendBroadcast(Constants.ACTION_DOWNLOAD_FAILED);
            }

            @Override
            public void dataIsReady() {
                sendBroadcast(Constants.ACTION_DOWNLOAD_SUCCESS);
            }
        });
    }

    private void startDownload() {
        if (InetChecker.isInternetConnectionAvailable(this)) {
            apiManager.prepare();
        } else {
            sendBroadcast(Constants.ACTION_DOWNLOAD_FAILED);
        }
    }

    private EventListener eventListener = new EventListener() {
        @Override
        public void onEvent(final Event event) {
            Log.d(LOG_TAG, event.getType() + " : " + event.getId() + " : " + event.getMessage());
            performEventListenerAction(event);
        }
    };

    private void performEventListenerAction(Event event) {
        switch (event.getId()) {
            case AppModel.ChangeEvent.DOWNLOAD_ALL_CHANGED_ID:
                SharedPrefUtils.setLastUpdate(getApplicationContext(), apiManager.getLastModelUpdate());
                apiManager.fetchAllLevels();
                break;
            case AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID:
                sendBroadcast(Constants.ACTION_DOWNLOAD_FAILED);
                break;
            case AppModel.ChangeEvent.DOWNLOAD_FILE_CHANGED_ID:
                String progress = "Downloading " + event.getMessage() + "...";
                Intent intent = new Intent(Constants.ACTION_FILE_DOWNLOADED);
                intent.putExtra(Constants.DOWNLOADED_FILE_ARG, progress);
                intent.setPackage(getPackageName());
                sendBroadcast(intent);
                //Log.d(LOG_TAG, progress);
                break;
        }
    }

    private void sendBroadcast(String action) {
        Intent intent = new Intent(action);
        intent.putExtra(Constants.ALL_LEVELS_MODEL_ARG, apiManager.getAllLevelsModel());
        intent.setPackage(getPackageName());
        sendBroadcast(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(LOG_TAG, "onDestroy");

    }
}
