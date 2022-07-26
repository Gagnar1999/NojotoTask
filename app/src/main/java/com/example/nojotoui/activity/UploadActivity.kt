package com.example.nojotoui.activity

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.nojotoui.databinding.ActivityUploadBinding
import com.example.nojotoui.network.RetrofitFactory
import com.google.gson.Gson
import com.google.gson.JsonObject
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import kotlin.math.log


class UploadActivity : AppCompatActivity() {

    private val binding: ActivityUploadBinding by lazy {
        ActivityUploadBinding.inflate(layoutInflater)
    }
    var file: File? = null
    var isFileUploading : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        try {
            file = File(intent.getStringExtra("FILE"))
            Log.d("TAG", "onCreate: "+file.toString())
            Glide.with(this).load(file).into(binding.image)
            val requestBody = file?.asRequestBody("image/*".toMediaTypeOrNull())
            Log.d("TAG", "onCreate: "+requestBody.toString())
            requestBody?.let {
                Log.d("TAG", "onCreate: request body not null")
                val body = MultipartBody.Part.createFormData("image", file!!.name, it)
                RetrofitFactory.apiService.uploadImage(image = body).enqueue(object :
                    Callback<JsonObject>{
                    override fun onResponse(
                        call: Call<JsonObject>,
                        response: Response<JsonObject>
                    ) {
                        isFileUploading = false
                        Log.d("TAG", "onResponse: " + Gson().toJson(response.body()))
                        if(response.code() == 200){
                            val success = response.body()?.get("success")
                            val failure = response.body()?.get("error")
                            success?.let {
                                if(it.asBoolean && failure?.asBoolean == false){
                                    Toast.makeText(this@UploadActivity, "File Uploaded Successfully", Toast.LENGTH_SHORT).show()
                                }
                            }

                        }
                    }

                    override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                        Log.d("TAG", "onFailure: "+t.message)
                    }

                })
            }

        } catch (e: Exception) {
            Log.d("TAG", "onCreate: "+e.message)
        }

    }
}