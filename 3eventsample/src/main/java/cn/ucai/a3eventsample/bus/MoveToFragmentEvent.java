package cn.ucai.a3eventsample.bus;

import android.app.Fragment;

/**
 * Event for moving to a fragment.
 *
 * @author Xinyue Zhao
 */
public final class MoveToFragmentEvent {
	/**
	 * {@link Fragment} to switch.
	 */
	private Fragment mFragment;

	/**
	 * Constructor of {@link MoveToFragmentEvent}
	 *
	 * @param _fragment
	 * 		{@link Fragment} to switch.
	 */
	public MoveToFragmentEvent(Fragment _fragment) {
		mFragment = _fragment;
	}

	/**
	 * Get the {@link Fragment} to switch.
	 *
	 * @return {@link Fragment} to switch.
	 */
	public Fragment getFragment() {
		return mFragment;
	}
}
