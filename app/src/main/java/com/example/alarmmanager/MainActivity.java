package com.example.alarmmanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.alarmmanager.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;
    private Button mButton;

    private int mCount;

    public static final String TAG = "main1";
    public static final String KEY = "key";

    ActivityMainBinding mBinding;
    MyViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // ViewModel 與 onSaveInstanceState()效果相同, 但程序被系統終止後之數據仍丟失(需要持久化技術)
        // 程序進入後台被系統殺死(因為內存緊張) != 程序終止

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

//        mBinding.setViewModel(mViewModel);
//        mBinding.setLifecycleOwner(this);

        if(savedInstanceState != null) {
            mCount = savedInstanceState.getInt(KEY);
            mBinding.textView.setText(String.valueOf(mCount));
        }

        mBinding.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });

    }

    private void increment() {
        mCount++;
        mBinding.textView.setText(String.valueOf(mCount));
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY, mCount);
    }
}
