package com.example.admin.services.services;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.admin.services.model.Car;

import java.util.ArrayList;
import java.util.List;

public class MyBoundService extends Service {

    private static final String TAG = "MyBoundServiceTag";

    IBinder iBinder = new MyBinder();
    List<Car> cars;

    public MyBoundService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return iBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnbind: ");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    //using the binder
    public class MyBinder extends Binder {

        public MyBoundService getService() {
            return MyBoundService.this;
        }
    }

    public void initData() {
        cars = new ArrayList<>();
        cars.add( new Car( "Sedan", "Dodge", "Challenger", "Black", 1987 ));
    }

    public List<Car> getCars() {
        return cars;
    }

    public boolean addCar( Car car ) {
        return cars.add( car );
    }
}
