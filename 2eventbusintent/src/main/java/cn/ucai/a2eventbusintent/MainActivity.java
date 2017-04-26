package cn.ucai.a2eventbusintent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * EventBus代替广播
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onPause() {
        super.onPause();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void handleEvent(IntentServiceResult result) {
        Toast.makeText(MainActivity.this, result.getResult() + "/"
                + result.getResultValue(), Toast.LENGTH_SHORT).show();
    }
    //按钮点击事件处理方法
    public void startIntentSerivce(View view) {
        Intent intent = new Intent(this, MyIntentService.class);
        startService(intent);
    }
}
