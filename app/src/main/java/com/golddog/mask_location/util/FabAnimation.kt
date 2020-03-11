package com.golddog.mask_location.util

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseApplication

object FabAnimation {
    fun fabOpen(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.fab_open)
    }

    fun fabClose(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.fab_close)
    }

    fun fabRotateFoward(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.rotate_forward)
    }

    fun fabRotateBackward(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.rotate_backward)
    }
}