package com.thinkmobiles.bodega.fragments.extras;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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

    private List<ExtraWrapper> mExtras;

    public static BaseFragment newInstance(ItemWrapper _parentItem) {
        Bundle args = new Bundle();
        args.putSerializable(Constants.EXTRA_ITEM, _parentItem);
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
            mItem = (ItemWrapper) args.getSerializable(Constants.EXTRA_ITEM);
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
        mExtras = new ArrayList<>();
        List<String> extraVids = mItem.getExtraVideos();
        List<String> extraVidsDesc = mItem.getExtraVideosDescripton();
        List<String> extraImgs = mItem.getExtraImages();
        List<String> extraImgsDesc = mItem.getExtraImagesDescription();

        collectVids(extraVids, extraVidsDesc);
        collectImgs(extraImgs, extraImgsDesc);
    }

    private void setUpRecycler() {
        rvExtras.setHasFixedSize(true);
        rvExtras.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvExtras.setAdapter(new ExtrasAdapter(getApplicationContext(), mExtras));
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

    private void collectImgs(List<String> _imgs, List<String> _descs) {
        ExtraWrapper extra;
        if (validList(_imgs)) {
            for (int i = 0; i < _imgs.size(); i++) {
                extra = new ExtraWrapper();
                if (!TextUtils.isEmpty(_imgs.get(i)))
                    extra.setImage(_imgs.get(i));

                if (validList(_descs)) {
                    if (!TextUtils.isEmpty(_descs.get(i)))
                        extra.setImageDescriprion(_descs.get(i));
                }
                if (validExtra(extra))
                    mExtras.add(extra);
            }
        }
    }

    private void collectVids(List<String> _vids, List<String> _descs) {
        ExtraWrapper extra;
        if (validList(_vids)) {
            for (int i = 0; i < _vids.size(); i++) {
                extra = new ExtraWrapper();
                if (!TextUtils.isEmpty(_vids.get(i)))
                    extra.setVideo(_vids.get(i));

                if (validList(_descs)) {
                    if (!TextUtils.isEmpty(_descs.get(i)))
                        extra.setVideoDescription(_descs.get(i));
                }
                if (validExtra(extra))
                    mExtras.add(extra);
            }
        }
    }

    private boolean validList(List<String> _list) {
        return !(_list == null
                || _list.isEmpty());
    }

    private boolean validExtra(ExtraWrapper e) {
        return !TextUtils.isEmpty(e.getImage())
                || !TextUtils.isEmpty(e.getImageDescriprion())
                || !TextUtils.isEmpty(e.getVideo())
                || !TextUtils.isEmpty(e.getVideoDescription());
    }
}
