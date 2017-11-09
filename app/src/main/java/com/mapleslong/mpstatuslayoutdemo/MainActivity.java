package com.mapleslong.mpstatuslayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.mapleslong.widget.MPStatusLayout;

public class MainActivity extends AppCompatActivity {
    MPStatusLayout mStatusLayout;
    Button btnSuccess;
    Button btnError;
    Button btnNoNetwork;
    Button btnEmpty;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusLayout = (MPStatusLayout) findViewById(R.id.status_layout);
        btnSuccess = (Button) findViewById(R.id.btn_success);
        btnError = (Button) findViewById(R.id.btn_error);
        btnEmpty = (Button) findViewById(R.id.btn_empty);
        btnNoNetwork = (Button) findViewById(R.id.btn_nonetwork);

        /**
         * 可以在application中设置MPStatusLayoutConfig进行全局配置和更换状态图片
         * 以下是简单使用的方式
         */

        mStatusLayout.setStatus(MPStatusLayout.STATUS_LOADING);


        btnSuccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayout.setStatus(MPStatusLayout.STATUS_SUCCESS);
            }
        });

        btnEmpty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayout.setStatus(MPStatusLayout.STATUS_EMPTY);
            }
        });

        btnError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayout.setStatus(MPStatusLayout.STATUS_ERROR);
                mStatusLayout.setErrorText("改变一下内容");
            }
        });


        btnNoNetwork.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mStatusLayout.setStatus(MPStatusLayout.STATUS_NONETWORK, new MPStatusLayout.onReloadListener() {
                    @Override
                    public void onReLoad(View v) {
                        Toast.makeText(MainActivity.this, "按了重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
