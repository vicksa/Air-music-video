package com.vicksa.airmusic;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import androidx.webkit.WebViewAssetLoader;

public class MainActivity extends Activity {
    private static final String HOST = "appassets.androidplatform.net";
    private static final int CAMERA_REQUEST = 10;
    private WebView webView;
    private PermissionRequest cameraRequest;

    @Override public void onCreate(Bundle state) {
        super.onCreate(state);
        webView = new WebView(this);
        FrameLayout container = new FrameLayout(this);
        container.addView(webView, new FrameLayout.LayoutParams(-1, -1));
        container.setOnApplyWindowInsetsListener((view, insets) -> {
            view.setPadding(insets.getSystemWindowInsetLeft(), insets.getSystemWindowInsetTop(),
                insets.getSystemWindowInsetRight(), insets.getSystemWindowInsetBottom());
            return insets;
        });
        setContentView(container);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setMediaPlaybackRequiresUserGesture(false);
        settings.setAllowFileAccess(false);
        settings.setAllowContentAccess(false);
        settings.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        WebViewAssetLoader assets = new WebViewAssetLoader.Builder()
            .addPathHandler("/assets/", new WebViewAssetLoader.AssetsPathHandler(this)).build();
        webView.setWebViewClient(new WebViewClient() {
            @Override public WebResourceResponse shouldInterceptRequest(WebView view, WebResourceRequest request) {
                return assets.shouldInterceptRequest(request.getUrl());
            }
            @Override public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return !HOST.equals(request.getUrl().getHost());
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override public void onPermissionRequest(PermissionRequest request) {
                runOnUiThread(() -> {
                    if (!HOST.equals(request.getOrigin().getHost()) || !"https".equals(request.getOrigin().getScheme())) {
                        request.deny(); return;
                    }
                    boolean asksCamera = false;
                    for (String resource : request.getResources()) {
                        if (PermissionRequest.RESOURCE_VIDEO_CAPTURE.equals(resource)) asksCamera = true;
                    }
                    if (!asksCamera || cameraRequest != null) { request.deny(); return; }
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        request.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
                    } else {
                        cameraRequest = request;
                        requestPermissions(new String[]{Manifest.permission.CAMERA}, CAMERA_REQUEST);
                    }
                });
            }
            @Override public void onPermissionRequestCanceled(PermissionRequest request) {
                if (cameraRequest == request) cameraRequest = null;
            }
        });
        webView.loadUrl("https://" + HOST + "/assets/index.html");
    }
    @Override public void onRequestPermissionsResult(int code, String[] permissions, int[] results) {
        super.onRequestPermissionsResult(code, permissions, results);
        if (code == CAMERA_REQUEST && cameraRequest != null) {
            if (results.length > 0 && results[0] == PackageManager.PERMISSION_GRANTED) {
                cameraRequest.grant(new String[]{PermissionRequest.RESOURCE_VIDEO_CAPTURE});
            } else { cameraRequest.deny(); }
            cameraRequest = null;
        }
    }
    @Override protected void onPause() {
        if (webView != null) {
            webView.evaluateJavascript("window.pauseAirMusic && window.pauseAirMusic()", null);
            webView.onPause();
        }
        super.onPause();
    }
    @Override protected void onResume() {
        super.onResume();
        if (webView != null) webView.onResume();
    }
    @Override protected void onDestroy() {
        if (cameraRequest != null) { cameraRequest.deny(); cameraRequest = null; }
        if (webView != null) { webView.destroy(); webView = null; }
        super.onDestroy();
    }
}
