package cn.ucai.eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //按钮事件处理方法
    public void sendMsg(View view) {
        new Thread() {
            public void run() {
                //模拟耗时操作
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.e("", "发送消息线程ID" + this.getId());
                EventBus.getDefault().post(new AKjkdsajkdfajsdlf("Hello"));
            }
        }.start();
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void dsafasfewrew(AKjkdsajkdfajsdlf event) {
        Log.e("", "线程ID：" + Thread.currentThread().getId());
//        Toast.makeText(this, event.message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }
}
