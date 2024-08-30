package com.anddev.movieguide.tools;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkChangeReceiver {

    Activity activity;

    public NetworkChangeReceiver(Activity activity) {
        this.activity = activity;

    }

    private BroadcastReceiver networkChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            try {
                if (intent.getExtras() != null) {
                    NetworkInfo ni = (NetworkInfo) intent.getExtras().get(ConnectivityManager.EXTRA_NETWORK_INFO);
                    if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED) {
                        mListener.userTurnedInternetOn();


                    } else {
                        mListener.userTurnedInternetOff();


                    }
                }
            } catch (Exception e) {

            }
        }
    };

    public void registerNetworkChangeReceiver() {
        try {

            IntentFilter regFilter = new IntentFilter();
            regFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
            activity.registerReceiver(networkChangeReceiver, regFilter);

        } catch (Exception e) {

        }
    }

    public void unregisterNetworkChangeReceiver(Activity activity) {
        try {
            activity.unregisterReceiver(networkChangeReceiver);

        } catch (Exception e) {

        }
    }

    //////////////////////////////
    onSubmitListener mListener;


    public NetworkChangeReceiver setOnNetworkChangeReceiver(onSubmitListener onsubmitlistener) {
        mListener = onsubmitlistener;
        return this;
    }

    public interface onSubmitListener {
        void userTurnedInternetOn();

        void userTurnedInternetOff();

    }

    //////////////////////////////

}
