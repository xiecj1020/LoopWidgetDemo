package com.crab.loopwidgetdemo;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.LoopViewPager2;


/**
 * @author 谢朝军.
 */
public class MainActivity extends Activity {
    /**
     * 横向viewpager.
     */
    private LoopViewPager2 mHorViewPager;
    /**
     * 竖向viewpager.
     */
    private LoopViewPager2 mVerViewPager;
    /**
     * 列表的数据.
     */
    private final int[] mImages = new int[]{
            R.drawable.item5,
            R.drawable.item1,
            R.drawable.item2,
            R.drawable.item3,
            R.drawable.item0,
            R.drawable.item4,
            R.drawable.item6,
            R.drawable.item7,
            R.drawable.item8,
            R.drawable.item9,
    };
    /**
     * 提示ui.
     */
    private Toast mToast;
    private final LoopViewPager2.OnPageChangeCallback mOnPageChangeCallback = new LoopViewPager2.OnPageChangeCallback() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            showToast("select item:" + position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        mHorViewPager = findViewById(R.id.hor_viewpager);
        mHorViewPager.setOffscreenPageLimit(1);
        mHorViewPager.setAdapter(new MyAdapter());
        mHorViewPager.registerOnPageChangeCallback(mOnPageChangeCallback);
        mVerViewPager = findViewById(R.id.ver_viewpager);
        mVerViewPager.setOffscreenPageLimit(1);
        mVerViewPager.setAdapter(new MyAdapter());
        mVerViewPager.registerOnPageChangeCallback(mOnPageChangeCallback);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHorViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback);
        mVerViewPager.unregisterOnPageChangeCallback(mOnPageChangeCallback);
    }

    private void showToast(String msg) {
        if (mToast != null) {
            mToast.cancel();
        }
        mToast = Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT);
        mToast.show();
    }

    private class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View view = inflater.inflate(R.layout.item, parent, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            holder.mImageView.setImageResource(mImages[position]);
            holder.mTextView.setText("item" + position);
            holder.itemView.setOnClickListener(v -> {
                showToast("click item:" + position);
            });
        }

        @Override
        public int getItemCount() {
            return mImages.length;
        }
    }

    private static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.img);
            mTextView = itemView.findViewById(R.id.txt);
        }
    }
}
