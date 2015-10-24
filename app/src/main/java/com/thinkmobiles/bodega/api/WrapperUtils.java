package com.thinkmobiles.bodega.api;

import android.util.Log;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 23.10.15.
 */
public class WrapperUtils {

    private static final String LOG_TAG = "ApiManagerSDK";

    public static List<ItemWrapper> getWrappedLevelList(List<Item> itemsList) {
        if (itemsList == null)
            return null;
        List<ItemWrapper> wrappedList = new ArrayList<>();
        for (Item item : itemsList) {
            wrappedList.add(new ItemWrapper(item));
        }
        return wrappedList;
    }

    public static List<ProductWrapper> getWrappedProductsList(List<Product> productsList) {
        if (productsList == null)
            return null;
        List<ProductWrapper> wrappedList = new ArrayList<>();
        for (Product product : productsList) {
            wrappedList.add(new ProductWrapper(product));
        }
        return wrappedList;
    }

    public static void printWrappedLevels(List<ItemWrapper> wrappedList) {
        if (wrappedList != null) {
            for (ItemWrapper itemWrapper : wrappedList) {
                String tab = "";
                for (int j = 1; j < itemWrapper.getLevelNumber(); j++) {
                    tab += "\t";
                }
                Log.d(LOG_TAG, tab + itemWrapper.getName());
                printWrappedLevels(itemWrapper.getInnerLevel());
            }
        }
    }

}
