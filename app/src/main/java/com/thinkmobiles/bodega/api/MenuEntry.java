package com.thinkmobiles.bodega.api;

/**
 * Created by illia on 23.10.15.
 */
public class MenuEntry {
    private String mName;
    private String mId;
    private int mLevel;

    public MenuEntry(String _id, String _name, int _level) {
        this.mName = _name;
        this.mId = _id;
        this.mLevel = _level;
    }

    public String getName() {
        return mName;
    }

    public void setName(String _name) {
        mName = _name;
    }

    public String getId() {
        return mId;
    }

    public void setId(String _id) {
        mId = _id;
    }

    public int getLevel() {
        return mLevel;
    }

    public void setLevel(int _level) {
        mLevel = _level;
    }
}
