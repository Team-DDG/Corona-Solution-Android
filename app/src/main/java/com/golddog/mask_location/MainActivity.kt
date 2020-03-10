package com.golddog.mask_location

import android.os.Bundle
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private var isFabOpen = false
    val fab_open: Animation by lazy { AnimationUtils.loadAnimation(applicationContext, R.anim.fab_open) }
    val fab_close: Animation by lazy { AnimationUtils.loadAnimation(applicationContext, R.anim.fab_close) }
    val fab_rotate_forward by lazy { AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_forward) }
    val fab_rotate_backward: Animation by lazy { AnimationUtils.loadAnimation(applicationContext, R.anim.rotate_backward) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab_main_main.setOnClickListener(this)
        fab_help_main.setOnClickListener(this)
        fab_1339call_main.setOnClickListener(this)
        fab_corona_manual_main.setOnClickListener(this)
        fab_corona_now_main.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view) {
            fab_main_main -> {
                fab_anim()
                Toast.makeText(this, "fab_1", Toast.LENGTH_LONG).show()
            }
            fab_help_main -> {
                fab_anim()
                Toast.makeText(this, "fab_2", Toast.LENGTH_LONG).show()
            }
            fab_1339call_main -> {
                fab_anim()
                Toast.makeText(this, "fab_3", Toast.LENGTH_LONG).show()
            }
            fab_corona_manual_main -> {
                fab_anim()
                Toast.makeText(this, "fab_4", Toast.LENGTH_LONG).show()
            }
            fab_corona_now_main -> {
                fab_anim()
                Toast.makeText(this, "fab_5", Toast.LENGTH_LONG).show()
            }
        }
    }

    fun fab_anim(){
        if (isFabOpen){
            fab_main_main.startAnimation(fab_rotate_backward)
            fab_help_main.startAnimation(fab_close)
            fab_1339call_main.startAnimation(fab_close)
            fab_corona_manual_main.startAnimation(fab_close)
            fab_corona_now_main.startAnimation(fab_close)
            fab_help_main.isClickable = false
            fab_1339call_main.isClickable = false
            fab_corona_manual_main.isClickable = false
            fab_corona_now_main.isClickable = false
            isFabOpen = false
        } else{
            fab_main_main.startAnimation(fab_rotate_forward)
            fab_help_main.startAnimation(fab_open)
            fab_1339call_main.startAnimation(fab_open)
            fab_corona_manual_main.startAnimation(fab_open)
            fab_corona_now_main.startAnimation(fab_open)
            fab_help_main.isClickable = true
            fab_1339call_main.isClickable = true
            fab_corona_manual_main.isClickable = true
            fab_corona_now_main.isClickable = true
            isFabOpen = true
        }
    }
}
