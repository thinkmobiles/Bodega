package com.thinkmobiles.bodega.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.thinkmobiles.bodega.Constants;
import com.thinkmobiles.bodega.R;

/**
 * Created by illia on 03.11.15.
 */
public class WebViewVideoActivity extends Activity {

    private WebView videoContainer;
    private String mVideoLink;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_web_view);
        findView();

        Intent intent = getIntent();
        mVideoLink = intent.getStringExtra(Constants.EXTRA_VIDEO);

        if(!TextUtils.isEmpty(mVideoLink)){
            playVideo();
        } else {
            finish();
        }
    }

    public void findView()    {
        videoContainer = (WebView) findViewById(R.id.wvVideoContainer);
    }

    public void playVideo(){
        videoContainer.getSettings().setJavaScriptEnabled(true);
        videoContainer.loadUrl(mVideoLink);
        videoContainer.setWebChromeClient(new WebChromeClient());
        videoContainer.setWebViewClient(new WebViewClient());

        videoContainer.getSettings().setAllowContentAccess(true);
        WebSettings webSettings = videoContainer.getSettings();
        webSettings.setPluginState(WebSettings.PluginState.ON);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        videoContainer.canGoBack();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        videoContainer.destroy();
        this.finish();
    }
}
