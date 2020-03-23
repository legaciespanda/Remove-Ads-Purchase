package com.ernest.removeadspurchase.controller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.ernest.removeadspurchase.R;
import com.ernest.removeadspurchase.viewmodel.MainActivityViewModel;

public class MainActivity extends AppCompatActivity {

    //An instance of the MainActivityViewModel
    private MainActivityViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel.class);
    }
}
