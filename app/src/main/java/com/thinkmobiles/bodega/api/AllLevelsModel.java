package com.thinkmobiles.bodega.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.cristaliza.mvc.models.estrella.Item;
import com.cristaliza.mvc.models.estrella.Product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by denis on 23.10.15.
 */
public class AllLevelsModel implements Parcelable {

    private List<ItemWrapper> firstLevel;
    private HashMap<String, List<ItemWrapper>> secondLevel, thirdLevel, fourthLevel;
    public HashMap<String, List<ProductWrapper>> firstLevelProducts, secondLevelProducts, thirdLevelProducts, fourthLevelProducts;


    public AllLevelsModel() {
    }

    public List<ItemWrapper> getFirstLevelList() {
        return firstLevel;
    }

    // PRODUCTS
    public void setFirstLevelProducts(HashMap<String, List<ProductWrapper>> firstLevelProducts) {
        this.firstLevelProducts = firstLevelProducts;
    }

    public void setSecondLevelProducts(HashMap<String, List<ProductWrapper>> secondLevelProducts) {
        this.secondLevelProducts = secondLevelProducts;
    }

    public void setThirdLevelProducts(HashMap<String, List<ProductWrapper>> thirdLevelProducts) {
        this.thirdLevelProducts = thirdLevelProducts;
    }

    public void setFourthLevelProducts(HashMap<String, List<ProductWrapper>> fourthLevelProducts) {
        this.fourthLevelProducts = fourthLevelProducts;
    }

    public List<ProductWrapper> getFirstLevelProducts(String id) {
        return firstLevelProducts.get(id);
    }

    public List<ProductWrapper> getSecondLevelProducts(String id) {
        return secondLevelProducts.get(id);
    }

    public List<ProductWrapper> getThirdLevelProducts(String id) {
        return thirdLevelProducts.get(id);
    }

    public List<ProductWrapper> getFourthLevelProducts(String id) {
        return fourthLevelProducts.get(id);
    }

    // LEVELS
    public List<ItemWrapper> getSecondLevelListById(String id) {
        return secondLevel.get(id);
    }

    public List<ItemWrapper> getThirdLevelListById(String id) {
        return thirdLevel.get(id);
    }

    public List<ItemWrapper> getFourthLevelListById(String id) {
        return fourthLevel.get(id);
    }

    public void setFirstLevelList(List<ItemWrapper> list) {
        firstLevel = list;
    }

    public void setSecondLevel(HashMap<String, List<ItemWrapper>> secondLevel) {
        this.secondLevel = secondLevel;
    }

    public void setThirdLevel(HashMap<String, List<ItemWrapper>> thirdLevel) {
        this.thirdLevel = thirdLevel;
    }

    public void setFourthLevel(HashMap<String, List<ItemWrapper>> fourthLevel) {
        this.fourthLevel = fourthLevel;
    }

    public static List<ItemWrapper> getWrappedLevelList(List<Item> itemsList) {
        List<ItemWrapper> wrappedList = new ArrayList<>();
        for (Item item : itemsList) {
            wrappedList.add(new ItemWrapper(item));
        }
        return wrappedList;
    }

    public static List<ProductWrapper> getWrappedProductsList(List<Product> productsList) {
        List<ProductWrapper> wrappedList = new ArrayList<>();
        for (Product product : productsList) {
            wrappedList.add(new ProductWrapper(product));
        }
        return wrappedList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.firstLevel);
        dest.writeSerializable(this.secondLevel);
        dest.writeSerializable(this.thirdLevel);
        dest.writeSerializable(this.fourthLevel);
        dest.writeSerializable(this.firstLevelProducts);
        dest.writeSerializable(this.secondLevelProducts);
        dest.writeSerializable(this.thirdLevelProducts);
        dest.writeSerializable(this.fourthLevelProducts);
    }

    protected AllLevelsModel(Parcel in) {
        this.firstLevel = new ArrayList<ItemWrapper>();
        in.readList(this.firstLevel, List.class.getClassLoader());
        this.secondLevel = (HashMap<String, List<ItemWrapper>>) in.readSerializable();
        this.thirdLevel = (HashMap<String, List<ItemWrapper>>) in.readSerializable();
        this.fourthLevel = (HashMap<String, List<ItemWrapper>>) in.readSerializable();
        this.firstLevelProducts = (HashMap<String, List<ProductWrapper>>) in.readSerializable();
        this.secondLevelProducts = (HashMap<String, List<ProductWrapper>>) in.readSerializable();
        this.thirdLevelProducts = (HashMap<String, List<ProductWrapper>>) in.readSerializable();
        this.fourthLevelProducts = (HashMap<String, List<ProductWrapper>>) in.readSerializable();
    }

    public static final Creator<AllLevelsModel> CREATOR = new Creator<AllLevelsModel>() {
        public AllLevelsModel createFromParcel(Parcel source) {
            return new AllLevelsModel(source);
        }

        public AllLevelsModel[] newArray(int size) {
            return new AllLevelsModel[size];
        }
    };
}
