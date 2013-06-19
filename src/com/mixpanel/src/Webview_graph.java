package com.mixpanel.src;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Webview_graph extends Activity {
    /** Called when the activity is first created. */
    @SuppressLint("SetJavaScriptEnabled")
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        
        WebView mainWebView = (WebView) findViewById(R.id.webview1);
        WebSettings webSettings = mainWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        
        mainWebView.setWebViewClient(new MyCustomWebViewClient());
        mainWebView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
        mainWebView.getSettings().setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        mainWebView.getSettings().setPluginState(WebSettings.PluginState.ON);
        mainWebView.getSettings().setPluginsEnabled(true);
        webSettings.setBuiltInZoomControls(true);
        mainWebView.setWebChromeClient(new WebChromeClient());
        webSettings.setSupportZoom(true);
        mainWebView.loadUrl("file:///android_asset/graph/index.html");
    }
    
    private class MyCustomWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}