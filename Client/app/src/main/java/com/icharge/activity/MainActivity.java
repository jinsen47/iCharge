package com.icharge.activity;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;


public class MainActivity extends Activity {

	private TextView tv_welcome;
	private TabHost tab;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.test_user);



//		tv_welcome=(TextView)findViewById(R.id.user_welcome);
//		//设置title
//
//		tv_welcome.setText("欢迎您！");
		//添加groupActivity 是实现intent的必要
		LocalActivityManager groupActivity =new LocalActivityManager(this,false);
		groupActivity.dispatchCreate(savedInstanceState);

		tab=(TabHost)findViewById(R.id.tabhost);
//这里引用groupactivity
		tab.setup(groupActivity);







//		View mesTab = (View) LayoutInflater.from(this).inflate(R.layout.lost,null);
//		TextView mes = (TextView) mesTab.findViewById(R.id.tab_label2);
//		mes.setText("发布");
//
//		View lostTab = (View) LayoutInflater.from(this).inflate(R.layout.lost, null);
//		TextView lost = (TextView) lostTab.findViewById(R.id.tab_label2);
//		lost.setText("失物");
//
//		View foundTab = (View) LayoutInflater.from(this).inflate(R.layout.lost, null);
//		TextView found = (TextView) foundTab.findViewById(R.id.tab_label2);
//		found.setText("招领");
//
//		View toolTab = (View) LayoutInflater.from(this).inflate(R.layout.lost, null);
//		TextView tool = (TextView) toolTab.findViewById(R.id.tab_label2);
//		tool.setText("搜索");
		//		Bundle bundle=this.getIntent().getExtras();
//		String username=bundle.getString("name");



		View mesTab = (View) LayoutInflater.from(this).inflate(R.layout.new_main_tab_item,null);
		ImageView mesI =(ImageView)mesTab.findViewById(R.id.icon);
		mesI.setBackgroundResource(R.drawable.new_theme_ispressed);



		View lostTab = (View) LayoutInflater.from(this).inflate(R.layout.new_main_tab_item, null);
		ImageView mesl =(ImageView)lostTab.findViewById(R.id.icon);
		mesl.setBackgroundResource(R.drawable.order_charge_ispressed);



		View foundTab = (View) LayoutInflater.from(this).inflate(R.layout.new_main_tab_item, null);
		ImageView mesf =(ImageView)foundTab.findViewById(R.id.icon);
		mesf.setBackgroundResource(R.drawable.information_ispressed);


		View toolTab = (View) LayoutInflater.from(this).inflate(R.layout.new_main_tab_item, null);
		ImageView mest =(ImageView)toolTab.findViewById(R.id.icon);
		mest.setBackgroundResource(R.drawable.person_information_ispressed);







		Intent map = new Intent(this, MyActivity.class);
		tab.addTab(tab.newTabSpec("map").setIndicator(mesTab).setContent(map));

		Intent station = new Intent(this, OrderChargeActivity.class);
		tab.addTab(tab.newTabSpec("station").setIndicator(lostTab).setContent(station));

		Intent information = new Intent(this, InformationActivity.class);
		tab.addTab(tab.newTabSpec("information").setIndicator (foundTab).setContent(information));

		Intent person = new Intent(this, PersonInfoActivity.class);
		tab.addTab(tab.newTabSpec("person").setIndicator(toolTab).setContent(person));


//////下面分别是跳转到不同的activity
//		Intent intent=new Intent(User.this,testActivity.class);
//		tab.addTab(tab.newTabSpec("mes").setIndicator(mesTab).setContent(intent));
//
//		Intent intent1=new Intent(User.this,testActivity2.class);
//		tab.addTab(tab.newTabSpec("lost").setIndicator(lostTab).setContent(intent1));
//
//		Intent intent2=new Intent(User.this,testActivity.class);
//		tab.addTab(tab.newTabSpec("found").setIndicator (foundTab).setContent(intent2));
//
//		Intent intent3=new Intent(User.this,testActivity2.class);
//		tab.addTab(tab.newTabSpec("tool").setIndicator(toolTab).setContent(intent3));


	}


}