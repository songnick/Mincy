package com.songnick.mincy.adapter

import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import java.io.File

@BindingAdapter("imageFromUrl")
fun bindImageUrl(view: ImageView, imageUrl: String?) {
    if (!imageUrl.isNullOrEmpty()){
        val file = File(imageUrl)
        Glide.with(view.context)
            .load(file)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(view)
    }
}