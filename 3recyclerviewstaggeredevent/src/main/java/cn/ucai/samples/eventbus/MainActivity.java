package cn.ucai.samples.eventbus;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

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
        MyAdapter adapter = new MyAdapter(getApplicationContext(), logos, codes, versions);
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, codes[position], Toast.LENGTH_SHORT).show();
            }
        });
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
