package com.warivirtualpos.wari;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.warivirtualpos.wari.model.Agent;
import com.warivirtualpos.wari.utils.DatabaseHandler;

import java.util.List;

/**
 * Created by hilary on 12/16/17.
 */

public class MainApplication extends Application{
    private static Context context;
    private DatabaseHandler databaseHandler;

    public static Context getContext() {
        return context;
    }

    @Override
    public void onCreate() {
        super.onCreate();





    }
}
