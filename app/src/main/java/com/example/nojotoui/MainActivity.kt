package com.example.nojotoui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.nojotoui.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val binding : ActivityMainBinding by lazy{
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar);
        getSupportActionBar()?.setDisplayShowTitleEnabled(false);
    }

}