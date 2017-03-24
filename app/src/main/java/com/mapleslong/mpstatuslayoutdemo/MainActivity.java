package com.mapleslong.mpstatuslayoutdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.mapleslong.widget.MPStatusLayout;

public class MainActivity extends AppCompatActivity {
  MPStatusLayout mStatusLayout;
  Button btn;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    mStatusLayout = (MPStatusLayout) findViewById(R.id.status_layout);
    mStatusLayout.setStatus(MPStatusLayout.STATUS_LOADING);
    btn = (Button) findViewById(R.id.btn);
    btn.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View v) {
        mStatusLayout.setErrorText("改变一下");
      }
    });
  }
}
