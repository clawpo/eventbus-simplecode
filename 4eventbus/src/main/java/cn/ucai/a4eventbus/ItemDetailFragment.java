
package cn.ucai.a4eventbus;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.ucai.a4eventbus.dummy.DummyContent;

public class ItemDetailFragment extends Fragment {

    private TextView tvDetail;

    private DummyContent.DummyItem mItem;

    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // register
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Unregister
        EventBus.getDefault().unregister(this);
    }

    /**
     * List点击时会发送些事件，接收到事件后更新详情
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onUpdateEvent(DummyContent.DummyItem item) {
        Log.d(MainActivity.TAG, "Received event at ItemDetailFragment");
        mItem = item;
        updateDetail();
    }

    private void updateDetail() {
        if (mItem != null) {
            tvDetail.setText(mItem.content);
        }
    }

    //给旧模式用的更新TextView的方法
    public void updateDetail(String text) {
        tvDetail.setText(text);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_item_detail, container, false);
        tvDetail = (TextView) rootView.findViewById(R.id.item_detail_text);
        return rootView;
    }
}
