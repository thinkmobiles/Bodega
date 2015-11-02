package com.thinkmobiles.bodega.fragments.extras;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.adapters.ExtrasAdapter;
import com.thinkmobiles.bodega.api.ExtraWrapper;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 29.10.15.
 */
public class ExtrasFragment extends BaseFragment implements View.OnClickListener {

    private ItemWrapper mItem;
    private RecyclerView rvExtras;
    private ImageView ivDownBtn;

    private List<ExtraWrapper> extras;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ITEM, _parentItem);
        BaseFragment fragment = new ExtrasFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_extras);
        checkArgument();
    }

    private void checkArgument() {
        Bundle args = getArguments();
        if (args != null && args.size() != 0) {
            mItem = args.getParcelable(Constants.EXTRA_ITEM);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        findUi();
        generateExtras();
        setUpRecycler();
        initListeners();
    }

    private void findUi() {
        rvExtras = $(R.id.rvExtras_FE);
        ivDownBtn = $(R.id.ivDownBtn_FE);
    }

    private void generateExtras() {
        // TODO: fix to normal data
        extras = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            ExtraWrapper extra = new ExtraWrapper();
            if (i%2 > 0) {
                extra.setImage(mItem.getIcon());
                extra.setImageDescriprion((i+1) + " IMAGE Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum.");
            } else {
                extra.setVideo("Xc5fFvp8le4");
                extra.setVideoDescription((i+1) + " VIDEO Lorem ipsum. Lorem ipsum. Lorem ipsum. Lorem ipsum.");
            }
            extras.add(extra);
        }
    }

    private void setUpRecycler() {
        rvExtras.setHasFixedSize(true);
        rvExtras.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvExtras.setAdapter(new ExtrasAdapter(getApplicationContext(), extras));
    }

    private void initListeners() {
        ivDownBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ivDownBtn_FE:
                LinearLayoutManager llm = (LinearLayoutManager) rvExtras.getLayoutManager();
                rvExtras.smoothScrollToPosition(llm.findLastVisibleItemPosition() + 1);
        }
    }
}
