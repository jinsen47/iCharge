package com.icharge.activity;

import java.util.ArrayList;
import java.util.List;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;

/**
 * Created by emosfet on 2015/3/26.
 */
public class PoiSearchActivity extends Activity implements OnGetSuggestionResultListener {

    private ListView SuggestionList = null;
    private Button btn_poi_search = null;
    private Button btn_turn_back = null;

    private SuggestionSearch mSuggestionSearch = null;
    private MapView mMapView = null;
    private BaiduMap mBaiduMap = null;


    /**
     * 搜索关键字输入窗口
     */
    private AutoCompleteTextView keyWorldsView = null;
    private ArrayAdapter<String> sugAdapter = null;
    private int load_Index = 0;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poisearch);

        Bundle bundle = this.getIntent().getExtras();
        //接收name值
        final String city_all = bundle.getString("city_all");


        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        keyWorldsView = (AutoCompleteTextView) findViewById(R.id.searchkey);
        sugAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line);
        //keyWorldsView.setAdapter(sugAdapter);


        SuggestionList = (ListView)findViewById(R.id.listView_suggestion);
        SuggestionList.setAdapter(sugAdapter);

        btn_poi_search = (Button)findViewById(R.id.poi_search);
        btn_turn_back = (Button)findViewById(R.id.turn_back);
        /**
         * 当输入关键字变化时，动态更新建议列表
         */
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                if (cs.length() <= 0) {
                    return;
                }
                //String city = ((EditText) findViewById(R.id.city)).getText().toString();
                String city = city_all;
                /**
                 * 使用建议搜索服务获取建议列表，结果在onSuggestionResult()中更新
                 */
                mSuggestionSearch
                        .requestSuggestion((new SuggestionSearchOption())
                                .keyword(cs.toString()).city(city));
            }
        });
        /**
         * 影响搜索按钮点击事件
         *
         * @param v
         */
        btn_poi_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText editSearchKey = (EditText) findViewById(R.id.searchkey);
                String keyword = editSearchKey.getText().toString();

                if(keyword.length()>0) {
                    Intent intent = new Intent();
                    //把返回数据存入Intent
                    intent.putExtra("result", keyword);
                    //设置返回数据
                    PoiSearchActivity.this.setResult(2, intent);
                    //关闭Activity
                    PoiSearchActivity.this.finish();
                }
                else{
                    Toast.makeText(PoiSearchActivity.this, "请输入搜索关键字", Toast.LENGTH_LONG)
                            .show();
                }
            }
        });

        btn_turn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        SuggestionList.setOnItemClickListener(new OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                Toast.makeText(PoiSearchActivity.this, sugAdapter.getItem(position), Toast.LENGTH_LONG)
                        .show();
            }

        });

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        mSuggestionSearch.destroy();
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    @Override
    public void onGetSuggestionResult(SuggestionResult res) {
        if (res == null || res.getAllSuggestions() == null) {
            return;
        }
        sugAdapter.clear();
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null)
                sugAdapter.add(info.key);
        }
        sugAdapter.notifyDataSetChanged();
    }
 }

