package com.golddog.mask_location.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.golddog.mask_location.R
import kotlinx.android.synthetic.main.activity_corona_manual.*

class CoronaManualActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_corona_manual)

        btn_back_corona_coc.setOnClickListener {
            finish()
        }
    }
}