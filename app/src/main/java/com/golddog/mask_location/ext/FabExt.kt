package com.golddog.mask_location.ext

import android.view.View
import androidx.databinding.BindingAdapter
import com.golddog.mask_location.util.FabAnimation

object FabExt {
    @BindingAdapter("fab_status")
    fun change_status(view:View, fab_status:Boolean){
        //true is change to visible, false is change to invisible
        val animation = if(fab_status) FabAnimation.fabOpen() else FabAnimation.fabClose()
        view.visibility = if(fab_status) View.VISIBLE else View.INVISIBLE
        view.isClickable = fab_status
        view.startAnimation(animation)
    }
}