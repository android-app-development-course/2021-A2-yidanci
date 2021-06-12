package com.iwxyi.letsremember.Boxs;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import com.iwxyi.letsremember.Globals.App;
import com.iwxyi.letsremember.Globals.Def;
import com.iwxyi.letsremember.Material.MaterialSelectActivity;
import com.iwxyi.letsremember.R;

public class BoxsActivity extends AppCompatActivity implements BoxItemsFragment.OnBoxFragmentInteractionListener {

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_boxs);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        fab.hide();


        int last = App.getInt("box_last");
        if (last == Def.ICE_BOX) {
            mViewPager.setCurrentItem(0);
        } else if (last == Def.WOOD_BOX) {
            mViewPager.setCurrentItem(1);
        } else if (last == Def.COPPER_BOX) {
            mViewPager.setCurrentItem(2);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_boxs, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBoxItemClicked(BoxItemsFragment.BoxContent.BoxItem item) {
        String pack = item.pack;
        String sect = item.sect;
        int card = item.index-1; // 因为 index 是从 1 开始的

        App.setVal("last_package", pack);
        App.setVal("last_section", sect);
//        App.setVal("last_card", card);
//        App.setVal("selected_package", pack);
//        App.setVal("selected_section", sect);
//        App.setVal("selected_card", card);
        App.setVal("card:"+pack+"/"+sect, card);

        Intent activity_change= new Intent(BoxsActivity.this, MaterialSelectActivity.class);    //切换 Activityanother至MainActivity
        Bundle bundle = new Bundle();// 创建Bundle对象
        bundle.putBoolean("open", true);//  放入data值为int型
        activity_change.putExtras(bundle);// 将Bundle对象放入到Intent上
        startActivity(activity_change);//  开始跳转
    }

    public static class PlaceholderFragment extends Fragment {
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @SuppressLint("StringFormatInvalid")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_boxs, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));

            return rootView;
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return BoxItemsFragment.newInstance(1, Def.ICE_BOX);
                case 1:
                    return BoxItemsFragment.newInstance(1, Def.WOOD_BOX);
                case 2:
                    return BoxItemsFragment.newInstance(1, Def.COPPER_BOX);
            }
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            return 3;
        }

    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int i, float v, int i1) {

        }

        /**
         * 切换页面时，修改Activity的标题
         * @param i 页面索引，从0开始
         */
        @Override
        public void onPageSelected(int i) {
            if (toolbar == null) {
                toolbar = (Toolbar) findViewById(R.id.toolbar);
            }
            switch (i) {
                case 0:
                    if (toolbar != null) {
                        toolbar.setTitle("金盒子");
                    }
                    break;
                case 1:
                    if (toolbar != null) {
                        toolbar.setTitle("银盒子");
                    }
                    break;
                case 2:
                    if (toolbar != null) {
                        toolbar.setTitle("铁盒子");
                    }
                    break;
            }
        }

        @Override
        public void onPageScrollStateChanged(int i) {

        }
    }
}
