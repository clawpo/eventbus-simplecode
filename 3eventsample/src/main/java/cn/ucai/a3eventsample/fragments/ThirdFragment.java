package cn.ucai.a3eventsample.fragments;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.ucai.a3eventsample.R;
import cn.ucai.a3eventsample.bus.StickyEvent;
import cn.ucai.a3eventsample.bus.UpdateActionBarTitleEvent;

/**
 * {@link android.app.Fragment} shows last update of {@link StickyEvent}.
 * <p/>
 * Also update title on {@link android.app.ActionBar}.
 * <p/>
 * Demonstrate override of {@link EventBus} which is very different from <a href="http://square.github.io/otto/">Otto
 * Bus</a>.
 */
public final class ThirdFragment extends SecondFragment {
    /**
     * Overwritten version of {@link SecondFragment#onEvent(StickyEvent)}
     * to demonstrate advantage of {@link EventBus} which is very different from <a
     * href="http://square.github.io/otto/">Otto Bus</a>.
     *
     * @param _stickyEvent A {@link StickyEvent}.
     */
    @Override
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StickyEvent _stickyEvent) {
        Button updateBtn = (Button) getView().findViewById(R.id.update_event_btn);
        updateBtn.setText(String.format("Override onEvent\n%s", _stickyEvent.getDataObject().toString()));
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        /*Update ActionBar's title.*/
        EventBus.getDefault().post(new UpdateActionBarTitleEvent(getString(R.string.screen_3)));
        View v = view.findViewById(R.id.update_event_btn);
        v.setEnabled(false);
        view.findViewById(R.id.next_btn).setVisibility(View.GONE);
    }
}
