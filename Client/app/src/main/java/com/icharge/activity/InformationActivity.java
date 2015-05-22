package com.icharge.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import com.icharge.adapter.ImageListAdapter;
import com.icharge.modle.InformateDate;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2015/4/1.
 */
public class InformationActivity extends Activity {


    public static int COUNT =0;

    public ImageListAdapter adapter;

    private static List<InformateDate> dataArray = new ArrayList<InformateDate>();

    private ListView informationList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.information);
        initView();
        setListener();

    }

    private void setListener() {

        informationList.setOnItemClickListener(new ItemClickListener());
    }

    private void initView() {

        informationList = (ListView) this.findViewById(R.id.infomation);

        adapter =new ImageListAdapter(this, dataArray, informationList);

        new MyHomeTask().execute("");

        informationList.setAdapter(adapter);

    }

    final class MyHomeTask extends AsyncTask<String, String, String> {

        private int mFlag;
        public MyHomeTask(){

        }
        public MyHomeTask(int flag) {
            this.mFlag = flag;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

//            String result ="{\"total\":4,\"date\":[{\"title\":\"Google\",\"playUrl\":\"http://www.google.com\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"value\"},{\"title\":\"Google\",\"playUrl\":\"http://www.google.com\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"value\"},{\"title\":\"Google\",\"playUrl\":\"http://www.google.com\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"value\"},{\"title\":\"Google\",\"playUrl\":\"http://www.google.com\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"value\"}]}";
           String result ="{\"total\":4,\"date\":[{\"title\":\"支持无线充电汽车\",\"playUrl\":\"http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154\"},{\"title\":\"支持无线充电汽车\",\"playUrl\":\"http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154\"},{\"title\":\"支持无线充电汽车\",\"playUrl\":\"http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154\"},{\"title\":\"支持无线充电汽车\",\"playUrl\":\"http://blog.sina.com.cn/s/blog_b47798bc0101oadz.html\",\"datePublish\":\"\",\"informationDetail\":\"lalal\",\"coverUrl\":\"http://g1.ykimg.com/1100641F464F826E80E0BC017EAC6332ABE904-0DAD-0F39-7A97-6C03B44A1154\"}]}";
            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            if (null != result && !"".equals(result)) {
                try {
                    ArrayList<InformateDate> list = parserJsonData(result);

                            dataArray.clear();
                            dataArray.addAll(list);
                            adapter.notifyDataSetChanged();



                } catch (Exception e) {

                    e.printStackTrace();
                }
            } else {
                Toast.makeText(getApplicationContext(), "json null",Toast.LENGTH_LONG);
//                WidgetUtil.TextToast(getApplicationContext(), "json null", 1);

                }

            super.onPostExecute(result);
        }
    }

    private ArrayList<InformateDate> parserJsonData(String result) {


        ArrayList<InformateDate> list = new ArrayList<InformateDate>();
        JSONTokener tokener = new JSONTokener(result);
        JSONObject jObject = null;
        try {
            jObject = (JSONObject) tokener.nextValue();
        } catch (JSONException e) {

            e.printStackTrace();
        }
        try {
            COUNT = jObject.getInt("total");
        } catch (JSONException e) {

            e.printStackTrace();
        }
        JSONArray jArray = null;
        try {
            jArray = jObject.getJSONArray("date");
        } catch (JSONException e) {

            e.printStackTrace();
        }
        InformateDate vi ;
        for (int i = 0; i < jArray.length(); i++) {
            vi = new InformateDate();
            try {
                vi.setTitle(jArray.getJSONObject(i).getString("title"));
            } catch (JSONException e) {
                vi.setCoverURL("null");
                e.printStackTrace();
            }
            try {
                vi.setPlayURL(jArray.getJSONObject(i).getString("playUrl"));
            } catch (JSONException e) {

                vi.setPlayURL(null);
                e.printStackTrace();
            }
            try {
                vi.setDatePublish(jArray.getJSONObject(i).getString("datePublish"));
            } catch (JSONException e) {
                vi.setDatePublish("null");
                e.printStackTrace();
            }

            try {
                vi.setInformationDetail(jArray.getJSONObject(i).getString("informationDetail"));
            } catch (JSONException e) {

                vi.setInformationDetail("null");
                e.printStackTrace();
            }

            try {
                vi.setCoverURL(jArray.getJSONObject(i).getString("coverUrl"));
            } catch (JSONException e) {
                vi.setCoverURL("null");
                e.printStackTrace();
            }



            list.add(vi);
        }
        return list;

    }


    final private class ItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view,
                                int position, long id) {

//				Intent intent = new Intent(getApplicationContext(), EpisodePlayPage.class);
                Intent intent = new Intent(getApplicationContext(), MoreInformation.class);
                InformateDate info = (InformateDate) informationList.getItemAtPosition(position);
                HashMap<String, InformateDate> map = new HashMap<String, InformateDate>();
                map.put("videoinfo", info);
                intent.putExtra("info", map);
                startActivity(intent);

        }

    }

}
