package com.example.anton.artificialhuman;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * Created by Anton on 22.12.2016.
 */

public class Ut {
    public static final String LOG_TAG = "MY TAG";
    public static void printRunningServices(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> rs = am.getRunningServices(50);
        int i;
        Toast.makeText(context, "Print Running Services",Toast.LENGTH_LONG).show();
        for ( i = 0; i < rs.size(); i++) {
            ActivityManager.RunningServiceInfo rsi = rs.get(i);
            Log.e(LOG_TAG,"Process " + rsi.process + " with component " + rsi.service.getClassName());
            if (rsi.service.getClassName().contentEquals("Test"))
            Log.e(LOG_TAG,"Test");

        }
        Log.e(LOG_TAG,"Total running processes: " + i);
    }
}
