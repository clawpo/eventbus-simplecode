package cn.ucai.samples.eventbus;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by shangshuwen on 16/7/15.
 */
class MyAdapter extends RecyclerView.Adapter<MyAdapter
        .AndroidVersionHolder> {
    Context context;
    int logos[];
    String[] codes;
    String[] versions;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    OnItemClickListener onItemClickListener;

    public MyAdapter() {
    }

    public MyAdapter(Context context, int[] logos,
                     String[] codes, String[] versions) {
        this.context = context;
        this.logos = logos;
        this.codes = codes;
        this.versions = versions;
    }

    interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    @Override
    public AndroidVersionHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.item_layout,
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
                        //...
//                            Toast.makeText()
                        if (onItemClickListener != null) {
                            onItemClickListener.onItemClick(v, holder.getAdapterPosition());
                        }
                    }
                });
        //设置长按事件处理
        holder.itemView.setOnLongClickListener(
                new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View v) {
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