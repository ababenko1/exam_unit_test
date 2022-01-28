package com.example.testapplication.presentation.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.testapplication.R


fun ImageView.load(url: String?) {
    Glide
        .with(context)
        .load(url)
        .apply(
            RequestOptions()
                .placeholder(R.drawable.placeholder_user)
                .circleCrop()
        )
        .into(this)
}