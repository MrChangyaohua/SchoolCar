package com.example.clientdemo;

import java.util.ArrayList;
import java.util.List;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;

import com.example.clientdemo.fragment.ListShowFragment;
import com.example.clientdemo.fragment.MapShowFragment;

public class MainActivity extends FragmentActivity {
	private ViewPager mViewPager;
	private FragmentPagerAdapter mAdapter;
	private List<Fragment> mFragments;
	
	private Button btn_map;
	private Button btn_list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		
		initView();
		
		setSelect(0);
		
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_viewpager);
		
		btn_list = (Button) findViewById(R.id.btn_list);
		btn_map = (Button) findViewById(R.id.btn_map);
		
		btn_list.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setSelect(0);
			}
		});
		
		btn_map.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				setSelect(1);
			}
		});
		
		Fragment listFragment = new ListShowFragment();
		Fragment mapFragment = new MapShowFragment();
		
		mFragments = new ArrayList<Fragment>();
		mFragments.add(listFragment);
		mFragments.add(mapFragment); 
		
		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager())
		{

			@Override
			public int getCount()
			{
				return mFragments.size();
			}

			@Override
			public Fragment getItem(int arg0)
			{
				return mFragments.get(arg0);
			}
		};
		mViewPager.setAdapter(mAdapter);
		
		mViewPager.setOnPageChangeListener(new OnPageChangeListener()
		{
			
			@Override
			public void onPageSelected(int i)
			{
				if (i == 0) {
					btn_map.setBackgroundColor(Color.parseColor("#FFFFFF"));
					btn_map.setTextColor(Color.parseColor("#000000"));
					btn_list.setBackgroundColor(Color.parseColor("#000000"));
					btn_list.setTextColor(Color.parseColor("#FFFFFF"));
				} else if (i == 1){
					btn_list.setBackgroundColor(Color.parseColor("#FFFFFF"));
					btn_list.setTextColor(Color.parseColor("#000000"));
					btn_map.setBackgroundColor(Color.parseColor("#000000"));
					btn_map.setTextColor(Color.parseColor("#FFFFFF"));
					
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2)
			{
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0)
			{
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void setSelect(int i)
	{
		if (i == 0) {
			btn_map.setBackgroundColor(Color.parseColor("#FFFFFF"));
			btn_map.setTextColor(Color.parseColor("#000000"));
			btn_list.setBackgroundColor(Color.parseColor("#000000"));
			btn_list.setTextColor(Color.parseColor("#FFFFFF"));
		} else if (i == 1){
			btn_list.setBackgroundColor(Color.parseColor("#FFFFFF"));
			btn_list.setTextColor(Color.parseColor("#000000"));
			btn_map.setBackgroundColor(Color.parseColor("#000000"));
			btn_map.setTextColor(Color.parseColor("#FFFFFF"));
		}
		mViewPager.setCurrentItem(i);
	}

}

	


