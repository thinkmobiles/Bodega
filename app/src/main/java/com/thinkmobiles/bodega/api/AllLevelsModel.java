package com.thinkmobiles.bodega.api;

import android.os.Parcel;
import android.os.Parcelable;

import com.cristaliza.mvc.models.estrella.Item;

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

    public AllLevelsModel() {
    }

    public List<ItemWrapper> getFirstLevelList() {
        return firstLevel;
    }

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

    public static List<ItemWrapper> getWrappedList(List<Item> itemsList) {
        List<ItemWrapper> wrappedList = new ArrayList<>();
        for (Item item : itemsList) {
            wrappedList.add(new ItemWrapper(item));
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
    }

    protected AllLevelsModel(Parcel in) {
        this.firstLevel = new ArrayList<>();
        in.readList(this.firstLevel, List.class.getClassLoader());
        this.secondLevel = (HashMap<String, List<ItemWrapper>>) in.readSerializable();
        this.thirdLevel = (HashMap<String, List<ItemWrapper>>) in.readSerializable();
        this.fourthLevel = (HashMap<String, List<ItemWrapper>>) in.readSerializable();
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
