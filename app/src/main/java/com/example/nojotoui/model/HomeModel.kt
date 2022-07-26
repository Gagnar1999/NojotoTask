package com.example.nojotoui.model

data class HomeModel(
    val imgUrl : String,
    val profileUrl : String,
    var isLikeButtonActive : Boolean,
    val time : String,
    val userName : String
)
