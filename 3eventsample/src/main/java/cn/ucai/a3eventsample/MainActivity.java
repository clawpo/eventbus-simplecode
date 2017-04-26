package cn.ucai.a3eventsample;


import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.ucai.a3eventsample.bus.MoveToFragmentEvent;
import cn.ucai.a3eventsample.bus.StickyEvent;
import cn.ucai.a3eventsample.bus.UpdateActionBarTitleEvent;
import cn.ucai.a3eventsample.data.DataObject;
import cn.ucai.a3eventsample.fragments.BaseFragment;
import cn.ucai.a3eventsample.fragments.NoStickyFragment;
import cn.ucai.a3eventsample.fragments.SecondFragment;
import cn.ucai.a3eventsample.fragments.ThirdFragment;

/**
 * Sample main {@link Activity}.
 * <p/>
 * Created by kevintanhongann.
 * <p/>
 * Update:
 * <p/>
 * 1. Added event-handler that updates title of {@link android.app.ActionBar}.
 * <p/>
 * 2. Added event-handler that controls showing {@link cn.ucai.a3eventsample.fragments.SecondFragment}, {@link
 * cn.ucai.a3eventsample.fragments.ThirdFragment}.
 * <p/>
 * 3. Remove {@link cn.ucai.a3eventsample.bus.StickyEvent} in {@link MainActivity#onDestroy()}.
 *
 * @author Xinyue Zhao
 */
public class MainActivity extends Activity {
    private static final int LAYOUT = R.layout.activity_main;

    /**
     * Handler for {@link cn.ucai.a3eventsample.bus.UpdateActionBarTitleEvent}
     *
     * @param e Event {@link  cn.ucai.a3eventsample.bus.UpdateActionBarTitleEvent}.
     */
    public void onEvent(UpdateActionBarTitleEvent e) {
        getActionBar().setTitle(e.getTitle());
    }

    /**
     * Handler for {@link cn.ucai.a3eventsample.bus.MoveToFragmentEvent}
     *
     * @param e Event {@link cn.ucai.a3eventsample.bus.MoveToFragmentEvent }.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MoveToFragmentEvent e) {
        if (e.getFragment() instanceof ThirdFragment) {
            getFragmentManager().beginTransaction().replace(R.id.container, e.getFragment()).addToBackStack(null)
                    .commit();
        } else if (e.getFragment() instanceof SecondFragment) {
            DataObject object = new DataObject("kevin tan", "kevintan@kevintan.com");
            EventBus.getDefault().postSticky(new StickyEvent(object));
            getFragmentManager().beginTransaction().replace(R.id.container, e.getFragment()).addToBackStack(null)
                    .commit();
        } else if (e.getFragment() instanceof NoStickyFragment) {
            DataObject object = new DataObject("Hello, world!", "www.github.com");
            EventBus.getDefault().postSticky(new StickyEvent(object));
            getFragmentManager().beginTransaction().replace(R.id.container, e.getFragment()).addToBackStack(null)
                    .commit();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(LAYOUT);
        EventBus.getDefault().register(this);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        EventBus.getDefault().removeStickyEvent(StickyEvent.class);
        super.onDestroy();
    }


    /**
     * A placeholder fragment containing a simple view.
     * <p/>
     * Update:
     * <p/>
     * Extends from {@link cn.ucai.a3eventsample.fragments.BaseFragment} to demonstrate coexistence between {@link
     * {@link org.greenrobot.eventbus.EventBus#register(Object)}.
     */
    public static class PlaceholderFragment extends BaseFragment {
        private static final int LAYOUT = R.layout.fragment_main;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            /* Update fragment's title.*/
            EventBus.getDefault().post(new UpdateActionBarTitleEvent(getString(R.string.screen_1)));
            View rootView = inflater.inflate(LAYOUT, container, false);
			/* Move to fragment that can demonstrate sticky-event. */
            rootView.findViewById(R.id.btn_test).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MoveToFragmentEvent(new SecondFragment()));
                }
            });
			/* Move to fragment that can not accept sticky-event. */
            rootView.findViewById(R.id.btn_no_sticky).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventBus.getDefault().post(new MoveToFragmentEvent(new NoStickyFragment()));
                }
            });

            return rootView;
        }
    }

}
