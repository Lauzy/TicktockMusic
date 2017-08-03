package com.freedom.lauzy.ticktockmusic.base;

import android.app.Activity;
import android.app.ActivityManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.bilibili.magicasakura.utils.ThemeUtils;
import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.RxBus;
import com.freedom.lauzy.ticktockmusic.TicktockApplication;
import com.freedom.lauzy.ticktockmusic.dagger.component.ActivityComponent;
import com.freedom.lauzy.ticktockmusic.dagger.component.DaggerActivityComponent;
import com.freedom.lauzy.ticktockmusic.dagger.module.ActivityModule;
import com.freedom.lauzy.ticktockmusic.event.ThemeEvent;
import com.freedom.lauzy.ticktockmusic.navigation.Navigator;
import com.freedom.lauzy.ticktockmusic.utils.ScreenUtils;
import com.lauzy.freedom.librarys.widght.TickToolbar;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.Disposable;

/**
 * Desc : BaseActivity
 * Author : Lauzy
 * Date : 2017/6/30
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public abstract class BaseActivity<T extends IPresenter> extends AppCompatActivity implements IBaseView {

    @Inject
    protected Navigator mNavigator;

    @Inject
    protected T mPresenter;

    private Unbinder mBind;
    private Toolbar mToolbar;

    @SuppressWarnings("unchecked")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setViews();
        initInject();
        subscribeThemeEvent();
        if (null != mPresenter) {
            mPresenter.attachView(this);
        }
        loadData();
    }

    protected abstract void initInject();

    private void setViews() {
        setStatusBar();
        setContentView(getLayoutRes());
        mBind = ButterKnife.bind(this);
        setToolbar();
        initViews();
    }

    protected abstract int getLayoutRes();

    protected abstract void initViews();

    protected abstract void loadData();

    protected ActivityComponent getActivityComponent() {
        return DaggerActivityComponent.builder()
                .applicationComponent(((TicktockApplication) getApplication()).getApplicationComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }

    /**
     * subscribe theme change event
     */
    private void subscribeThemeEvent() {
        Disposable disposable = RxBus.INSTANCE.doDefaultSubscribe(ThemeEvent.class,
                themeEvent -> ThemeUtils.refreshUI(BaseActivity.this, new ThemeUtils.ExtraRefreshable() {
                    @Override
                    public void refreshGlobal(Activity activity) {
                        ActivityManager.TaskDescription taskDescription = new ActivityManager
                                .TaskDescription(null, null, ThemeUtils.getThemeAttrColor(activity,
                                android.R.attr.colorPrimary));
                        setTaskDescription(taskDescription);
                    }

                    @Override
                    public void refreshSpecificView(View view) {

                    }
                }));
        RxBus.INSTANCE.addDisposable(this, disposable);
    }

    private void setToolbar() {
        mToolbar = (TickToolbar) findViewById(R.id.toolbar_common);
        if (null != mToolbar) {
            mToolbar.getLayoutParams().height += ScreenUtils.getStatusHeight(getApplicationContext());
            mToolbar.setPadding(0, ScreenUtils.getStatusHeight(getApplicationContext()), 0, 0);
            setSupportActionBar(mToolbar);
            if (getSupportActionBar() != null)
                getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
    }

    protected void setToolbarTitle(String title) {
        if (null != mToolbar) mToolbar.setTitle(title);
    }

    protected void hideToolbarTitle() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    /**
     * show the default NavigationIcon of the toolbar
     */
    protected void showBackIcon() {
        if (getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            super.onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * set transparent status bar
     */
    private void setStatusBar() {
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.colorHalfTransparent));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mBind.unbind();
        if (null != mPresenter) {
            mPresenter.detachView();
        }
        RxBus.INSTANCE.dispose(this);
    }
}
