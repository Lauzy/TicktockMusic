package com.freedom.lauzy.ticktockmusic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.dagger.component.DaggerFragmentComponent;
import com.freedom.lauzy.ticktockmusic.dagger.component.FragmentComponent;
import com.freedom.lauzy.ticktockmusic.dagger.module.FragmentModule;
import com.freedom.lauzy.ticktockmusic.utils.ScreenUtils;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Desc : BaseLazyFragment
 * Author : Lauzy
 * Date : 2017/8/2
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public abstract class BaseFragment<T extends IPresenter> extends Fragment implements IBaseView {

    @Inject
    protected T mPresenter;
    protected Activity mActivity;
    private View mRootView;
    private Unbinder mUnBinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (null == mRootView) {
            mRootView = inflater.inflate(getLayoutRes(), null);
            mUnBinder = ButterKnife.bind(this, mRootView);
            initInjector();
        }
        ViewGroup parent = (ViewGroup) mRootView.getParent();
        if (parent != null) {
            parent.removeView(mRootView);
        }
        return mRootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setToolbar(view);
        initViews();
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
        loadData();
    }

    private void setToolbar(View view) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_common);
        if (toolbar != null) {
            toolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(mActivity.getApplicationContext());
            toolbar.setPadding(0, ScreenUtils.getStatusHeight(mActivity.getApplicationContext()), 0, 0);
            ((AppCompatActivity) mActivity).setSupportActionBar(toolbar);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        if (null != mUnBinder) {
            mUnBinder.unbind();
        }
    }

    protected abstract int getLayoutRes();

    protected abstract void initInjector();

    protected abstract void initViews();

    protected abstract void loadData();

    protected FragmentComponent getFragmentComponent() {
        return DaggerFragmentComponent.builder()
                .applicationComponent(TicktockApplication.getInstance().getApplicationComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
    }
}
