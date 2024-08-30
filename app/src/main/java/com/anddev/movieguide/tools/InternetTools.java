package com.anddev.movieguide.tools;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;

import com.anddev.movieguide.R;


public class InternetTools {

    public static boolean isNetworkAvailable(Context context) {

        ConnectivityManager manager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {

            return true;
        }
        return false;
    }

    public static AlertDialog showNoConnectionDialog(final Context ctx1) {
        final Context ctx = ctx1;
        AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
        builder.setCancelable(true);
        builder.setMessage(ctx.getString(R.string.turn_on_the_internet));
        builder.setTitle(ctx.getString(R.string.no_internet_connection));
        builder.setPositiveButton(ctx.getString(R.string.settings), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                ctx.startActivity(new Intent(Settings.ACTION_SETTINGS));
                return;
            }
        });
        builder.setNegativeButton(ctx1.getString(R.string.cancel), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                return;
            }
        });
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            public void onCancel(DialogInterface dialog) {

                return;
            }
        });

        return builder.show();
    }
}
