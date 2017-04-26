package cn.ucai.a3eventsample.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.ucai.a3eventsample.R;
import cn.ucai.a3eventsample.bus.MoveToFragmentEvent;
import cn.ucai.a3eventsample.bus.NormalEvent;
import cn.ucai.a3eventsample.bus.StickyEvent;
import cn.ucai.a3eventsample.bus.UpdateActionBarTitleEvent;
import cn.ucai.a3eventsample.data.DataObject;

/**
 * SecondFragment where user can update the {@link cn.ucai.a3eventsample.bus.StickyEvent} or moving to {@link
 * cn.ucai.a3eventsample.fragments.ThirdFragment} that enhances the original sample codes.
 * <p/>
 * Created by kevintanhongann on 11/27/13.
 * <p/>
 * Update:
 * <p/>
 * 1. Demonstrate that {@link org.greenrobot.eventbus.EventBus#postSticky(Object)} works including with normal {@link
 * org.greenrobot.eventbus.EventBus#post(Object)}.
 * <p/>
 * 2. Update {@link cn.ucai.a3eventsample.bus.StickyEvent} and more to third {@link android.app.Fragment} that
 * shows new {@link cn.ucai.a3eventsample.bus.StickyEvent}.
 * <p/>
 * 3. Post event to update {@link android.app.ActionBar}'s title.
 * <p/>
 * 4. Post {@link cn.ucai.a3eventsample.bus.MoveToFragmentEvent} that will be handled by{@link
 * cn.ucai.a3eventsample.MainActivity} to switch to {@link cn.ucai.a3eventsample.fragments.ThirdFragment}.
 * <p/>
 * 5. Move calling {@link org.greenrobot.eventbus.EventBus#removeStickyEvent(Class)} on {@link
 * cn.ucai.a3eventsample.bus.StickyEvent} into {@link cn.ucai.a3eventsample.MainActivity#onDestroy()}.
 * <p/>
 * 6. Extends from {@link cn.ucai.a3eventsample.fragments.BaseFragment} to demonstrate coexistence of {@link
 * org.greenrobot.eventbus.EventBus#register(Object)} and {@link org.greenrobot.eventbus.EventBus#register(Object)}.
 *
 * @author Xinyue Zhao
 */
public class SecondFragment extends BaseFragment {
    private static final int LAYOUT = R.layout.fragment_second;

    /**
     * Handler for {@link StickyEvent}
     *
     * @param e Event {@link StickyEvent}.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(StickyEvent e) {
        Toast.makeText(getActivity(), String.format("Sticky handler: %s", e.getDataObject().toString()), Toast.LENGTH_SHORT).show();
    }

    /**
     * Handler for {@link NormalEvent}
     *
     * @param e Event {@link  NormalEvent}.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NormalEvent e) {
        Toast.makeText(getActivity(), R.string.msg_normal_event, Toast.LENGTH_SHORT).show();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /*Update ActionBar's title.*/
        EventBus.getDefault().post(new UpdateActionBarTitleEvent(getString(R.string.screen_2)));
        return inflater.inflate(LAYOUT, container, false);
    }

    @Override
    public void onViewStateRestored(Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.normal_event_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post(new NormalEvent());
            }
        });

        view.findViewById(R.id.update_event_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				/*Update the sticky, LOOK the postSticky() runs including post() that onEvent(StickyEvent e) will be called late.*/
                DataObject to = new DataObject("XinyueZ", "dev.xinyue.zhao@gmail.com");
                StickyEvent newEvent = new StickyEvent(to);
                EventBus.getDefault().postSticky(newEvent);
                Toast.makeText(getActivity(), "New Sticky is posted.", Toast.LENGTH_SHORT).show();
            }
        });

        view.findViewById(R.id.next_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
				/*Move to 3rd fragment.*/
                EventBus.getDefault().post(new MoveToFragmentEvent(new ThirdFragment()));
            }
        });
    }

    @Override
    protected boolean isStickyAvailable() {
        return true;
    }
}
