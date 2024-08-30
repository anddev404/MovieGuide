package com.anddev.movieguide.database;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.anddev.movieguide.model.Favourite;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.RuntimeExceptionDao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseHelper extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "favourite.db";
    private static final int DATABASE_VERSION = 1;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, com.j256.ormlite.support.ConnectionSource connectionSource) {
        try {

            TableUtils.createTable(connectionSource, Favourite.class);
            Log.d("Db. Favourite onCreate", "MARCIN");

        } catch (SQLException e) {
            Log.d("Can't create Favo.. db.", "MARCIN");
            throw new RuntimeException(e);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {


//        if (DATABASE_VERSION == 2) {
//            try {
//                TableUtils.createTable(connectionSource, XXX.class);
//            } catch (SQLException e) {
//                e.printStackTrace();
//            }
//
//        }
//        try {
//            Log.d(DatabaseHelper.class.getName(), "onUpgrade");
//
//            TableUtils.dropTable(connectionSource, Favourite.class, true);
//            onCreate(db, connectionSource);
//
//        } catch (SQLException e) {
//            Log.d(DatabaseHelper.class.getName(), "Can't upgrade Favourite database", e);
//            throw new RuntimeException(e);
//        }
    }


    @Override
    public void close() {
        super.close();
        favouriteRuntimeDao = null;
    }


    //region Favourite
    private RuntimeExceptionDao<Favourite, Integer> favouriteRuntimeDao = null;

    public RuntimeExceptionDao<Favourite, Integer> getFavouriteDataDao() {
        if (favouriteRuntimeDao == null) {
            favouriteRuntimeDao = getRuntimeExceptionDao(Favourite.class);
        }
        return favouriteRuntimeDao;
    }


    private Dao<Favourite, Integer> favouriteDao = null;

    public Dao<Favourite, Integer> getFavouriteDao() throws SQLException {

        if (favouriteDao == null) {
            try {
                favouriteDao = getDao(Favourite.class);
            } catch (SQLException e) {
                //
            }

        }
        return favouriteDao;
    }

    //endregion

    public static RuntimeExceptionDao<Favourite, Integer> getFavouriteDataDao(Activity activity) {

        return DatabaseSingleton.getInstance(activity).getFavouriteDataDao();
    }


}


