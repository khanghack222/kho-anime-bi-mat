package com.khangflix.stream;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.JavascriptInterface;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private WebView webView;
    private FrameLayout customViewContainer;
    private View customView;
    private WebChromeClient.CustomViewCallback customViewCallback;

    private static final int STORAGE_PERMISSION_CODE = 101;
    private String pendingDownloadUrl = null;
    private String pendingDownloadFilename = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Giữ màn hình luôn sáng khi xem phim, tối ưu cho máy Oppo A71k
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);

        webView = findViewById(R.id.webView);
        customViewContainer = findViewById(R.id.customViewContainer);

        setupOptimizedWebView();

        // Nạp giao diện Netflix UI từ assets nội bộ (Offline, mở app tức thì 0.1 giây)
        webView.loadUrl("file:///android_asset/netflix.html");
    }

    private void setupOptimizedWebView() {
        WebSettings ws = webView.getSettings();

        // 1. Tối ưu cực đại cho chip Snapdragon 450 và 2GB RAM của Oppo A71k
        ws.setJavaScriptEnabled(true);
        ws.setDomStorageEnabled(true);
        ws.setDatabaseEnabled(true);
        ws.setAllowFileAccess(true);
        ws.setAllowContentAccess(true);

        // Hỗ trợ media & hardware decode video mượt mà
        ws.setMediaPlaybackRequiresUserGesture(false);

        // Cache trên RAM thấp để chống tràn bộ nhớ 16GB
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);
        ws.setAppCacheEnabled(true);
        File cacheDir = getCacheDir();
        if (cacheDir != null) {
            ws.setAppCachePath(cacheDir.getAbsolutePath());
        }

        // Tắt zoom rườm rà
        ws.setSupportZoom(false);
        ws.setBuiltInZoomControls(false);

        // Cầu nối Javascript Interface để HTML gọi chức năng Native của Android
        webView.addJavascriptInterface(new AndroidBridge(), "AndroidNative");

        // Xử lý tải video thông qua DownloadManager của Android (Tải ngầm tiết kiệm RAM)
        webView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimeType, long contentLength) {
                String filename = URLUtil.guessFileName(url, contentDisposition, mimeType);
                triggerNativeDownload(url, filename);
            }
        });

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // Giữ luồng trong webview
                return false;
            }
        });

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onShowCustomView(View view, CustomViewCallback callback) {
                // Xử lý toàn màn hình xoay ngang tự động
                if (customView != null) {
                    onHideCustomView();
                    return;
                }
                customView = view;
                customViewCallback = callback;
                customViewContainer.setVisibility(View.VISIBLE);
                customViewContainer.addView(view, new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                ));
                webView.setVisibility(View.GONE);

                // Ép xoay ngang khi full màn hình
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

                // Ẩn thanh điều hướng ảo
                getWindow().getDecorView().setSystemUiVisibility(
                        View.SYSTEM_UI_FLAG_FULLSCREEN
                                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                );
            }

            @Override
            public void onHideCustomView() {
                if (customView == null) return;

                customViewContainer.setVisibility(View.GONE);
                customViewContainer.removeView(customView);
                customView = null;
                if (customViewCallback != null) {
                    customViewCallback.onCustomViewHidden();
                }
                webView.setVisibility(View.VISIBLE);

                // Trả lại xoay dọc
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
                getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
            }
        });
    }

    public void triggerNativeDownload(String url, String filename) {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                pendingDownloadUrl = url;
                pendingDownloadFilename = filename;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        STORAGE_PERMISSION_CODE);
                return;
            }
        }
        executeDownload(url, filename);
    }

    private void executeDownload(String url, String filename) {
        try {
            if (filename == null || filename.trim().isEmpty()) {
                filename = "video_" + System.currentTimeMillis() + ".mp4";
            }
            if (!filename.endsWith(".mp4") && !filename.endsWith(".mkv")) {
                filename += ".mp4";
            }

            DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
            request.setTitle("Đang tải: " + filename);
            request.setDescription("KhangFlix đang lưu vào thư mục Download...");
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

            String cookies = CookieManager.getInstance().getCookie(url);
            if (cookies != null) {
                request.addRequestHeader("cookie", cookies);
            }
            request.addRequestHeader("User-Agent", webView.getSettings().getUserAgentString());

            request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, filename);

            DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
            if (dm != null) {
                dm.enqueue(request);
                Toast.makeText(this, "Bắt đầu tải về: " + filename, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Lỗi khi tải: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (pendingDownloadUrl != null) {
                    executeDownload(pendingDownloadUrl, pendingDownloadFilename);
                }
            } else {
                Toast.makeText(this, "Cần cấp quyền ghi bộ nhớ để tải phim về máy.", Toast.LENGTH_SHORT).show();
            }
            pendingDownloadUrl = null;
            pendingDownloadFilename = null;
        }
    }

    public class AndroidBridge {
        @JavascriptInterface
        public void downloadVideo(String url, String filename) {
            runOnUiThread(() -> triggerNativeDownload(url, filename));
        }

        @JavascriptInterface
        public void showToast(String message) {
            runOnUiThread(() -> Toast.makeText(MainActivity.this, message, Toast.LENGTH_SHORT).show());
        }

        @JavascriptInterface
        public void reloadApp() {
            runOnUiThread(() -> {
                if (webView != null) {
                    webView.clearCache(true);
                    webView.reload();
                }
            });
        }

        @JavascriptInterface
        public boolean isNative() {
            return true;
        }
    }

    @Override
    public void onBackPressed() {
        if (customView != null) {
            webView.getWebChromeClient().onHideCustomView();
        } else if (webView.canGoBack()) {
            webView.goBack();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.destroy();
        }
        super.onDestroy();
    }
}
