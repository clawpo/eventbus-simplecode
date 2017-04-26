package cn.ucai.a5eventbusbroadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {
    Intent intent;
    TextView textView;
    MyReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        intent = new Intent(this, MyService.class);
        startService(intent);
//        receiver = new MyReceiver();
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("cn.ucai.ACTION");
//        registerReceiver(receiver, filter);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCount(Integer integer) {
        textView.setText(integer.toString());
    }

    @Override
    protected void onDestroy() {
        stopService(intent);
//        unregisterReceiver(receiver);
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    class MyReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            textView.setText(intent.getIntExtra("count", 0) + "");
        }
    }
}
