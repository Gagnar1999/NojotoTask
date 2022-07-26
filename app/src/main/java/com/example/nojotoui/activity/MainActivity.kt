package com.example.nojotoui.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nojotoui.R
import com.example.nojotoui.adapter.MainAdapter
import com.example.nojotoui.databinding.ActivityMainBinding
import com.example.nojotoui.model.HomeModel
import com.google.android.material.navigation.NavigationBarView

class MainActivity : AppCompatActivity() {
    val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    val list = mutableListOf<HomeModel>()
    lateinit var mainAdapter: MainAdapter
    val PERMISSION_CAMERA_REQUEST_CODE = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar);
        getSupportActionBar()?.setDisplayShowTitleEnabled(false)

        //Generation of Dummy Data
        for (i in 1..10) {
            list.add(HomeModel("https://images.unsplash.com/photo-1503424886307-b090341d25d1?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxleHBsb3JlLWZlZWR8M3x8fGVufDB8fHx8&w=1000&q=80",
                "https://m.media-amazon.com/images/M/MV5BOTBhMTI1NDQtYmU4Mi00MjYyLTk5MjEtZjllMDkxOWY3ZGRhXkEyXkFqcGdeQXVyNzI1NzMxNzM@._V1_UY1200_CR92,0,630,1200_AL_.jpg",
                true,
                "1 mins ago",
                "Johny Depp"
            ))
        }
        mainAdapter = MainAdapter(list)

        binding.listRv.apply {
            adapter = mainAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
            isNestedScrollingEnabled = false
        }

        binding.bottomNavigation.setOnItemSelectedListener (object : NavigationBarView.OnItemSelectedListener{
            override fun onNavigationItemSelected(item: MenuItem): Boolean {
                if(item.itemId == R.id.camera){
                    if(hasCameraPermission())
                        startActivity(Intent(this@MainActivity, CameraActivity::class.java))
                    else
                        requestCameraPermission()
                    return true
                }
                return false
            }

        })
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),PERMISSION_CAMERA_REQUEST_CODE)
    }

    private fun hasCameraPermission(): Boolean{
        return ContextCompat.checkSelfPermission(this,
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == PERMISSION_CAMERA_REQUEST_CODE && grantResults[0]==PackageManager.PERMISSION_GRANTED){
            startActivity(Intent(this@MainActivity, CameraActivity::class.java))
        }
    }
}