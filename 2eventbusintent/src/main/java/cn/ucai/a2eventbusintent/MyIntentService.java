package cn.ucai.a2eventbusintent;

import android.app.IntentService;
import android.content.Intent;
import android.os.AsyncTask;

import org.greenrobot.eventbus.EventBus;

public class MyIntentService extends IntentService {
    AsyncTask task;
    int count = 0;

    //doInBackground
    public MyIntentService() {
        super("MyIntentSerivce");
    }

    /**
     * 这个方法中的耗时任务会自动被放在一个单独的线程中去执行
     * onStartCommand()方法被调用一次，该方法也会被调用一次
     * @param intent
     */
    @Override
    protected void onHandleIntent(Intent intent) {
        count++;
//        new Thread(){}.start();
        if (intent != null) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            EventBus.getDefault().post(new
                    IntentServiceResult(count, "完成"));
        }
    }
}
