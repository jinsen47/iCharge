package com.icharge.ui.knowledge;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.icharge.activity.R;
import com.icharge.api.RestSource;
import com.icharge.beans.KnowBean;
import com.icharge.beans.KnowListBean;
import com.icharge.utils.BusProvider;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class KnowFragment extends android.support.v4.app.Fragment {

    private KnowAdapter mAdapter;
    private List<KnowBean> mList = new LinkedList<>();
    private RecyclerView mRecyclerView;

    private RestSource mRestSource;

    private LayoutInflater mInflater;
    public static KnowFragment newInstance() {
        KnowFragment fragment = new KnowFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mInflater = inflater;
        View rootView = mInflater.inflate(R.layout.fragment_know, container, false);

        mRestSource = RestSource.getInstance();
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
        setListener();
    }

    @Override
    public void onStart() {
        super.onStart();
        BusProvider.getDefaultBus().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        BusProvider.getDefaultBus().unregister(this);
    }

    private void initView() {
        KnowBean dummyItem = new KnowBean("支持无线充电汽车", "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154", "http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html");
        KnowBean dummyItem2 = new KnowBean("支持无线充电汽车", "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154", "http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html");
        KnowBean dummyItem3 = new KnowBean("支持无线充电汽车", "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154", "http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html");
        mList.add(dummyItem);
        mList.add(dummyItem2);
        mList.add(dummyItem3);
        mAdapter = new KnowAdapter(getActivity(),mList);
        mRecyclerView = ((RecyclerView) getView().findViewById(R.id.recyclerView));
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRecyclerView.setAdapter(mAdapter);
        mRestSource.getArticles();

    }
    private void setListener() {
        mAdapter.setOnItemClickListener(new KnowAdapter.MyItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent();
            }
        });
    }

    public void onEventMainThread(KnowListBean bean) {
        mList.clear();
        for (int i = 0; i < bean.getList().size(); i++) {
            mList.add(bean.getList().get(i));
        }
        mAdapter.notifyDataSetChanged();
    }

}
