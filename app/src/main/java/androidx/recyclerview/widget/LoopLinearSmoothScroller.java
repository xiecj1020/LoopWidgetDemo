package androidx.recyclerview.widget;

import android.content.Context;
import android.view.View;

import java.lang.reflect.Field;

/**
 * 这个类是继承androidx.recyclerview:recyclerview:1.2.1版本的{@link LinearSmoothScroller}代码实现.
 * 最好更新下这个类的实现.
 *
 * @author 谢朝军
 */
public class LoopLinearSmoothScroller extends LinearSmoothScroller {

    private boolean mNeedResetTargetView = false;
    private LoopLinearLayoutManager mLoopLayoutManager;

    public LoopLinearSmoothScroller(Context context) {
        super(context);
        mNeedResetTargetView = false;
    }

    public void startFling(RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, int velocityX,
                           int velocityY) {
        RecyclerView.Adapter<?> adapter = recyclerView.getAdapter();
        boolean onlyOneData = adapter != null && adapter.getItemCount() == 1;
        boolean checkHor = layoutManager.canScrollHorizontally() && velocityX > 0;
        boolean checkVel = layoutManager.canScrollVertically() && velocityY > 0;
        mNeedResetTargetView = onlyOneData && (layoutManager instanceof LoopLinearLayoutManager) && (checkHor || checkVel);
        if (layoutManager instanceof LoopLinearLayoutManager) {
            mLoopLayoutManager = (LoopLinearLayoutManager) layoutManager;
        }
        super.start(recyclerView, layoutManager);
    }

    @Override
    protected void onStart() {
        if (mNeedResetTargetView) {
            //1.只有一个数据 2.向左fling
            View newTargetView = mLoopLayoutManager.findViewByPositionFling(getTargetPosition());
            try {
                Class<?> smoothScrollerCls = RecyclerView.SmoothScroller.class;
                Field targetViewFiled = smoothScrollerCls.getDeclaredField("mTargetView");
                targetViewFiled.setAccessible(true);
                targetViewFiled.set(this, newTargetView);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
            mNeedResetTargetView = false;
        }
    }
}
