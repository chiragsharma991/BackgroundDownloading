package com.project.nat.backgrounddownloading;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class TestOne extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_one);
        Log.e("TAG", "onCreate:   test " );


    }



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
