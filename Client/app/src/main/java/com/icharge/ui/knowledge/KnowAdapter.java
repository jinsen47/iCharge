package com.icharge.ui.knowledge;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.ui.NetworkImageViewPlus;
import com.icharge.activity.R;
import com.icharge.beans.KnowBean;
import com.icharge.utils.ImageUtil;

import java.util.List;

/**
 * Created by Jinsen on 15/5/23.
 */
public class KnowAdapter extends RecyclerView.Adapter<KnowAdapter.KnowHolder> {
    private static final String TAG = KnowAdapter.class.getSimpleName();

    private LayoutInflater mInflator;
    private Context mContext;
    private List<KnowBean> mList;
    private MyItemClickListener myItemClickListener;

    private final String DEFAULT_URL = "http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154";


    public KnowAdapter(Context context,List<KnowBean> list) {
        mContext = context;
        mList = list;
        mInflator = LayoutInflater.from(context);
    }


    @Override
    public KnowHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = mInflator.inflate(R.layout.know_list, viewGroup, false);
        KnowHolder holder = new KnowHolder(view,myItemClickListener);

        return holder;
    }

    @Override
    public void onBindViewHolder(KnowHolder knowHolder, int i) {
        if (mList.get(i).getImage() == null || mList.get(i).getImage().equals("")) {
            knowHolder.mImage.setImageUrl(DEFAULT_URL, ImageUtil.getImageLoader());
        } else {
            knowHolder.mImage.setImageUrl(mList.get(i).getImage(), ImageUtil.getImageLoader());
        }

        if (mList.get(i).getTitle() == null || mList.get(i).getTitle().equals("")) {
            knowHolder.mTitle.setText("This a test Title" + i);
        } else {
            knowHolder.mTitle.setText(mList.get(i).getTitle());
        }
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setOnItemClickListener (MyItemClickListener listener) {
        this.myItemClickListener = listener;
    }

    public interface MyItemClickListener {
        public void onClick(View view, int position);
    }

    public static class KnowHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public NetworkImageViewPlus mImage = null;
        public TextView mTitle = null;
        private MyItemClickListener mListener;

        public KnowHolder(View itemView, MyItemClickListener listener) {
            super(itemView);
            this.mListener = listener;
            mImage = ((NetworkImageViewPlus) itemView.findViewById(R.id.image));
            mTitle = ((TextView) itemView.findViewById(R.id.title));
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mListener != null) {
                mListener.onClick(v, getLayoutPosition());
            }

        }
    }

    public void clear() {
        mList.clear();
    }
}
