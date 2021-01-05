package com.example.zomatorsearchapp.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.example.zomatorsearchapp.R

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("imageUrl")
    fun setSrc(imageView: ImageView,imageUrl:String?){
        if(imageUrl == null || imageUrl.isEmpty() || imageUrl.isBlank()){
            imageView.load(R.drawable.restaurant)
        }
        else{
            imageView.load(imageUrl)
        }
    }
}