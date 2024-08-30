package com.anddev.movieguide.tools;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;

public class UpdateDownloader {

    UpdateDownloader instance;
    int pageToDownlad;
    RecyclerView recyclerView;
    private int visibleThreshold = 10;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    public UpdateDownloader(RecyclerView recyclerView) {

        instance = this;
        this.recyclerView = recyclerView;
        pageToDownlad = 2;
        recyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                totalItemCount = linearLayoutManager.getItemCount();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (mListener != null) {
                        isLoading = true;
                        mListener.downloadPage(instance, pageToDownlad);
                        Log.d("page " + pageToDownlad + " is downloading", "UPDATE_DOWNLOADER");//filtrowanie w logCat po treści - nie tagu

                    }
                }
            }
        });

    }

    public void downloadedPage(int page) {

        if (page == pageToDownlad) {
            Log.d("page " + pageToDownlad + " downloaded", "UPDATE_DOWNLOADER");//filtrowanie w logCat po treści - nie tagu
            pageToDownlad++;
            isLoading = false;

        }
    }

    public void notDownloadedPage(int page) {

        if (page == pageToDownlad) {
            Log.d("page " + pageToDownlad + " not downloaded", "UPDATE_DOWNLOADER");//filtrowanie w logCat po treści - nie tagu
            isLoading = false;
        }
    }

    //////////////////////////////
    OnUpdatePageDownloaderListener mListener;


    public void setOnUpdateDownloaderListener(OnUpdatePageDownloaderListener onUpdatePageDownloaderListener) {
        mListener = onUpdatePageDownloaderListener;

    }

    public interface OnUpdatePageDownloaderListener {
        void downloadPage(UpdateDownloader updateDownloader, int page);
    }

    //////////////////////////////
}
