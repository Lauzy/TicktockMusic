package com.freedom.lauzy.ticktockmusic.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.text.SpannableStringBuilder;
import android.view.View;
import android.widget.TextView;

import com.freedom.lauzy.ticktockmusic.R;
import com.freedom.lauzy.ticktockmusic.base.BaseActivity;
import com.freedom.lauzy.ticktockmusic.utils.Spanner;
import com.lauzy.freedom.librarys.common.IntentUtil;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Desc : 关于
 * Author : Lauzy
 * Date : 2018/4/3
 * Blog : http://www.jianshu.com/u/e76853f863a9
 * Email : freedompaladin@gmail.com
 */
public class AboutActivity extends BaseActivity {

    @BindView(R.id.tv_github)
    TextView mTvGithub;
    @BindView(R.id.tv_js)
    TextView mTvJs;

    public static Intent newIntent(Context context) {
        return new Intent(context, AboutActivity.class);
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.activity_about;
    }

    @Override
    protected void initViews() {
        super.initViews();
        showBackIcon();
        setToolbarTitle(getResources().getString(R.string.drawer_about));
    }

    @Override
    protected void loadData() {
        super.loadData();
        Spanner gitSp = new Spanner(getString(R.string.my_github));
        SpannableStringBuilder github = gitSp.setColor(ContextCompat.getColor(this, R.color.txt_black), 0, 6)
                .setSize(14, 0, 6)
                .build();
        mTvGithub.setText(github);

        Spanner blogSp = new Spanner(getString(R.string.my_blog));
        SpannableStringBuilder blog = blogSp.setColor(ContextCompat.getColor(this, R.color.txt_black), 0, 4)
                .setSize(14, 0, 4)
                .build();
        mTvJs.setText(blog);
    }

    @OnClick({R.id.tv_github, R.id.tv_js, R.id.tv_bugs, R.id.tv_star})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_github:
                IntentUtil.browser(this, getString(R.string.github_website));
                break;
            case R.id.tv_js:
                IntentUtil.browser(this, getString(R.string.blog_website));
                break;
            case R.id.tv_bugs:
                IntentUtil.browser(this, getString(R.string.issues_website));
                break;
            case R.id.tv_star:
                IntentUtil.browser(this, getString(R.string.star_website));
                break;
        }
    }
}
