package com.anddev.movieguide.tools;

public class DownloadManager {

    final public static boolean THERE_IS_INTERNET_CONNECTION = true;
    final public static boolean THERE_IS_NO_INTERNET_CONNECTION = false;

    final public static boolean DATA_IS_DOWNLOAD = true;
    final public static boolean DATA_IS_NOT_DOWNLOAD = false;

    public boolean isInternetConnection;
    public boolean isDataDownload;

    public DownloadManager(boolean isInternetConnection, boolean isDataDownload) {
        this.isInternetConnection = isInternetConnection;
        this.isDataDownload = isDataDownload;
    }

    public void changeStateInternetConnection(boolean state) {
        isInternetConnection = state;
        processStates();
    }

    public void changeStateDataDownload(boolean state) {
        isDataDownload = state;
        processStates();
    }

    public void processStates() {
        if (listener != null) {

            if (isInternetConnection == THERE_IS_INTERNET_CONNECTION && isDataDownload == DATA_IS_DOWNLOAD) {

                listener.hideNoInternetNotification();
                listener.showData();

            }
            if (isInternetConnection == THERE_IS_INTERNET_CONNECTION && isDataDownload == DATA_IS_NOT_DOWNLOAD) {

                listener.hideNoInternetNotification();
                listener.downloadData();

            }


            if (isInternetConnection == THERE_IS_NO_INTERNET_CONNECTION && isDataDownload == DATA_IS_DOWNLOAD) {

                listener.hideNoInternetNotification();
                listener.showData();

            }
            if (isInternetConnection == THERE_IS_NO_INTERNET_CONNECTION && isDataDownload == DATA_IS_NOT_DOWNLOAD) {

                listener.showNoInternetNotification();

            }


        }
    }

    private OnDownloadManagerListener listener;

    public void setOnDownloadManagerListener(OnDownloadManagerListener listener) {

        this.listener = listener;

    }

    public interface OnDownloadManagerListener {

        void downloadData();

        void showData();

        void showNoInternetNotification();

        void hideNoInternetNotification();
    }
}
