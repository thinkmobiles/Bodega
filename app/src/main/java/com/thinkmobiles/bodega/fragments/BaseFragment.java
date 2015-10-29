package com.thinkmobiles.bodega.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.activities.MainActivity;
import com.thinkmobiles.bodega.api.AllLevelsModel;
import com.thinkmobiles.bodega.controllers.FragmentNavigator;

public abstract class BaseFragment extends Fragment {

    //____________________ Protected variables ___________________//
    protected MainActivity              mActivity;
    protected View                      inflatedView;
    protected FragmentNavigator         mFragmentNavigator;
    protected AllLevelsModel            allLevelsModel;

    //_____________________ Private variables ____________________//
    private int             fragmentResId = -1;

    /**
     * The method sets resources into fragment.
     * @param resId - resource for inflating view in fragment
     */
    protected void setContentView(@LayoutRes int resId){
        this.fragmentResId = resId;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mActivity = (MainActivity) getActivity();
        mFragmentNavigator = mActivity.getFragmentNavigator();
        allLevelsModel = mActivity.getAllLevelsModel();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (fragmentResId != -1) {
            inflatedView = inflater.inflate(fragmentResId, container, false);
            return inflatedView;
        }
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    protected void setActionBarTitle(String _title) {
        mActivity.setActionBarTitle(_title);
    }

    protected Context getApplicationContext() {
        return mActivity.getApplicationContext();
    }

    protected void setDefaultBackground() {
        mActivity.setBackground(R.drawable.background);
    }

    protected void setRedBackground() {
        mActivity.setBackground(R.drawable.background_red_a);
    }

    /**
     * The method finding view in parent view
     */
    @SuppressWarnings("unchecked")
    protected <T extends View> T $(@IdRes int resId){
        if (inflatedView == null )
            return null;

        return (T) inflatedView.findViewById(resId);
    }

}
