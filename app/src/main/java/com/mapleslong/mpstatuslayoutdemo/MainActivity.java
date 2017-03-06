package com.mapleslong.mpstatuslayoutdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapleslong.widget.MPStatusLayout;

public class MainActivity extends AppCompatActivity {
    MPStatusLayout mStatusLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStatusLayout= (MPStatusLayout) findViewById(R.id.status_layout);
        mStatusLayout.setStatus(MPStatusLayout.STATUS_NONETWORK);
    }
}
