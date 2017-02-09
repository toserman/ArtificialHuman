package com.example.anton.artificialhuman;

import android.app.Service;
import android.content.ActivityNotFoundException;
import android.content.Intent;
//import android.icu.util.TimeUnit;
import android.net.Uri;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.sql.Time;

public class TestService extends Service {
    public TestService() {
    }


    @Override
    public void onCreate() {
        Toast.makeText(this,"Service was created",Toast.LENGTH_LONG).show();
        Log.e(Ut.LOG_TAG,"onStartCommand: Service was created");
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this,"Service was started",Toast.LENGTH_LONG).show();
        Log.e(Ut.LOG_TAG,"onStartCommand: Service was started");
//        testLoop();
        String url = "http://www.google.com";
        try {
            Intent c = new Intent(Intent.ACTION_VIEW);
            c.setData(Uri.parse(url));
            startActivity(c);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        Toast.makeText(this,"Service was stopped",Toast.LENGTH_LONG).show();
        Log.e(Ut.LOG_TAG,"onDestroy: Service was stopped");
        super.onDestroy();
    }

    public void testLoop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 6; i++) {
                    try {
                        TimeUnit.SECONDS.sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
//                    Toast.makeText(this,"SECOND:" + Integer.toString(i),Toast.LENGTH_LONG).show();
                    Log.e(Ut.LOG_TAG,"SECOND : " + Integer.toString(i));
                }
            }
        }).start();
    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
