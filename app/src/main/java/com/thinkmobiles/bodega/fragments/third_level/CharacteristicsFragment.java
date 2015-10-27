package com.thinkmobiles.bodega.fragments.third_level;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

/**
 * Created by sasha on 26.10.2015.
 */
public class CharacteristicsFragment extends BaseFragment implements View.OnClickListener{

    private TextView mInformation;
    private RecyclerView mRecyclerView;
    private ItemWrapper mItemWrapper;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new CharacteristicsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_characteristics);
        checkArgument();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findView();
        setUpData();
        setBtnListeners();
    }

    private void setBtnListeners() {
        $(R.id.btn_volver_FC).setOnClickListener(this);
        $(R.id.btn_add_envio_FC).setOnClickListener(this);
    }

    private void setUpData() {
        mInformation.setText(Html.fromHtml( mItemWrapper.getDescription()));


    }

    private void findView() {
        mInformation = $(R.id.tv_information_FC);
        mRecyclerView = $(R.id.rvRecycler_FC);
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItemWrapper = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id){
            case R.id.btn_volver_FC:
                mActivity.onBackPressed();
                break;
            case R.id.btn_add_envio_FC:
                break;
        }
    }
}
