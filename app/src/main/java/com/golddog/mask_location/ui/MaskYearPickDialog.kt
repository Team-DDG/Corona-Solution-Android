package com.golddog.mask_location.ui

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.golddog.mask_location.R
import kotlinx.android.synthetic.main.year_picker.*
import java.util.*


class MaskYearPickDialog : DialogFragment() {

    private var listener: OnDateSetListener? = null

    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.year_picker, null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_cancel.setOnClickListener { this@MaskYearPickDialog.dialog?.cancel() }
        btn_confirm.setOnClickListener {
            listener?.onDateSet(null, picker_year.value, 0, 0)
            this@MaskYearPickDialog.dialog?.cancel()
        }
        picker_year.maxValue = 2099
        picker_year.minValue = 1900
        picker_year.value = Calendar.getInstance().get(Calendar.YEAR)
    }
}