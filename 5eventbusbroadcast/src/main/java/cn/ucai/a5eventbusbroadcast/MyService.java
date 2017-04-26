package cn.ucai.a5eventbusbroadcast;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import org.greenrobot.eventbus.EventBus;

public class MyService extends Service {
    boolean isRunning = true;
    int count = 1;

    public MyService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        new Thread() {
//            public void run() {
//                while (isRunning) {
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                    Intent intent = new Intent();
//                    intent.putExtra("count", count++);
//                    intent.setAction("cn.ucai.ACTION");
//                    sendBroadcast(intent);
//                }
//            }
//        }.start();
        new Thread() {
            public void run() {
                while (isRunning) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    //auto-boxing
                    //auto-unboxing
                    EventBus.getDefault().post(count++);
                }
            }
        }.start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        isRunning = false;
    }
}
