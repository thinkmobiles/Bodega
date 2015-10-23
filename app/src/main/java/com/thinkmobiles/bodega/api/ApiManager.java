package com.thinkmobiles.bodega.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import com.cristaliza.mvc.controllers.estrella.MainController;
import com.cristaliza.mvc.controllers.estrella.MainViewListener;
import com.cristaliza.mvc.events.Event;
import com.cristaliza.mvc.events.EventListener;
import com.cristaliza.mvc.models.estrella.AppModel;
import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;
import com.thinkmobiles.bodega.Constants;
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

    public static String getPath(Context context) {
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
        File f = new File(getPath(context));
        if (!f.exists())
            f.mkdirs();
        controller.downloadAllAppData(listener, getPath(context));
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
        model.setOfflinePath(getPath(context));
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.FIRST_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.SECOND_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.THIRD_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.FOURTH_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.PRODUCTS_CHANGED, levelsEventListener);
        controller.onExecuteWSFirstLevel();
    }

    private Item current1tLevelItem, current2tLevelItem, current3tLevelItem, current4tLevelItem;
    private HashMap<String, List<ItemWrapper>> secondLevel, thirdLevel, fourthLevel;
    private int levelsCounter = -1;

    private EventListener levelsEventListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            //Log.d(LOG_TAG, event.getType() + " : " + event.getId() + " : " + event.getMessage());
            switch (event.getType()) {
                case AppModel.ChangeEvent.FIRST_LEVEL_CHANGED:
                    levelsCounter = getFirstLevelList().size();
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
                    secondLevel.put(current1tLevelItem.getId(), AllLevelsModel.getWrappedList(getSecondLevelList()));
                    levelsCounter += getSecondLevelList().size();
                    for (Item item : getSecondLevelList()) {
                        current2tLevelItem = item;
                        //Log.d(LOG_TAG, "\t--" + item.getName());
                        controller.onExecuteWSThirdLevel(item);
                        //controller.onExecuteWSProducts(item);
                        levelsCounter--;
                    }
                    break;
                case AppModel.ChangeEvent.THIRD_LEVEL_CHANGED:
                    if (thirdLevel == null)
                        thirdLevel = new HashMap<>();
                    thirdLevel.put(current2tLevelItem.getId(), AllLevelsModel.getWrappedList(getThirdLevelList()));
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
                    fourthLevel.put(current3tLevelItem.getId(), AllLevelsModel.getWrappedList(getFourthLevelList()));
                    levelsCounter += getFourthLevelList().size();
                    for (Item item : getFourthLevelList()) {
                        current4tLevelItem = item;
                        //Log.d(LOG_TAG, "\t\t\t--" + item.getName());
                        levelsCounter--;
                    }
                    break;
                case AppModel.ChangeEvent.PRODUCTS_CHANGED:
                    /*if (getProductsList() != null) {
                        List<Product> productList = getProductsList();
                        Log.d(LOG_TAG, "prod size: " + productList.size());
                        for (Product product : productList) {
                            Log.d(LOG_TAG, "" + product.getName());

                        }
                    }*/
                    break;
            }
            checkLoadingExecutionEnd();
        }
    };

    private void checkLoadingExecutionEnd() {
        if (levelsCounter == 0 && prepareCallback != null) {
            model.removeListeners();
            prepareCallback.dataIsReady();
        }
    }

    public AllLevelsModel getAllLevelsModel() {
        AllLevelsModel allLevelsModel = new AllLevelsModel();
        allLevelsModel.setFirstLevelList(AllLevelsModel.getWrappedList(model.getFirstLevel()));
        allLevelsModel.setSecondLevel(secondLevel);
        allLevelsModel.setThirdLevel(thirdLevel);
        allLevelsModel.setFourthLevel(fourthLevel);
        return allLevelsModel;
    }

    private List<Item> getFirstLevelList() {
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

    private List<Product> getProductsList() {
        return model.getProducts();
    }

    public void setPrepareCallback(PrepareCallback prepareCallback) {
        this.prepareCallback = prepareCallback;
    }

    public interface PrepareCallback {
        void managerIsReady();
        void dataIsReady();
    }
}
