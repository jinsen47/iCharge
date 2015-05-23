package com.icharge.ui.knowledge;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.icharge.ChargeApp;
import com.icharge.activity.MoreInformation;
import com.icharge.activity.R;
import com.icharge.adapter.ImageListAdapter;
import com.icharge.beans.KnowItem;
import com.icharge.modle.InformateDate;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KnowFragment extends android.support.v4.app.Fragment {

    private KnowAdapter mAdapter;
    private RecyclerView mRecyclerView;

    private LayoutInflater mInflater;
    public static KnowFragment newInstance() {
        KnowFragment fragment = new KnowFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View rootView = mInflater.inflate(R.layout.fragment_know, container, false);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }

    private void initView() {
        List<KnowItem> dummyList = new ArrayList<>();
        KnowItem dummyItem = new KnowItem("支持无线充电汽车", "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154", "http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html");
        KnowItem dummyItem2 = new KnowItem("支持无线充电汽车", "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154", "http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html");
        KnowItem dummyItem3 = new KnowItem("支持无线充电汽车", "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154", "http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html");
        dummyList.add(dummyItem);
        dummyList.add(dummyItem2);
        dummyList.add(dummyItem3);
        mAdapter = new KnowAdapter(getActivity(),dummyList);
        mRecyclerView = ((RecyclerView) getView().findViewById(R.id.recyclerView));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);

    }
    private void setListener() {
        mAdapter.setOnItemClickListener(new KnowAdapter.MyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent();
            }
        });
    }

}
