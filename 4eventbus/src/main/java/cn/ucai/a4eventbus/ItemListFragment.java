
package cn.ucai.a4eventbus;


import android.os.Bundle;
import android.os.Looper;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.ucai.a4eventbus.dummy.DummyContent;

public class ItemListFragment extends ListFragment {

    public ItemListFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Register
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // 开启工作线程加载列表
        new WorkerThread().start();
    }

    /**
     * 在主线程接收ItemListEvent事件，必须是public void
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEventMainThread(Event.ItemListEvent event) {
        Log.d(MainActivity.TAG,
                "Received ItemListEvent, is main thread:" + (Looper.myLooper() == Looper.getMainLooper()));
        setListAdapter(new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_activated_1, android.R.id.text1, event.getItems()));
    }

    @Override
    public void onListItemClick(ListView listView, View view, int position, long id) {
        super.onListItemClick(listView, view, position, id);
        //EventBus新模式
        // 发送列表项点击事件，直接使用getItem，这里是DummyItem类型
//        Log.d(MainActivity.TAG, "Clicked item:" + position);
        EventBus.getDefault().post(getListView().getItemAtPosition(position));
        //给旧模式用的点击事件处理
//        ItemDetailFragment detailFragment = (ItemDetailFragment) getFragmentManager().findFragmentById(R.id.item_detail_container);
//        DummyContent.DummyItem item = (DummyContent.DummyItem) getListView().getItemAtPosition(position);
//        if (detailFragment != null)
//            detailFragment.updateDetail(item.content);
    }

    /**
     * 加载列表的工作线程
     */
    private static class WorkerThread extends Thread {

        @Override
        public void run() {
            try {
                Log.d(MainActivity.TAG, "Start get data at WorkerThred");
                Thread.sleep(2000); // 模拟延时
                // 发事件，在后台线程发的事件
                Log.d(MainActivity.TAG, "Got data, post ItemListEvent");
                EventBus.getDefault().post(new Event.ItemListEvent(DummyContent.ITEMS));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
