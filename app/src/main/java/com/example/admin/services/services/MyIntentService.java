package com.example.admin.services.services;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MyIntentService extends IntentService {

    private static final String TAG = "MyIntentServiceTag";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(TAG, "onHandleIntent: "
                + intent.getStringExtra( "data" )
                + " "
                + Thread.currentThread() );

        switch( intent.getAction() ) {
            case "Task1":

                Log.d(TAG, "onHandleIntent: Executing Task 1");
                for (int i = 0; i < 5; i++) {
                    try {
                        Log.d(TAG, "onHandleIntent: " + i);
                        Thread.sleep( 1000 );
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                Log.d(TAG, "onHandleIntent: Task 1 completed.");

                break;
            case "Task2":

                Log.d(TAG, "onHandleIntent: Executing Task 2");

                List<String> strings = new ArrayList<>();
                strings.add( "string1" );
                strings.add( "string2" );
                strings.add( "string3" );
                strings.add( "string4" );

                for( String s: strings )
                    Log.d(TAG, "onHandleIntent: " + s);

                Log.d(TAG, "onHandleIntent: Task 2 completed.");
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }
}
