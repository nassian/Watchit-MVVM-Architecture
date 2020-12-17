package com.nassiansoft.watchit

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*


fun View.show(boolean: Boolean){
    visibility=if (boolean) View.VISIBLE
    else View.GONE
}

fun View.showSnack(msg:String){
    Snackbar.make(this,msg,Snackbar.LENGTH_SHORT).show()
}

@BindingAdapter("android:url")
fun ImageView.setImageUrl(url:String?){

    url?.let {
        Glide.with(this).load(it)
            .placeholder(R.drawable.ic_baseline_local_movies_24)
                .transform( RoundedCorners(16))
                .into(this)
    }
}

fun formatDate(stringDate:String):String?{
    ///2006-01-15T08:00:00Z
    val simpleDateFormat=SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
    val date: Date? =simpleDateFormat.parse(stringDate)
    val convertDateFormat=SimpleDateFormat("MMM dd yyyy")
    return convertDateFormat.format(date)
}

@BindingAdapter("android:date")
fun TextView.setDate(date:String?){
    date?.let {
        text= formatDate(date)
    }
}

@BindingAdapter("android:duration")
fun TextView.setDuration(milliSecond:String?){
    milliSecond?.let {
        val minutes=(it.toBigIntegerOrNull())?.div((1000*60).toBigInteger())?.toInt()

        text= String.format("%d minutes",minutes)
    }
}