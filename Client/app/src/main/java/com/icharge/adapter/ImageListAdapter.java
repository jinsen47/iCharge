package com.icharge.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.icharge.activity.R;
import com.icharge.modle.InformateDate;
import com.icharge.newCache.ImageCache;
import com.icharge.newCache.ImageWorker;
import com.icharge.newCache.Utils;

import java.util.List;

/**
 * Created by dengpingzheng on 2015/3/2.
 */
public class ImageListAdapter extends ArrayAdapter<InformateDate> {

    private ImageWorker mImageWorker;
    private ListView listView;
    //    private AsyncImageLoader asyncImageLoader;
    private List<InformateDate> videoList;
    public static String TAG ="ImageListAdapter";

    public ImageListAdapter(Activity activity, List<InformateDate> InformateDateLists, ListView listView) {
        super(activity, 0, InformateDateLists);
        this.listView = listView;
//        asyncImageLoader = new AsyncImageLoader();
//        mImageWorker = ImageWorker.newInstance(activity);
        ImageCache.ImageCacheParams cacheParams = new ImageCache.ImageCacheParams();
        cacheParams.reqWidth = activity.getResources().getDisplayMetrics().widthPixels / 5;
        cacheParams.reqHeight = (int) (cacheParams.reqWidth * 1.5);
        cacheParams.loadingResId = R.drawable.nocaching;
        // cacheParams.clearDiskCacheOnStart = true;
        cacheParams.memCacheSize = (1024 * 1024 * Utils
                .getMemoryClass(activity)) / 2;
        // cacheParams.clearDiskCacheOnStart = true;
        mImageWorker = ImageWorker.newInstance(activity);
        mImageWorker.addParams(TAG, cacheParams);
        this.videoList = InformateDateLists;


    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return videoList.size();
    }
    @Override
    public InformateDate getItem(int position) {
        // TODO Auto-generated method stub
        return videoList.get(position);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Activity activity = (Activity) getContext();
        InformateDate InformateDate = getItem(position);

        ViewLock viewHolder;
        if(convertView == null){
            viewHolder = new ViewLock();
            LayoutInflater inflater = activity.getLayoutInflater();
            convertView = inflater.inflate(R.layout.information_item, null);
            viewHolder.title = (TextView) convertView.findViewById(R.id.title);
            viewHolder.playUrl = (TextView) convertView.findViewById(R.id.PlayUrl);
            viewHolder.cover = (ImageView) convertView.findViewById(R.id.image);
            convertView.setTag(viewHolder);
        }
        viewHolder = (ViewLock)convertView.getTag();

        viewHolder.title.setText(InformateDate.getTitle());
        viewHolder.playUrl.setText(InformateDate.getPlayURL());
        final ImageView imageView = viewHolder.cover;
        imageView.setImageDrawable(null); //reset default
        imageView.setTag(position);
        mImageWorker.loadBitmap(InformateDate.getCoverURL(), imageView);//  新设置封面

        viewHolder.cover.setTag(InformateDate.getCoverURL());
        return convertView;

    }
    static class ViewLock{
        TextView title ;
        TextView playUrl ;
        ImageView cover ;
    }

}
