package com.thinkmobiles.bodega.controllers;

/**
 * Created by illia on 22.10.15.
 */

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.fragments.LevelTwoFragment;

public final class FragmentNavigator  {

    //_____________________ Private variables ____________________//
    private FragmentManager mFragmentManager;
    private int              mContainerId;

    /**
     * The method register this class in your activity or fragment
     * @param fragmentManager - fragment manager or child fragment manager
     * @param containerId - id container for fragments
     */
    public void register(FragmentManager fragmentManager, int containerId){
        this.mFragmentManager    = fragmentManager;
        this.mContainerId        = containerId;
    }

    /**
     * The method clear all fragments in fragment manager
     */
    public void clearAllFragments() {
        int entryCount = mFragmentManager.getBackStackEntryCount();
        if (entryCount <= 0)
            return;

        FragmentManager.BackStackEntry entry = mFragmentManager.getBackStackEntryAt(0);
        int id = entry.getId();
        mFragmentManager.popBackStackImmediate(id, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

//    public void clearBackStackToFragmentOrShow(BaseFragment _baseFragment) {
//        int entryCount = mFragmentManager.getBackStackEntryCount();
//        String tag = _baseFragment.getClass().toString();
//        FragmentManager.BackStackEntry searchedEntry = null;
//        for (int i = 0; i < entryCount; ++i) {
//            FragmentManager.BackStackEntry entry = mFragmentManager.getBackStackEntryAt(i);
//            if (entry.getName().equals(tag)) {
//                searchedEntry = entry;
//                break;
//            }
//        }
//
//        if (searchedEntry == null)  {
//            showFragment(_baseFragment);
//        } else {
//            mFragmentManager.popBackStackImmediate(searchedEntry.getId(), 0);
//        }
//    }

    /**
     * The method return true - if back stack not empty and true another. And call back stack in fragment manager
     */
    public boolean popBackStack(){
        return mFragmentManager.popBackStackImmediate();
    }

    /**
     * The method work replace fragment with back stack (key = class name fragment)
     * @param baseFragment - fragment for adding
     */
    public void showFragment(BaseFragment baseFragment){
        if (mFragmentManager != null)
            getTransaction()
                    .replace(mContainerId, baseFragment)
                    .addToBackStack(baseFragment.getClass().toString())
                    .commit();
    }

    /**
     * The method work replace fragment without back stack
     * @param baseFragment - fragment for replacing
     */
    public void replaceFragment(BaseFragment baseFragment){
        if (mFragmentManager != null)
            getTransaction()
                    .replace(mContainerId, baseFragment)
                    .commit();
    }

    public void addFragment(BaseFragment baseFragment) {
        if (mFragmentManager != null)
            getTransaction()
                    .add(mContainerId, baseFragment)
                    .addToBackStack(baseFragment.getClass().getSimpleName())
                    .commit();
    }

    public void showFragmentWithoutBackStack(BaseFragment baseFragment) {
        if (mFragmentManager != null) {
            clearAllFragments();
            replaceFragment(baseFragment);
        }
    }

//    public void showFragmentWithAnimation(BaseFragment _baseFragment) {
//        if (mFragmentManager == null)
//            return;
//
//
//        getTransaction()
//                .setCustomAnimations(R.anim.slide_left_enter, R.anim.slide_left,
//                        R.anim.slide_right_enter, R.anim.slide_right)
//                .replace(mContainerId, _baseFragment)
//                .addToBackStack(_baseFragment.getClass().toString())
//                .commit();
//
//    }
//
//    public void showFragmentWithAnimationBottomToTop(BaseFragment _baseFragment) {
//        if (mFragmentManager == null)
//            return;
//
//
//        getTransaction()
//                .setCustomAnimations(R.anim.slide_top_enter, R.anim.slide_top,
//                        R.anim.slide_down_enter, R.anim.slide_down)
//                .replace(mContainerId, _baseFragment)
//                .addToBackStack(_baseFragment.getClass().toString())
//                .commit();
//
//    }
//
//    public BaseFragment getTopFragment() {
//        if (mFragmentManager == null)
//            return null;
//
//        return (BaseFragment) mFragmentManager
//                .findFragmentById(mContainerId);
//    }

    /**
     * The method return fragment transaction
     */
    private FragmentTransaction getTransaction(){
        return mFragmentManager.beginTransaction();
    }

    public boolean checkSecondLevelFragmentOnThirdLvl(ItemWrapper _entry, boolean _withBackStack) {
        switch (_entry.getId()) {
            case Constants.ELEMENTOS_DECORATIVOS_ID:
            case Constants.APLICACIONES_ID:
            case Constants.COLUMNAS_ID:
                if (_withBackStack)
                    showFragment(LevelTwoFragment
                        .newInstance(_entry));
                else
                    showFragmentWithoutBackStack(LevelTwoFragment
                            .newInstance(_entry));
                return true;
            default:
                return false;
        }
    }
}
