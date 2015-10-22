package com.thinkmobiles.bodega.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.cristaliza.mvc.controllers.estrella.MainController;
import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.thinkmobiles.bodega.utils.SharedPrefUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 20.10.15.
 */
public class ApiManager {

    private static final String LOG_TAG = "ApiManagerSDK";

    private Context context;
    private AppModel model;
    private MainViewListener controller;
    private PrepareCallback prepareCallback;
    private LoadDataCallback loadDataCallback;

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
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.LAST_UPDATE_CHANGED, prepareEventListener);
        model.addListener(AppModel.ChangeEvent.APP_CONFIG_CHANGED, prepareEventListener);
        controller.onExecuteWSAppConfig();
    }

    private String getPath() {
        //Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName();
        return context.getDir(context.getPackageName(), Context.MODE_PRIVATE).getAbsolutePath() + "/" + context.getPackageName();
    }

    private EventListener prepareEventListener = new EventListener() {
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
        Log.d(LOG_TAG, "last update performed: " + SharedPrefUtils.getLastUpdate(context) + "; server update available: " + getLastModelUpdate());
        return !SharedPrefUtils.getLastUpdate(context).equals(getLastModelUpdate());
    }

    public String getLastModelUpdate() {
        return model.getLastUpdate();
    }

    public void getAllLevels() {
        controller.setSynchronousMode();
        model.setOnlineMode(false);
        model.setOfflinePath(getPath());
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.FIRST_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.SECOND_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.THIRD_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.FOURTH_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.PRODUCTS_CHANGED, levelsEventListener);
        controller.onExecuteWSFirstLevel();
    }

    private Item current1tLevelItem, current2tLevelItem, current3tLevelItem, current4tLevelItem;
    private Map<String, List<Item>> secondLevel, thirdLevel, fourthLevel;
    //private int firstLevelCounter, secondLevelCounter, thirdLevelCounter, fourthLevelCounter;
    private int levelsCounter;

    private EventListener levelsEventListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            //Log.d(LOG_TAG, event.getType() + " : " + event.getId() + " : " + event.getMessage());
            switch (event.getType()) {
                case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED:
                    levelsCounter += getFirstLevelList().size();
                    for (Item item : getFirstLevelList()) {
                        current1tLevelItem = item;
                        //Log.d(LOG_TAG, "" + item.getName());
                        controller.onExecuteWSSecondLevel(item);
                        levelsCounter--;
                    }
                    break;
                case AppModel.ChangeEvent.SECOND_LEVEL_CHANGED:
                    if (secondLevel == null)
                        secondLevel = new HashMap<>();
                    secondLevel.put(current1tLevelItem.getId(), getSecondLevelList());
                    levelsCounter += getSecondLevelList().size();
                    for (Item item : getSecondLevelList()) {
                        current2tLevelItem = item;
                        //Log.d(LOG_TAG, "\t--" + item.getName());
                        controller.onExecuteWSThirdLevel(item);
                        levelsCounter--;
                    }
                    break;
                case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED:
                    if (thirdLevel == null)
                        thirdLevel = new HashMap<>();
                    thirdLevel.put(current2tLevelItem.getId(), getThirdLevelList());
                    levelsCounter += getThirdLevelList().size();
                    for (Item item : getThirdLevelList()) {
                        current3tLevelItem = item;
                        //Log.d(LOG_TAG, "\t\t--" + item.getName());
                        controller.onExecuteWSFourthLevel(item);
                        levelsCounter--;
                    }
                    break;
                case AppModel.ChangeEvent.FOURTH_LEVEL_CHANGED:
                    if (fourthLevel == null)
                        fourthLevel = new HashMap<>();
                    fourthLevel.put(current3tLevelItem.getId(), getFourthLevelList());
                    levelsCounter += getFourthLevelList().size();
                    for (Item item : getFourthLevelList()) {
                        current4tLevelItem = item;
                        //Log.d(LOG_TAG, "\t\t\t--" + item.getName());
                        levelsCounter--;
                    }
                    break;
                case AppModel.ChangeEvent.PRODUCTS_CHANGED:
                    break;
            }
            checkLoadingExecutionEnd();
        }
    };

    private void checkLoadingExecutionEnd() {
        if (levelsCounter == 0 && loadDataCallback != null) {
            model.removeListeners();
            loadDataCallback.dataIsLoaded();
        }
    }

    public List<Item> getFirstLevelList() {
        return model.getFirstLevel();
    }

    private List<Item> getSecondLevelList() {
        return model.getSecondLevel();
    }

    private List<Item> getThirdLevelList() {
        return model.getThirdLevel();
    }

    private List<Item> getFourthLevelList() {
        return model.getFourthLevel();
    }

    public List<Item> getSecondLevelListById(String id) {
        return secondLevel.get(id);
    }

    public List<Item> getThirdLevelListById(String id) {
        return thirdLevel.get(id);
    }

    public List<Item> getFourthLevelListById(String id) {
        return fourthLevel.get(id);
    }

    private List<Product> getProductsList() {
        return model.getProducts();
    }

    public void setPrepareCallback(PrepareCallback prepareCallback) {
        this.prepareCallback = prepareCallback;
    }

    public void setLoadDataCallback(LoadDataCallback loadDataCallback) {
        this.loadDataCallback = loadDataCallback;
    }

    public interface LoadDataCallback {
        void dataIsLoaded();
    }

    public interface PrepareCallback {
        void managerIsReady();
    }
}
