package cn.ucai.samples.eventbus.decoration;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class MyItemDecorator extends RecyclerView.ItemDecoration {
    //使用系统的列表分割线作为我们要绘制的分割线
    private static final int[] ATTRS = new int[]{
            android.R.attr.listDivider
    };
    //用于标识水平列表的常量
    public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;
    //用于标识垂直列表的常量
    public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

    //方向,取值为上面2个常量之一
    private int mOrientation;

    private Drawable mDivider;

    public MyItemDecorator(Context context, int orientation) {
        final TypedArray typedArray = context.obtainStyledAttributes(ATTRS);
        mDivider = typedArray.getDrawable(0);
        typedArray.recycle();
        setOrientation(orientation);
    }

    public void setOrientation(int orientation) {
        if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
            throw new IllegalArgumentException("invalid orientation");
        }
        mOrientation = orientation;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent,
                               RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            //对于垂直列表,只需要指定高度即可,宽度无所谓,这里设置成0(第三个参数)
            //getIntrinsicHeight()方法获取图片的固定高度,
            //即没有根据屏幕大小\密度伸缩的高度
            outRect.set(0, 0, 0, mDivider.getIntrinsicHeight());
        } else {
            //对于水平列表,只需要指定宽度即可,高度无所谓,这里设置成0(第四个参数)
            outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
        }
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        if (mOrientation == VERTICAL_LIST) {
            //如果是垂直列表,则调用drawVertical()方法绘制水平分割线
            drawVertical(c, parent);
        } else {
            //如果是水平列表,则调用drawVertical()方法绘制垂直分割线
            drawHorizontal(c, parent);
        }
    }

    //为垂直列表绘制水平分割线
    private void drawVertical(Canvas c, RecyclerView parent) {
        final int left = parent.getPaddingLeft();
        final int right = parent.getWidth() - parent.getPaddingRight();

        final int childCount = parent.getChildCount();

        //遍历每个可见选项,并且绘制分割线
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params =
                    (RecyclerView.LayoutParams) child.getLayoutParams();
            final int top = child.getBottom() + params.bottomMargin;
            final int bottom = top + mDivider.getIntrinsicHeight();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }

    //为水平列表绘制垂直分割线
    private void drawHorizontal(Canvas c, RecyclerView parent) {
        final int top = parent.getPaddingTop();
        final int bottom = parent.getHeight() - parent.getPaddingBottom();

        final int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            final View child = parent.getChildAt(i);
            final RecyclerView.LayoutParams params
                    = (RecyclerView.LayoutParams) child.getLayoutParams();
            final int left = child.getRight() + params.rightMargin;
            final int right = left + mDivider.getIntrinsicWidth();
            mDivider.setBounds(left, top, right, bottom);
            mDivider.draw(c);
        }
    }
}
