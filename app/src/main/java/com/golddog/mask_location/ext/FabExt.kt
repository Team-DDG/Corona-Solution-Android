package com.golddog.mask_location.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.golddog.mask_location.util.FabAnimation

@BindingAdapter("fab_status")
fun change_fab_status(view: View, fab_status:Boolean?){
    fab_status?.let {
        val animation = if(it) FabAnimation.fabOpen() else FabAnimation.fabClose()
        view.visibility = if(it) View.VISIBLE else View.INVISIBLE
        view.isClickable = fab_status
        view.startAnimation(animation)
    }
}

@BindingAdapter("label_status")
fun change_label_status(view:View, label_status:Boolean?){
    label_status?.let {
        val animation = if(it) FabAnimation.fabOpen() else FabAnimation.fabClose()
        view.visibility = if (it) View.VISIBLE else View.INVISIBLE
        view.startAnimation(animation)
    }
}