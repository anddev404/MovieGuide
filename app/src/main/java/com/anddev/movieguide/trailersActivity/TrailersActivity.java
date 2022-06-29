package com.anddev.movieguide.trailersActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.anddev.movieguide.R;
import com.anddev.movieguide.tools.BackStackManager;

import java.lang.reflect.InvocationTargetException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TrailersActivity extends AppCompatActivity {

    //public static final String YOUTUBE_SEARCH_LINK = "https://www.youtube.com/embed?listType=search&list=";
    public static final String YOUTUBE_SEARCH_LINK = "https://www.youtube.com/results?search_query=";
    public static final String TRAILER_KEY = "trailers";

    public static void goToActivity(Activity actualActivity, String query) {

        Intent intent = new Intent(actualActivity, TrailersActivity.class);
        intent.putExtra(TrailersActivity.TRAILER_KEY, query);
        actualActivity.startActivity(intent);
    }

    @BindView(R.id.trailers_webView)
    WebView webView;

    String query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trailers);

        ButterKnife.bind(this);
        BackStackManager.getInstance().addActivity(this);

        getSupportActionBar().hide();

        webView.setBackgroundColor(Color.TRANSPARENT);

        try {
            if (getIntent().getExtras() != null) {
                query = getIntent().getExtras().getString(TRAILER_KEY, "");
            } else {
                Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
                finish();
            }
        } catch (Exception e) {
            Toast.makeText(this, getString(R.string.no_results), Toast.LENGTH_SHORT).show();
            finish();
        }

        configureWebView();
        loadVideos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeWebView();
    }

    @Override
    protected void onPause() {
        super.onPause();
        pauseWebView();
    }

    @Override
    protected void onDestroy() {
        destroyWebView();
        super.onDestroy();
    }

    public void configureWebView() {
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

    }

    public void pauseWebView() {
        try {
            Class.forName("android.webkit.WebView").getMethod("onPause", (Class[]) null).invoke(webView, (Object[]) null);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        webView.onPause();
        webView.pauseTimers();
    }

    public void resumeWebView() {
        webView.onResume();
        webView.resumeTimers();
    }

    public void destroyWebView() {
        webView.destroy();
        webView = null;
    }

    public void loadVideos() {
        try {

            webView.loadUrl(YOUTUBE_SEARCH_LINK + query);

        } catch (Exception e) {
        }
    }
}
