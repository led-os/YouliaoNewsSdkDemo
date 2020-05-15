package com.youliao.news.java;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.youliao.news.R;
import com.youliao.sdk.news.ui.NewsFragment;

public class JavaRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private FragmentManager fragmentManager;

    public JavaRecyclerViewAdapter(Context context) {
        mContext = context;
        AppCompatActivity activity = (AppCompatActivity) mContext;
        fragmentManager = activity.getSupportFragmentManager();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if (i < 3) {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_item_image, viewGroup, false);
            return new ImageViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext)
                    .inflate(R.layout.layout_item_framelayout, viewGroup, false);
            return new FrameLayoutViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public void onViewAttachedToWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewAttachedToWindow(viewHolder);
        if (viewHolder instanceof FrameLayoutViewHolder) {
            FrameLayoutViewHolder frameLayoutViewHolder = (FrameLayoutViewHolder) viewHolder;
            JavaFragmentData javaFragmentData = frameLayoutViewHolder.javaFragmentData;
            int containerViewId = frameLayoutViewHolder.itemView.getId();
            JavaFrameLayout frameLayout = (JavaFrameLayout) frameLayoutViewHolder.itemView;
            Fragment fragment = javaFragmentData.fragment;
            if (fragment == null) {
                javaFragmentData.setContainerViewId(containerViewId);
                fragment = getFragment(javaFragmentData);
                if (fragment.isAdded()) {
                    fragmentManager.beginTransaction().remove(fragment).commitNowAllowingStateLoss();
                }
                fragmentManager.beginTransaction().add(containerViewId, fragment, javaFragmentData.getTag()).commitNowAllowingStateLoss();
                javaFragmentData.setFragment(fragment);
            }
            frameLayout.setFragment((NewsFragment) fragment);
            fragment.setUserVisibleHint(true);
            fragment.setMenuVisibility(true);
        }
    }

    @Override
    public void onViewDetachedFromWindow(@NonNull RecyclerView.ViewHolder viewHolder) {
        super.onViewDetachedFromWindow(viewHolder);
        if (viewHolder instanceof FrameLayoutViewHolder) {
            FrameLayoutViewHolder frameLayoutViewHolder = (FrameLayoutViewHolder) viewHolder;
            Fragment fragment = frameLayoutViewHolder.javaFragmentData.getFragment();
            if (fragment != null) {
                fragment.setUserVisibleHint(false);
                fragment.setMenuVisibility(false);
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ImageViewHolder) {
            ((ImageViewHolder) viewHolder).imageView.setBackgroundResource(R.drawable.restaurant);
        }
    }

    private Fragment getFragment(JavaFragmentData javaFragmentData) {
        if (javaFragmentData.fragment != null) {
            return javaFragmentData.fragment;
        }
        Fragment fragment = fragmentManager.findFragmentByTag(javaFragmentData.getTag());
        if (fragment == null) {
            fragment = instantiate(javaFragmentData);
            fragment.setMenuVisibility(false);
            fragment.setUserVisibleHint(false);
        }
        return fragment;
    }

    private Fragment instantiate(JavaFragmentData javaFragmentData) {
        Fragment fragment = NewsFragment.newInstance();
        // tab 类型配置 start==
        Bundle bundle = new Bundle();
        bundle.putString(NewsFragment.ARGUMENT_TYPE, "news"); // 默认为news，只有一个信息流页面时可以不设置
        bundle.putBoolean(NewsFragment.ARGUMENT_SWITCH, true); // 是否显示右下角的刷新按钮
        // tab 类型配置 end==
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    class ImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
        }
    }

    class FrameLayoutViewHolder extends RecyclerView.ViewHolder {
        private JavaFragmentData javaFragmentData;

        public FrameLayoutViewHolder(@NonNull View itemView) {
            super(itemView);
            javaFragmentData = new JavaFragmentData("NewsFragment");
        }
    }
}
