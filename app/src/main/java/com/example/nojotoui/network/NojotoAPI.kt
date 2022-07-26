package com.example.nojotoui.network

import okhttp3.MultipartBody
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.*

interface NojotoAPI {
    @Multipart
    @POST("/api/beta/content.php")
    fun uploadImage(
        @Query("cid") cid: String = "7ec99b415af3e88205250e3514ce0fa7",
        @Part image: MultipartBody.Part
    ): Call<JsonObject>
}