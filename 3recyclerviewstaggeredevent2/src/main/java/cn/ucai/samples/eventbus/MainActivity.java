package cn.ucai.samples.eventbus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import cn.ucai.samples.eventbus.decoration.MyItemDecorator;

public class MainActivity extends ActionBarActivity {
    RecyclerView recyclerView;
    String[] codes;
    String[] versions;
    int[] logos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        codes = getResources().getStringArray(R.array.code);
        versions = getResources().getStringArray(R.array.version);
        //Logo数组
        logos = new int[]{R.drawable.cupcake, R.drawable.donut,
                R.drawable.eclair, R.drawable.froyo,
                R.drawable.gingerbread, R.drawable.honeycomb,
                R.drawable.ics,
                R.drawable.jellybean,
                R.drawable.kitkat};
        //设置Layout模式
//        //网格布局,2列
//        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
//        //设置网格分割线
//        recyclerView.addItemDecoration(new MyGridItemDecorator(this));

        //        //垂直线性布局
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //设置水平分割线
        MyItemDecorator itemDecorator = new MyItemDecorator(this,
                MyItemDecorator.VERTICAL_LIST);
        recyclerView.addItemDecoration(itemDecorator);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        MyAdapter adapter = new MyAdapter();
        recyclerView.setAdapter(adapter);
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void longClickEvent(Events.LongClickEvent event) {

    }

    //处理点击事件
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clickEvent(Events.ClickEvent event) {
        int position = event.position;
        Toast.makeText(MainActivity.this, codes[position],
                Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    class MyAdapter extends RecyclerView.Adapter<MyAdapter
            .AndroidVersionHolder> {

        @Override
        public AndroidVersionHolder onCreateViewHolder(ViewGroup parent,
                                                       int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_layout,
                    parent, false);
            return new AndroidVersionHolder(view);
        }

        @Override
        public void onBindViewHolder(final AndroidVersionHolder holder,
                                     int position) {
            holder.getLogoView().setImageResource(logos[position]);
            holder.getCodeTv().setText(codes[position]);
            holder.getVersionTv().setText(versions[position]);
            //加上背景,这里使用系统主题中为列表定义的的选择器
            holder.itemView.setBackgroundResource(
                    android.R.drawable.list_selector_background);
            //设置点击事件处理
            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //注意,这里不要直接使用position
                            int pos = holder.getAdapterPosition();
                            //不再需要将事件转交给其他监听器,直接发送广播
//                            onItemClickListener.onItemClick(view,positon);
                            EventBus.getDefault().post(
                                    new Events.ClickEvent(pos, holder.itemView));
                        }
                    });
            //设置长按事件处理
            holder.itemView.setOnLongClickListener(
                    new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            //注意,这里不要直接使用position
                            int pos = holder.getAdapterPosition();
                            //不再需要将事件转交给其他监听器,直接发送广播即可
                            EventBus.getDefault().post(
                                    new Events.LongClickEvent(pos, holder.itemView));
                            return false;
                        }
                    });
        }

        @Override
        public int getItemCount() {
            return logos.length;
        }


        class AndroidVersionHolder extends RecyclerView.ViewHolder {
            ImageView logoView;
            TextView codeTv, versionTv;

            public TextView getCodeTv() {
                return codeTv;
            }

            public ImageView getLogoView() {
                return logoView;
            }

            public TextView getVersionTv() {
                return versionTv;
            }

            public AndroidVersionHolder(View itemView) {
                super(itemView);
                logoView = (ImageView) itemView.findViewById(R.id.imageView);
                codeTv = (TextView) itemView.findViewById(R.id.textView);
                versionTv = (TextView) itemView.findViewById(R.id.textView2);
            }
        }
    }
}
