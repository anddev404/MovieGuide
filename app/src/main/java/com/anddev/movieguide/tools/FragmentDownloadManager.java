package com.anddev.movieguide.tools;

import android.support.v4.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class FragmentDownloadManager {

    final public static boolean THERE_IS_INTERNET_CONNECTION = true;
    final public static boolean THERE_IS_NO_INTERNET_CONNECTION = false;

    final public static boolean DATA_IS_DOWNLOAD = true;
    final public static boolean DATA_IS_NOT_DOWNLOAD = false;

    final public static boolean FRAGMENT_IS_CREATED = true;
    final public static boolean FRAGMENT_IS_NO_CREATED = false;

    List<State> list;

    public FragmentDownloadManager() {
        list = new ArrayList<>();

    }

    public void changeStateInternetConnection(boolean state, Fragment fragment) {
        for (State s : list) {
            if (s.fragment == fragment) {
                s.isInternetConnection = state;
                processStates(fragment);
            }
        }
    }

    public void changeStateDataDownload(boolean state, Fragment fragment) {
        for (State s : list) {
            if (s.fragment == fragment) {
                s.isDataDownload = state;
                processStates(fragment);
            }
        }
    }

    public void changeStateFragmentIsCreated(boolean state, Fragment fragment) {
        for (State s : list) {
            if (s.fragment == fragment) {
                s.isCreatedFragment = state;
                processStates(fragment);
            }
        }
    }

    public void processStates(Fragment fragment) {
        if (listener != null) {
            for (State s : list) {
                if (s.fragment == fragment) {

                    if (s.isDataDownload && s.isCreatedFragment) {

                        listener.hideNoInternetNotification(fragment);
                        listener.showData(fragment);
                    }

                    if (s.isInternetConnection && !s.isDataDownload) {

                        listener.downloadData(fragment);
                    }

                    if (!s.isInternetConnection && !s.isDataDownload) {

                        listener.showNoInternetNotification(fragment);
                    }
                }
            }
        }
    }


    private OnFragmentDownloadManagerListener listener;

    public void setOnDownloadManagerListener(OnFragmentDownloadManagerListener listener, Fragment fragment, boolean isInternetConnection, boolean isDataDownload, boolean isCreatedFragment) {
        this.listener = listener;
        list.add(new State(fragment, isInternetConnection, isDataDownload, isCreatedFragment));
    }

    public interface OnFragmentDownloadManagerListener {

        void showData(Fragment fragment);

        void downloadData(Fragment fragment);

        void showNoInternetNotification(Fragment fragment);

        void hideNoInternetNotification(Fragment fragment);
    }

    public class State {

        Fragment fragment;

        public boolean isInternetConnection;
        public boolean isDataDownload;
        public boolean isCreatedFragment;

        public State(Fragment fragment, boolean isInternetConnection, boolean isDataDownload, boolean isCreatedFragment) {
            this.fragment = fragment;
            this.isInternetConnection = isInternetConnection;
            this.isDataDownload = isDataDownload;
            this.isCreatedFragment = isCreatedFragment;
        }


    }
}
