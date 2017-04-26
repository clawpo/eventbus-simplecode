package cn.ucai.a3eventsample.fragments;

import android.app.Fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * {@link BaseFragment}  demonstrates a general pattern used in App.
 * <p/>
 * Generally only when the App has been in foreground evens could be handled, so that we connect bus in {@code
 * onResume()}, and disconnect in {@code onPause()}.
 * <p/>
 * {@link cn.ucai.a3eventsample.fragments.BaseFragment#onEvent(Object)} to tricky {@link
 * org.greenrobot.eventbus.EventBusException} for a least one subscribed method "onEvent".
 * <p/>
 * Added {@link cn.ucai.a3eventsample.fragments.BaseFragment#isStickyAvailable()} for subclasses whether using
 * sticky-mode or normal.
 */
public abstract class BaseFragment extends Fragment {
    /**
     * Handler for {@link }
     *
     * @param e Event {@link  }.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Object e) {

    }

    @Override
    public void onResume() {
        if (isStickyAvailable()) {
            EventBus.getDefault().register(this);
        } else {
            EventBus.getDefault().register(this);
        }
        super.onResume();
    }

    @Override
    public void onPause() {
        EventBus.getDefault().unregister(this);
        super.onPause();
    }

    /**
     * Is the {@link Fragment} ready to subscribe a sticky-event or not.
     *
     * @return {@code true} if the {@link Fragment}  available for sticky-events inc. normal events.
     * <p/>
     * <b>Default is {@code false}</b>.
     */
    protected boolean isStickyAvailable() {
        return false;
    }
}
