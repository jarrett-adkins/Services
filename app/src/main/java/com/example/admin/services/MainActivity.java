package com.example.admin.services;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.admin.services.model.Car;
import com.example.admin.services.services.MyBoundService;
import com.example.admin.services.services.MyIntentService;
import com.example.admin.services.services.MyJobService;
import com.example.admin.services.services.MyStartedService;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivityTag";
    MyBoundService myBoundService ;
    boolean isBound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void startServices(View view) {
        Log.d(TAG, "startServices: ");

        //A service
        Intent intent = new Intent( this, MyStartedService.class );

        //The intent service
        Intent intIntent = new Intent( this, MyIntentService.class );

        //The bound service
        Intent boundIntent = new Intent( this, MyBoundService.class );

        switch( view.getId() ) {
            case R.id.btnStartedService:

                intent.putExtra( "data", "This is a normal started service." );
                startService( intent );

                break;
            case R.id.btnStopService:

                stopService( intent );

                break;
            case R.id.btnStartIntentService:

                intIntent.putExtra( "data", "This is an Intent service running on" );
                intIntent.setAction( "Task1" );
                startService( intIntent );

                //will stop itself, don't need to worry about clean up.
                //Runs on an IntentService thread named MyIntentService.
                //Multiple clicks will put multiple services in a queue, ran one at a time.

                break;
            case R.id.btnStartIntentService2:

                intIntent.putExtra( "data", "This is an Intent service running on" );
                intIntent.setAction( "Task2" );
                startService( intIntent );

                break;
            case R.id.btnBoundToService:

                bindService( boundIntent, serviceConnection, Context.BIND_AUTO_CREATE);

                break;
            case R.id.btnBoundGetCar:

                if( isBound ) {
                    List<Car> carList = myBoundService.getCars();
                    for( Car c: carList )
                        Log.d(TAG, "onServiceConnected: " + c.getModel() + " " + c.getYear());
                }

                break;
            case R.id.btnBoundAddCar:

                if( isBound ) {
                    int year = new Random().nextInt( 50 );
                    year += 1950;
                    Car car = new Car( "SUV", "Jeep", "Compass", "Brown", year );
                    boolean added = myBoundService.addCar( car );

                    if( added )
                        Toast.makeText(myBoundService, "Car Added", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.btnUnbind:

                if( isBound ) {
                    unbindService(serviceConnection);
                    isBound = false;
                }

                break;
            case R.id.btnScheduleJob:

                //component that has the job
                ComponentName componentName = new ComponentName( this, MyJobService.class );

                //create job info for setting job parameters
                JobInfo jobInfo = new JobInfo.Builder( 0, componentName )
                        .setMinimumLatency( 1000 )   //minimum time for the job to be started. Will wait one second.
                        .setOverrideDeadline( 2000 ) //Maximum time for the job to be completed. Will stop job when reached
                        .build();

                JobScheduler jobScheduler = getSystemService( JobScheduler.class );
                jobScheduler.schedule( jobInfo );
                break;
        }
    }

    //create the service connection to bind the service
    ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            //iBinder is the one we return in our Bound Service
            Log.d(TAG, "onServiceConnected: ");

            MyBoundService.MyBinder myBinder = (MyBoundService.MyBinder) iBinder;
            myBoundService = myBinder.getService();

            isBound = true;

            myBoundService.initData();
            List<Car> carList = myBoundService.getCars();

            for( Car c: carList )
                Log.d(TAG, "onServiceConnected: " + c.getModel());
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            Log.d(TAG, "onServiceDisconnected: ");
            isBound = false;
        }
    };
}
