package androidx.viewpager2.widget;

import android.view.View;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LoopLinearLayoutManager;
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback;
import androidx.viewpager2.widget.ViewPager2.PageTransformer;

import java.util.Locale;

/**
 * 这个类是根据androidx.viewpager2:viewpager2:1.0.0版本的{@link PageTransformerAdapter}代码实现.
 *
 * @author 谢朝军
 */
final class LoopPageTransformerAdapter extends OnPageChangeCallback {
    private final LoopLinearLayoutManager mLayoutManager;

    private PageTransformer mPageTransformer;

    LoopPageTransformerAdapter(LoopLinearLayoutManager layoutManager) {
        mLayoutManager = layoutManager;
    }

    PageTransformer getPageTransformer() {
        return mPageTransformer;
    }

    /**
     * Sets the PageTransformer. The page transformer will be called for each attached page whenever
     * the scroll position is changed.
     *
     * @param transformer The PageTransformer
     */
    void setPageTransformer(@Nullable PageTransformer transformer) {
        // TODO: add support for reverseDrawingOrder: b/112892792
        // TODO: add support for pageLayerType: b/112893074
        mPageTransformer = transformer;
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (mPageTransformer == null) {
            return;
        }

        float transformOffset = -positionOffset;
        for (int i = 0; i < mLayoutManager.getChildCount(); i++) {
            View view = mLayoutManager.getChildAt(i);
            if (view == null) {
                throw new IllegalStateException(String.format(Locale.US,
                        "LayoutManager returned a null child at pos %d/%d while transforming pages",
                        i, mLayoutManager.getChildCount()));
            }
            int currPos = mLayoutManager.getPosition(view);
            float viewOffset = transformOffset + (currPos - position);
            mPageTransformer.transformPage(view, viewOffset);
        }
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }
}
