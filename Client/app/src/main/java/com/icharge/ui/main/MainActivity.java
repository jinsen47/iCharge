package com.icharge.ui.main;

import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.icharge.activity.R;
import com.icharge.ui.BaseActivity;
import com.icharge.utils.ScreenHelper;
import com.icharge.views.ViewPagerCompat;

public class MainActivity extends BaseActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    private Toolbar mToolbar;
    private ViewPagerCompat mViewPager;
    private MainAdapter mAdapter;
    private PagerSlidingTabStrip mTabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        mToolbar = ((Toolbar) findViewById(R.id.tool_bar));
        mViewPager = (ViewPagerCompat) findViewById(R.id.main_cotainer);
        mAdapter = new MainAdapter(getSupportFragmentManager());
        mTabs = (PagerSlidingTabStrip) findViewById(R.id.main_tabs);

        setSupportActionBar(mToolbar);
        mViewPager.setAdapter(mAdapter);
        mTabs.setViewPager(mViewPager);

        mTabs.setBackgroundColor(getResources().getColor(R.color.color_primary));
        mTabs.setDividerColor(getResources().getColor(R.color.main_divider));
        mTabs.setIndicatorColor(getResources().getColor(R.color.rating_star));

//        mToolbar.setLogo(R.drawable.title);
        this.setTitle("");
        mToolbar.setTitle("");

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
