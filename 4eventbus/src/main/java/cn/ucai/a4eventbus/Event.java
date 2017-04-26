package cn.ucai.a4eventbus;


import java.util.List;

import cn.ucai.a4eventbus.dummy.DummyContent;

public class Event {
    /**
     * 列表加载事件
     */
    public static class ItemListEvent {
        private List<DummyContent.DummyItem> items;

        public ItemListEvent(List<DummyContent.DummyItem> items) {
            this.items = items;
        }

        public List<DummyContent.DummyItem> getItems() {
            return items;
        }
    }

}
