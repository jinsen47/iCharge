package com.icharge.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import com.icharge.modle.StationInfo;


public class NewMainActivity extends Activity{
	private Context mContex = this;
	private TabHost mTabHost;

	private View mapView;
	private View stationView;
	private View informationView;
	private View personView;




	
//	private List<LinearLayout> menuItemList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
//		menuItemList = new ArrayList<LinearLayout>();

		LocalActivityManager groupActivity =new LocalActivityManager(this,false);
		groupActivity.dispatchCreate(savedInstanceState);

		mTabHost = (TabHost) findViewById(R.id.tabhost);





		mapView = (View) LayoutInflater.from(mContex).inflate(R.layout.main_tab_item, null);
		ImageView imgView = (ImageView)mapView.findViewById(R.id.icon);
		imgView.setBackgroundResource(R.drawable.theme_ispressed);
		TextView textView = (TextView)mapView.findViewById(R.id.name);
		textView.setText("电桩");

		stationView = (View) LayoutInflater.from(mContex).inflate(R.layout.main_tab_item, null);
		ImageView imgView1 = (ImageView)stationView.findViewById(R.id.icon);
		imgView1.setBackgroundResource(R.drawable.wallpaper_ispressed);
		TextView textView1 = (TextView)stationView.findViewById(R.id.name);
		textView1.setText("我要充电");

		informationView = (View) LayoutInflater.from(mContex).inflate(R.layout.main_tab_item, null);
		ImageView imgView2 = (ImageView)informationView.findViewById(R.id.icon);
		imgView2.setBackgroundResource(R.drawable.iconbg_ispressed);
		TextView textView2 = (TextView)informationView.findViewById(R.id.name);
		textView2.setText("知识库");

		personView = (View) LayoutInflater.from(mContex).inflate(R.layout.main_tab_item, null);
		ImageView imgView3 = (ImageView)personView.findViewById(R.id.icon);
		imgView3.setBackgroundResource(R.drawable.screenlock_ispressed);
		TextView textView3 = (TextView)personView.findViewById(R.id.name);
		textView3.setText("我的主页");



//		Intent intent=new Intent(this,testActivity.class);
//		mTabHost.addTab(mTabHost.newTabSpec("mes").setIndicator(mapView).setContent(intent));
//
//		Intent intent1=new Intent(this,testActivity2.class);
//		mTabHost.addTab(mTabHost.newTabSpec("lost").setIndicator(stationView).setContent(intent1));
//
//		Intent intent2=new Intent(this,testActivity.class);
//		mTabHost.addTab(mTabHost.newTabSpec("found").setIndicator (informationView).setContent(intent2));
//
//		Intent intent3=new Intent(this,testActivity2.class);
//		mTabHost.addTab(mTabHost.newTabSpec("tool").setIndicator(personView).setContent(intent3));




		Intent map = new Intent(this, MyActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("map").setIndicator(mapView).setContent(map));

		Intent station = new Intent(this, StationInfoActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("station").setIndicator(stationView).setContent(station));

		Intent information = new Intent(this, InformationActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("information").setIndicator (informationView).setContent(information));

		Intent person = new Intent(this, PersonInfoActivity.class);
		mTabHost.addTab(mTabHost.newTabSpec("person").setIndicator(personView).setContent(person));






	}




}
