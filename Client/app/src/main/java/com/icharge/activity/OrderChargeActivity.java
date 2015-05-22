package com.icharge.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.icharge.utils.DateTimePickDialogUtil;

/**
 * Created by Administrator on 2015/4/14.
 */
public class OrderChargeActivity extends Activity {


    private EditText startDateTime;
    private EditText endDateTime;
    private Button order;

    private AlertDialog orderAlert;

    private String initStartDateTime = "2013年9月3日 14:44"; // 初始化开始时间
    private String initEndDateTime = "2014年8月23日 17:44"; // 初始化结束时间


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.order_charge);

        // 两个输入框
        startDateTime = (EditText) findViewById(R.id.inputDate);
        endDateTime = (EditText) findViewById(R.id.inputDate2);

//        预约
        order = (Button) findViewById(R.id.order_charge);

        startDateTime.setText(initStartDateTime);
        endDateTime.setText(initEndDateTime);

        order.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                initOrderConfirmDialog();
                orderAlert.show();

            }
        });

        startDateTime.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        OrderChargeActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(startDateTime);

            }
        });

        endDateTime.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                DateTimePickDialogUtil dateTimePicKDialog = new DateTimePickDialogUtil(
                        OrderChargeActivity.this, initEndDateTime);
                dateTimePicKDialog.dateTimePicKDialog(endDateTime);
            }
        });

    }


    private void initOrderConfirmDialog(){
        if (orderAlert == null){
            orderAlert = new AlertDialog.Builder(this)
                    .setMessage("确定预约？")
                    .setPositiveButton("是", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            Toast.makeText(OrderChargeActivity.this, getResources().getText(R.string.order_charge_succ), Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("否", null).create();


        }
    }
}
