package com.example.nojotoui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.example.nojotoui.databinding.ActivityUploadBinding
import java.io.File

class UploadActivity : AppCompatActivity() {

    private val binding: ActivityUploadBinding by lazy {
        ActivityUploadBinding.inflate(layoutInflater)
    }
    var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        try {
            file = File(intent.getStringExtra("FILE"))
            Log.d("TAG", "onCreate: "+file.toString())
            Glide.with(this).load(file).into(binding.image)

        } catch (e: Exception) {
            Log.d("TAG", "onCreate: "+e.message)
        }

    }
}