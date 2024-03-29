package com.thinkmobiles.bodega.controllers;

/**
 * Created by illia on 22.10.15.
 */

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.fragments.DescriptionFragment;
import com.thinkmobiles.bodega.fragments.LevelTwoFragment;
import com.thinkmobiles.bodega.fragments.envios.AddToEnviosFragment;
import com.thinkmobiles.bodega.fragments.gallery_fragments.ViewGalleryPagerFragment;
import com.thinkmobiles.bodega.fragments.gallery_fragments.GalleryFragment;

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

    public void showFragment(boolean _withBackStack, BaseFragment baseFragment){
        if (_withBackStack)
            showFragment(baseFragment);
        else
            showFragmentWithoutBackStack(baseFragment);
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

    public void showDescriptionOrGalleryFragment(ItemWrapper _item, boolean _withBackStack) {
        switch (_item.getId()) {
            case Constants.CARACTERISTICS_ID:
                showFragment(_withBackStack, DescriptionFragment.newInstance(_item, false, true, false));
                break;
            case Constants.TANQUES_ID:
            case Constants.TIRAGE_ID:
                showFragment(_withBackStack, DescriptionFragment.newInstance(_item, true, true, true));
                break;
            case Constants.LOGOTIPOS_ID:
            case Constants.DIBOND_ID:
            case Constants.AZULEJO_ID:
            case Constants.MESA_VUELTA_ID:
            case Constants.COLONIAL_ID:
            case Constants.LEYENDA1_ID:
            case Constants.CAJA_DE_CERVEZAS_ID:
            case Constants.PALET_ID:
            case Constants.CHOPO_ID:
            case Constants.TELA_DE_SACO1_ID:
            case Constants.HAMACA_ID:
            case Constants.LONA_MICROPERFORADA_ID:
            case Constants.ARTICULOS_DE_USO_ID:
                showFragment(_withBackStack, GalleryFragment.newInstance(_item, true, false));
                break;
            case Constants.ENFIRADORES_ID:
            case Constants.COMPRESSOR_ID:
                showFragment(_withBackStack, DescriptionFragment.newInstance(_item, false, false, false));
                break;
            case Constants.EJEMPLOS_ID:
            case Constants.LEYENDA_ID:
            case Constants.GALERIA_DE_ACERO_ID:
            case Constants.GALERIA_DE_COBRE_ID:
            case Constants.TOLDOS_ID:
            case Constants.VINILIS_ID:
            case Constants.GRAFICAS_ID:
            case Constants.CON_VOLUMEN_ID:
            case Constants.LONA_ID:
            case Constants.SPRAY_ID:
            case Constants.TEXTIL_ID:
            case Constants.TELA_DE_SACO_ID:
            case Constants.PAPEL_PINTADO_ID:
                showFragment(_withBackStack, GalleryFragment.newInstance(_item, false, false));
                break;
            case Constants.FICHA_TACNICA_DE_COLUMNAS_ID:
            case Constants.FICHA_TACNICA_DE_BANDEJAS_ID:
                showFragment(_withBackStack, ViewGalleryPagerFragment.newInstance(_item, 0, false));
                break;
        }
    }

    public void showEnviosDialog(ItemWrapper _itemWrapper) {
        mFragmentManager.beginTransaction()
                .add(R.id.full_container, AddToEnviosFragment.newInstance(_itemWrapper))
                .addToBackStack(AddToEnviosFragment.class.getSimpleName())
                .commit();
    }
}
