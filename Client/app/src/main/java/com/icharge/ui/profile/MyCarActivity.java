package com.icharge.ui.profile;

import android.app.Activity;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;

import com.icharge.activity.R;

/**
 * Created by Administrator on 2015/4/25.
 */
public class MyCarActivity extends Activity{

    private ImageView mImg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.my_car);
        initView();

    }

    private void initView() {

        initRoundImge();
    }

    private void initRoundImge() {

        mImg = (ImageView) findViewById(R.id.main_image);

        //获得imageview中设置的图片
        BitmapDrawable drawable = (BitmapDrawable) mImg.getDrawable();
        Bitmap bmp = drawable.getBitmap();
        //获得图片的宽，并创建结果bitmap
        int width = bmp.getWidth();
        Bitmap resultBmp = Bitmap.createBitmap(width, width,
                Bitmap.Config.ARGB_8888);
        Paint paint = new Paint();
        Canvas canvas = new Canvas(resultBmp);
        //画圆
        canvas.drawCircle(width / 2, width / 2, width / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));// 选择交集去上层图片
        canvas.drawBitmap(bmp, 0, 0, paint);
        mImg.setImageBitmap(resultBmp);



    }



}
