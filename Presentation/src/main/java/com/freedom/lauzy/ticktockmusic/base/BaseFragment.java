package com.freedom.lauzy.ticktockmusic.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
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
    private Unbinder mUnBinder;
    private Toolbar mToolbar;
    private static final String STATE_SAVE_IS_HIDDEN = "STATE_SAVE_IS_HIDDEN";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    /* @Override
     public void onCreate(@Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
         if (savedInstanceState != null) {
             boolean isSupportHidden = savedInstanceState.getBoolean(STATE_SAVE_IS_HIDDEN);
             FragmentTransaction ft = getFragmentManager().beginTransaction();
             if (isSupportHidden) {
                 ft.hide(this);
             } else {
                 ft.show(this);
             }
             ft.commit();
         }
     }

     @Override
     public void onSaveInstanceState(Bundle outState) {
         super.onSaveInstanceState(outState);
         outState.putBoolean(STATE_SAVE_IS_HIDDEN, isHidden());
     }
 */
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(getLayoutRes(), container, false);
        mUnBinder = ButterKnife.bind(this, rootView);
        initInjector();
        return rootView;
    }

    @SuppressWarnings("unchecked")
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mToolbar = (Toolbar) view.findViewById(R.id.toolbar_common);
        setToolbar();
        initViews();
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
        loadData();
    }

    protected void setToolbar() {
        if (mToolbar != null) {
            ((AppCompatActivity) mActivity).setSupportActionBar(mToolbar);
//            mToolbar.setNavigationIcon(R.drawable.ic_draw_menu);
        }
    }

    protected void setToolbarPadding() {
        if (mToolbar != null) {
            mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(mActivity.getApplicationContext());
            mToolbar.setPadding(0, ScreenUtils.getStatusHeight(mActivity.getApplicationContext()), 0, 0);
        }
    }

    protected void setDrawerSync() {
        if (mToolbar != null) {
            DrawerLayout drawerLayout = (DrawerLayout) mActivity.findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(mActivity,
                    drawerLayout, mToolbar,
                    R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawerToggle.syncState();
            drawerLayout.addDrawerListener(drawerToggle);
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
