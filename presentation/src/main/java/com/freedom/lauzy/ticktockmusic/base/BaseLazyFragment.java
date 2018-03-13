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
import com.freedom.lauzy.ticktockmusic.injection.component.DaggerFragmentComponent;
import com.freedom.lauzy.ticktockmusic.injection.component.FragmentComponent;
import com.freedom.lauzy.ticktockmusic.injection.module.FragmentModule;
import com.lauzy.freedom.librarys.common.ScreenUtils;

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
public abstract class BaseLazyFragment<T extends IPresenter> extends Fragment implements IBaseView {

    @Inject
    protected T mPresenter;
    private Unbinder mUnBinder;
    private boolean isVisible = false;
    private boolean isInitView = false;
    private boolean isFirstLoad = true;
    protected Activity mActivity;
    private Toolbar mToolbar;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View convertView = inflater.inflate(getLayoutRes(), container, false);
        mUnBinder = ButterKnife.bind(this, convertView);
        initInjector();
        return convertView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_common);
        setToolbar();
        initViews();
        isInitView = true;
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
        lazyLoad();
    }

    protected void setToolbar() {
        if (mToolbar != null) {
            ((AppCompatActivity) mActivity).setSupportActionBar(mToolbar);
        }
    }

    protected void setToolbarPadding() {
        if (mToolbar != null) {
            mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(mActivity.getApplicationContext());
            mToolbar.setPadding(0, ScreenUtils.getStatusHeight(mActivity.getApplicationContext()), 0, 0);
        }
    }

    private void lazyLoad() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            return;
        }
        loadData();
        isFirstLoad = false;
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
