package com.anddev.movieguide.tools;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.AbsListView;

public class UpdateDownloader {

    int pageToDownlad;
    RecyclerView recyclerView;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private boolean isLoading;

    public UpdateDownloader(RecyclerView recyclerView) {

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
                        mListener.downloadPage(recyclerView, pageToDownlad);
                        Log.d("page " + pageToDownlad + " is downloading", "UPDATE_DOWNLOADER");//filtrowanie w logCat po treści - nie tagu

                    }
                }
            }
        });

    }

    public void downloadedPage(int page) {

        if (page == pageToDownlad) {
            pageToDownlad++;
            isLoading = false;
        }
    }

    public void notDownloadedPage(int page) {

        if (page == pageToDownlad) {
            isLoading = false;
        }
    }

    //////////////////////////////
    OnUpdatePageDownloaderListener mListener;


    public void setOnUpdateDownloaderListener(OnUpdatePageDownloaderListener onUpdatePageDownloaderListener) {
        mListener = onUpdatePageDownloaderListener;

    }

    public interface OnUpdatePageDownloaderListener {
        void downloadPage(RecyclerView recyclerView, int page);
    }

    //////////////////////////////
}
