package com.golddog.mask_location.util

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseApplication

// 일시적 미사용, 추후 리팩토링시 사용 예정
object FabAnimation {
    fun fabOpen(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.fab_open)
    }

    fun fabClose(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.fab_close)
    }

    fun fabRotateForward(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.rotate_forward)
    }

    fun fabRotateBackward(): Animation {
        return AnimationUtils.loadAnimation(BaseApplication.appContext, R.anim.rotate_backward)
    }
}