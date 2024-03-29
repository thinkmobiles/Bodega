package com.thinkmobiles.bodega.fragments.extras;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.activities.ImageViewActivity;
import com.thinkmobiles.bodega.activities.WebViewVideoActivity;
import com.thinkmobiles.bodega.activities.youtube_api.YouTubePlayerActivity;
import com.thinkmobiles.bodega.adapters.ExtrasAdapter;
import com.thinkmobiles.bodega.api.ExtraWrapper;
import com.thinkmobiles.bodega.api.ItemWrapper;
import com.thinkmobiles.bodega.fragments.BaseFragment;
import com.thinkmobiles.bodega.utils.ItemClickSupport;
import com.thinkmobiles.bodega.utils.PackageUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by illia on 29.10.15.
 */
public class ExtrasFragment extends BaseFragment implements View.OnClickListener, ItemClickSupport.OnItemClickListener {

    private ItemWrapper mItem;
    private RecyclerView rvExtras;
    private ImageView ivDownBtn;
    private ExtrasAdapter mAdapter;

    private List<ExtraWrapper> mExtras;

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
        mAdapter = new ExtrasAdapter(getApplicationContext(), mExtras);
        rvExtras.setAdapter(mAdapter);
    }

    private void initListeners() {
        ivDownBtn.setOnClickListener(this);
        ItemClickSupport.addTo(rvExtras).setOnItemClickListener(this);
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

    @Override
    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
        ExtraWrapper item = mAdapter.getItem(position);
        tryOpenVideo(item);
        tryOpenImage(item);
    }

    private void tryOpenVideo(ExtraWrapper item) {
        if (!TextUtils.isEmpty(item.getVideo())) {
            if (PackageUtils.isAppInstalled(Constants.YOUTUBE_PACKAGE_NAME,
                    getApplicationContext()))
                startVideoActivity(item, YouTubePlayerActivity.class);
            else
                startVideoActivity(item, WebViewVideoActivity.class);
        }
    }

    private void tryOpenImage(ExtraWrapper item) {
        if (!TextUtils.isEmpty(item.getImage())) {
            startActivity(new Intent(getApplicationContext(), ImageViewActivity.class)
                    .putExtra(Constants.EXTRA_ITEM, item.getImage()));
        }
    }

    private void startVideoActivity(ExtraWrapper item, Class _class) {
        startActivity(new Intent(getApplicationContext(), _class)
                .putExtra(Constants.EXTRA_VIDEO, item.getVideo()));
    }
}
