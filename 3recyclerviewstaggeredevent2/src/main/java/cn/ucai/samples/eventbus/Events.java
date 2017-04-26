package cn.ucai.samples.eventbus;

import android.view.View;

public class Events {
    //点击事件
    public static class ClickEvent {
        public View view;
        public int position;

        public ClickEvent(int position, View view) {
            this.position = position;
            this.view = view;
        }
    }

    //长按事件
    public static class LongClickEvent {
        public View view;
        public int position;

        public LongClickEvent(int position, View view) {
            this.position = position;
            this.view = view;
        }
    }
}
