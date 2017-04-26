package cn.ucai.threadmodel;

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
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    public void main(View view) {
        Log.e("main", Thread.currentThread().getName());
        EventBus.getDefault().post("abc");
    }

    public void nonMain(View view) {
        new Thread("MyThread") {
            public void run() {
                //耗时操作
                Log.e("non-main", Thread.currentThread().getName());
                EventBus.getDefault().post("def");
            }
        }.start();
    }

    @Subscribe(threadMode = ThreadMode.POSTING)
    public void test1(String str) {
        Log.e(str + "POSTING", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void test2(String str) {
        Log.e(str + "MAIN", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.BACKGROUND)
    public void test3(String str) {
        Log.e(str + "BG", Thread.currentThread().getName());
    }

    @Subscribe(threadMode = ThreadMode.ASYNC)
    public void test4(String str) {
        Log.e(str + "ASYNC", Thread.currentThread().getName());
    }
}
