package com.anddev.movieguide.tools;

import android.util.Log;

public class DownloadManager {

    //region state

    final public static boolean THERE_IS_INTERNET_CONNECTION = true;
    final public static boolean THERE_IS_NO_INTERNET_CONNECTION = false;

    final public static boolean DATA_IS_DOWNLOAD = true;
    final public static boolean DATA_IS_NOT_DOWNLOAD = false;

    final public static boolean DATA_IS_SHOWING = true;
    final public static boolean DATA_IS_NOT_SHOWING = false;

    final public static boolean DOWNLOAD_IS_IN_PROGRESS = true;
    final public static boolean DOWNLOAD_IS_NOT_IN_PROGRESS = false;

    final public static boolean NOTIFICATION_IS_SHOWING = true;
    final public static boolean NOTIFICATION_IS_NOT_SHOWING = false;

    public boolean isInternetConnection;
    public boolean isDataDownload;
    public boolean isDataShowing;
    public boolean isDownloadingInProgress;
    public boolean isNotificationShowing;

    //endregion

    public DownloadManager() {

        this.isInternetConnection = false;
        this.isDataDownload = false;
        this.isDataShowing = false;
        this.isDownloadingInProgress = false;
        this.isNotificationShowing = false;
    }

    //region changing state

    public void changeStateInternetConnection(boolean state) {
        isInternetConnection = state;
        processStates();
    }

    public void changeStateDataDownload(boolean state) {
        isDataDownload = state;
        processStates();
    }

    public void changeStateDataShowing(boolean state) {
        isDataShowing = state;
        processStates();
    }

    public void changeStateDownloadInProgress(boolean state) {
        isDownloadingInProgress = state;
        processStates();
    }

    public void changeStateNotificationIsShowing(boolean state) {
        isNotificationShowing = state;
        processStates();
    }

    public void initializeByCheckingInternetState(boolean state) {
        isInternetConnection = state;
        processStates();
    }

    //endregion

    private void processStates() {

        if (listener != null) {

            if (isDataDownload == DATA_IS_DOWNLOAD && isDataShowing == DATA_IS_NOT_SHOWING) {

                listener.showData(this);
                Log.d("show data", "DOWNLOAD_MANAGER");

            }
            if (isInternetConnection == THERE_IS_INTERNET_CONNECTION && isDataDownload == DATA_IS_NOT_DOWNLOAD && isDownloadingInProgress == DOWNLOAD_IS_NOT_IN_PROGRESS) {

                listener.downloadData(this);
                Log.d("download data", "DOWNLOAD_MANAGER");

            }


            if (isInternetConnection == THERE_IS_INTERNET_CONNECTION &&  isNotificationShowing == NOTIFICATION_IS_SHOWING) {

                listener.hideNoInternetNotification(this);
                Log.d("hide notification", "DOWNLOAD_MANAGER");

            }
            if (isInternetConnection == THERE_IS_NO_INTERNET_CONNECTION && isDataDownload == DATA_IS_NOT_DOWNLOAD && isNotificationShowing == NOTIFICATION_IS_NOT_SHOWING) {

                listener.showNoInternetNotification(this);
                Log.d("show notification", "DOWNLOAD_MANAGER");

            }


        }
    }

    //region listener

    private OnDownloadManagerListener listener;

    public void setOnDownloadManagerListener(OnDownloadManagerListener listener) {

        this.listener = listener;

    }

    public interface OnDownloadManagerListener {

        void downloadData(DownloadManager downloadManager);

        void showData(DownloadManager downloadManager);

        void showNoInternetNotification(DownloadManager downloadManager);

        void hideNoInternetNotification(DownloadManager downloadManager);
    }

    //endregion
}
