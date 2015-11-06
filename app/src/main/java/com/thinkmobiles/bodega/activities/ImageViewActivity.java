package com.thinkmobiles.bodega.activities;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;
import com.thinkmobiles.bodega.api.ApiManager;

/**
 * Created by illia on 06.11.15.
 */
public class ImageViewActivity extends Activity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_image_view);

        ImageView iv = ((ImageView) findViewById(R.id.ivImageView_AIV));
        String image = getIntent().getStringExtra(Constants.EXTRA_ITEM);
        if (!TextUtils.isEmpty(image))
            Glide.with(getApplicationContext())
                    .load(ApiManager.getPath(getApplicationContext()) + image)
                    .fitCenter()
                    .into(iv);
        iv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        finish();
    }
}
