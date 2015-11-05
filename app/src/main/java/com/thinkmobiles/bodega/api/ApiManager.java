package com.thinkmobiles.bodega.api;

import android.content.Context;
import android.os.Environment;
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
import java.util.List;

/**
 * Created by denis on 20.10.15.
 */
public class ApiManager {

    private static final String LOG_TAG = "ApiManagerSDK";

    private static final int FILE_DOWNLOAD_TIMEOUT = 20000;

    private Context context;
    private AppModel model;
    private MainViewListener controller;
    private PrepareCallback prepareCallback;
    private List<ItemWrapper> allLevelsList;

    public ApiManager(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        model = AppModel.getInstance();
        model.setFileDownloadTimeout(FILE_DOWNLOAD_TIMEOUT);
        controller = new MainController(model);
        controller.setAppBodega();
        controller.setSynchronousMode();
        // TODO set correct environment
        controller.setDevelopmentEnvironment();
    }

    public void prepare() {
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.LAST_UPDATE_CHANGED, prepareEventListener);
        model.addListener(AppModel.ChangeEvent.APP_CONFIG_CHANGED, prepareEventListener);
        controller.onExecuteWSAppConfig();
    }

    public static String getPath(Context context) {
        //return Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + context.getPackageName();
        return context.getDir(context.getPackageName(), Context.MODE_PRIVATE).getAbsolutePath() + "/" + context.getPackageName();
    }

    private EventListener prepareEventListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
            Log.d(LOG_TAG, event.getType() + " : " + event.getId() + " : " + event.getMessage());
            switch (event.getType()) {
                case AppModel.ChangeEvent.APP_CONFIG_CHANGED:
                    // If app config hasn't been changed, we'll receive ID that corresponds to the error code (-1)
                    // so we should check event by it's type, because unchanged config fits to our needs
                    controller.onExecuteWSAppLastUpdate();
                    break;
                case AppModel.ChangeEvent.LAST_UPDATE_CHANGED:
                    if (prepareCallback != null) {
                        String update = getLastModelUpdate();
                        if (update == null || update.isEmpty() || event.getId() == AppModel.ChangeEvent.ON_EXECUTE_ERROR_ID)
                            prepareCallback.prepareFailed();
                        else
                            prepareCallback.managerIsReady();
                    }
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
        File file = new File(getPath(context));
        return !SharedPrefUtils.getLastUpdate(context).equals(getLastModelUpdate()) || !file.exists();
    }

    public String getLastModelUpdate() {
        return model.getLastUpdate();
    }

    public void fetchAllLevels() {
        controller.setSynchronousMode();
        model.setOnlineMode(false);
        model.setOfflinePath(getPath(context));
        model.removeListeners();
        model.addListener(AppModel.ChangeEvent.FIRST_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.SECOND_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.THIRD_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.FOURTH_LEVEL_CHANGED, levelsEventListener);
        model.addListener(AppModel.ChangeEvent.PRODUCTS_CHANGED, levelsEventListener);
        allLevelsList = loadNestedLevel(1, null);
        model.removeListeners();
        if (prepareCallback != null)
            prepareCallback.dataIsReady();
    }

    public ArrayList<ItemWrapper> getAllLevelsList() {
        if (allLevelsList == null)
            return null;
        return new ArrayList<>(allLevelsList);
    }

    private List<ItemWrapper> loadNestedLevel(int level, Item nestedItem) {
        List<Item> originalList = getListByLevelNum(level, nestedItem);
        List<ItemWrapper> wrappedList = WrapperUtils.getWrappedLevelList(originalList);
        if (originalList != null) {
            for (int i = 0; i < originalList.size(); i++) {
                Item item = originalList.get(i);
                int nextLevel = level + 1;
                List<ItemWrapper> innerLevel = loadNestedLevel(nextLevel, originalList.get(i));
                ItemWrapper itemWrapper = wrappedList.get(i);
                itemWrapper.setLevelNumber(level);
                itemWrapper.setInnerLevel(innerLevel);
                itemWrapper.setProductList(WrapperUtils.getWrappedProductsList(getProductsList(item)));
            }
        }
        return wrappedList;
    }

    private List<Item> getListByLevelNum(int level, Item item) {
        List<Item> originalList = null;
        switch (level) {
            case 1:
                originalList = getFirstLevelList();
                break;
            case 2:
                originalList = getSecondLevelList(item);
                break;
            case 3:
                originalList = getThirdLevelList(item);
                break;
            case 4:
                originalList = getFourthLevelList(item);
                break;
        }
        return originalList;
    }

    private EventListener levelsEventListener = new EventListener() {
        @Override
        public void onEvent(Event event) {
        }
    };

    private List<Item> getFirstLevelList() {
        controller.onExecuteWSFirstLevel();
        return model.getFirstLevel();
    }

    private List<Item> getSecondLevelList(Item item) {
        controller.onExecuteWSSecondLevel(item);
        return model.getSecondLevel();
    }

    private List<Item> getThirdLevelList(Item item) {
        controller.onExecuteWSThirdLevel(item);
        return model.getThirdLevel();
    }

    private List<Item> getFourthLevelList(Item item) {
        controller.onExecuteWSFourthLevel(item);
        return model.getFourthLevel();
    }

    private List<Product> getProductsList(Item item) {
        controller.onExecuteWSProducts(item);
        return model.getProducts();
    }

    public void setPrepareCallback(PrepareCallback prepareCallback) {
        this.prepareCallback = prepareCallback;
    }

    public interface PrepareCallback {
        void managerIsReady();
        void prepareFailed();
        void dataIsReady();
    }
}
