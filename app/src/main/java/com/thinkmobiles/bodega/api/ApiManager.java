package com.thinkmobiles.bodega.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;

import com.cristaliza.mvc.commands.estrella.AppConfigImpl;
import com.cristaliza.mvc.commands.estrella.LastUpdateImpl;
import com.cristaliza.mvc.controllers.estrella.MainController;
import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppConfig;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.thinkmobiles.bodega.utils.SharedPrefUtils;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by denis on 20.10.15.
 */
public class ApiManager {

    private static final String LOG_TAG = "ApiManagerSDK";

    private Context context;
    private AppModel model;
    private MainViewListener controller;
    private PrepareCallback prepareCallback;

    public ApiManager(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        model = AppModel.getInstance();
        controller = new MainController(model);
        controller.setAppBodega();
        controller.setAsynchronousMode();
        controller.setProductionEnvironment();
    }

    public void prepare() {
        model.addListener(AppModel.ChangeEvent.LAST_UPDATE_CHANGED, eventListener);
        model.addListener(AppModel.ChangeEvent.APP_CONFIG_CHANGED, eventListener);
        controller.onExecuteWSAppConfig();
    }

    private String getPath() {
        //Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName();
        return context.getDir(context.getPackageName(), Context.MODE_PRIVATE).getAbsolutePath() + "/" + context.getPackageName();
    }

    private EventListener eventListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            Log.d(LOG_TAG, event.getType() + " : " + event.getId() + " : " + event.getMessage());
            switch (event.getType()) {
                case AppModel.ChangeEvent.APP_CONFIG_CHANGED:
                    controller.onExecuteWSAppLastUpdate();
                    break;
                case AppModel.ChangeEvent.LAST_UPDATE_CHANGED:
                    if (prepareCallback != null)
                        prepareCallback.managerIsReady();
                    break;
            }
        }
    };

    public void downloadContent(EventListener listener) {
        File f = new File(getPath());
        if (!f.exists())
            f.mkdirs();
        controller.downloadAllAppData(listener, getPath());
    }

    public boolean needUpdate() {
        Log.d(LOG_TAG, "last date: " + SharedPrefUtils.getLastUpdate(context) + "; last update: " + getLastModelUpdate());
        return !SharedPrefUtils.getLastUpdate(context).equals(getLastModelUpdate());
    }

    public String getLastModelUpdate() {
        return model.getLastUpdate();
    }

    public void setOfflineMode() {
        controller.setSynchronousMode();
        model.setOfflinePath(getPath());
    }

    public void getFirstLevel(EventListener listener) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.FIRST_LEVEL_CHANGED, listener);
        controller.onExecuteWSFirstLevel();
    }

    public void getSecondLevel(EventListener listener, Item item) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.SECOND_LEVEL_CHANGED, listener);
        controller.onExecuteWSSecondLevel(item);
    }

    public void getThirdLevel(EventListener listener, Item item) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.THIRD_LEVEL_CHANGED, listener);
        controller.onExecuteWSThirdLevel(item);
    }

    public void getLastUpdateServer(EventListener listener) {
        model.removeListeners();
        controller.setSynchronousMode();
        model.addListener(AppModel.ChangeEvent.LAST_UPDATE_CHANGED, listener);
        controller.onExecuteWSAppLastUpdate();
    }

    public List<Item> getFirstList() {
        List<Item> list = model.getFirstLevel();

        /*for (int i = 0; i < list.size(); ++i) {
            for (int j = i + 1; j < list.size(); ++j) {
                if (list.get(j).getId().trim().compareTo(list.get(i).getId().trim()) < 0) {
                    Item temp = list.get(j);
                    list.set(j, list.get(i));
                    list.set(i, temp);
                }
            }
        }*/
        return list;
    }

    public List<Item> getSecondList() {
        return model.getSecondLevel();
    }

    public List<Item> getThirdList() {
        return model.getThirdLevel();
    }

    public List<Product> getProductsList() {
        return model.getProducts();
    }

    public void getProducts(EventListener listener, Item item) {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.PRODUCTS_CHANGED, listener);
        controller.onExecuteWSProducts(item);
    }

    public final boolean isInternetConnectionAvailable(final Context _context) {
        final ConnectivityManager connectivityManager = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void finalizeManager() {
        model.removeListeners();
    }

    public void setPrepareCallback(PrepareCallback prepareCallback) {
        this.prepareCallback = prepareCallback;
    }

    public interface PrepareCallback {
        void managerIsReady();
    }
}
