package com.youliao.news.java;

import android.support.v4.app.Fragment;
import android.view.View;

/**
 * JavaFragmentData，Fragment数据 实现Parcelable，可以将Fragment的状态保存下来，以便于恢复
 * Created by LuckyJayce on 2016/8/9.
 */
public class JavaFragmentData {
    Fragment fragment;
    String tag;
    int containerViewId = View.NO_ID;

    public JavaFragmentData(String fragmentTag) {
        this.tag = fragmentTag;
    }

    public String getTag() {
        return tag;
    }

    public Fragment getFragment() {
        return fragment;
    }

    void setFragment(Fragment fragment) {
        this.fragment = fragment;
    }

    void setContainerViewId(int containerViewId) {
        this.containerViewId = containerViewId;
    }
}