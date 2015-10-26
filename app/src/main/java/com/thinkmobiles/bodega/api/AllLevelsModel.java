package com.thinkmobiles.bodega.api;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by denis on 24.10.15.
 */
public class AllLevelsModel implements Parcelable {

    private List<ItemWrapper> allLevelsList;

    public AllLevelsModel() {
    }

    public AllLevelsModel(List<ItemWrapper> allLevelsList) {
        this.allLevelsList = allLevelsList;
    }

    public List<ItemWrapper> getAllLevelsList() {
        return allLevelsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.allLevelsList);
    }

    protected AllLevelsModel(Parcel in) {
        this.allLevelsList = new ArrayList<ItemWrapper>();
        in.readList(this.allLevelsList, List.class.getClassLoader());
    }

    public static final Parcelable.Creator<AllLevelsModel> CREATOR = new Parcelable.Creator<AllLevelsModel>() {
        public AllLevelsModel createFromParcel(Parcel source) {
            return new AllLevelsModel(source);
        }

        public AllLevelsModel[] newArray(int size) {
            return new AllLevelsModel[size];
        }
    };
}
