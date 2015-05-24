package com.icharge.ui.booking;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.icharge.activity.R;
import com.icharge.beans.LocationBean;

import java.util.List;

/**
 * Created by Jinsen on 15/5/24.
 */
public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookHolder>{

    private static final String TAG = BookingAdapter.class.getSimpleName();

    private LayoutInflater mInflator;
    private Context mContext;
    private List<LocationBean> mList;
    private MyItemClickListener myItemClickListener;

    public BookingAdapter(Context mContext, List<LocationBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
        mInflator = LayoutInflater.from(mContext);
    }

    @Override
    public BookHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.book_item, parent, false);
        BookHolder holder = new BookHolder(view, myItemClickListener);
        return holder;
    }

    @Override
    public void onBindViewHolder(BookHolder holder, int position) {
        holder.mTitle.setText(mList.get(position).getName());
        holder.mName.setText(mList.get(position).getLocation());
        holder.mFastCount.setText(mList.get(position).getFast_count());
        holder.mSlowCount.setText(mList.get(position).getSlow_count());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface MyItemClickListener {
        public void onClick(View view, int position);
    }

    public static class BookHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView mTitle;
        public TextView mName;
        public TextView mDistance;
        public ImageView mPhoneIcon;
        public TextView mPhoneNum;
        public TextView mFastValid;
        public TextView mSlowValid;
        public TextView mFastCount;
        public TextView mSlowCount;
        public Button mNavBtn;
        public Button mBookBtn;
        public MyItemClickListener mListener;

        public BookHolder(View itemView, MyItemClickListener mListener) {
            super(itemView);
            this.mTitle = ((TextView) itemView.findViewById(R.id.title));
            this.mName = ((TextView) itemView.findViewById(R.id.name));
            this.mDistance = ((TextView) itemView.findViewById(R.id.distance));
            this.mPhoneIcon = ((ImageView) itemView.findViewById(R.id.phone_icon));
            this.mPhoneNum = ((TextView) itemView.findViewById(R.id.phone_num));
            this.mFastValid = ((TextView) itemView.findViewById(R.id.valid_fast));
            this.mSlowValid = ((TextView) itemView.findViewById(R.id.valid_slow));
            this.mSlowCount = ((TextView) itemView.findViewById(R.id.count_slow));
            this.mFastCount = ((TextView) itemView.findViewById(R.id.count_fast));
            this.mNavBtn = ((Button) itemView.findViewById(R.id.nav_btn));
            this.mBookBtn = ((Button) itemView.findViewById(R.id.book_btn));
            this.mListener = mListener;
        }

        @Override
        public void onClick(View v) {

        }
    }
}
