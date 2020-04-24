package com.golddog.mask_location.ui.activity

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseActivity
import com.golddog.mask_location.databinding.ActivityMaskBinding
import com.golddog.mask_location.ui.dialog.MaskYearPickDialog
import com.golddog.mask_location.viewmodel.MaskViewModel
import com.golddog.mask_location.viewmodelfactory.MaskViewModelFactory
import kotlinx.android.synthetic.main.activity_mask.*

class MaskActivity : BaseActivity<ActivityMaskBinding>() {
    override val layoutResourceId: Int = R.layout.activity_mask

    private lateinit var viewModel: MaskViewModel
    private lateinit var viewModelFactory: MaskViewModelFactory
    private var year = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModelFactory = MaskViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MaskViewModel::class.java)

        binding.vm = viewModel

        val maskDialogListener = DatePickerDialog.OnDateSetListener { _, p1, _, _ ->
            year = p1
            binding.vm?.year?.value = year
            tv_year_mask.text = year.toString()
        }

        tv_year_mask.setOnClickListener {
            val maskYearPickDialog = MaskYearPickDialog()
            maskYearPickDialog.setListener(maskDialogListener)
            maskYearPickDialog.show(supportFragmentManager, "maskYearPickDialog")
        }

        btn_back_mask.setOnClickListener {
            finish()
        }
    }
}