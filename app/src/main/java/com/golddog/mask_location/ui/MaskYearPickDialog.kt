package com.golddog.mask_location.ui

import android.app.AlertDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.NumberPicker
import androidx.fragment.app.DialogFragment
import com.golddog.mask_location.R
import kotlinx.android.synthetic.main.year_picker.*
import java.util.*


class MaskYearPickDialog : DialogFragment(){
    private val MAX_YEAR = 2099
    private val MIN_YEAR = 1900

    private var listener: OnDateSetListener? = null

    fun setListener(listener: OnDateSetListener?) {
        this.listener = listener
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(activity)
        val inflater = activity!!.layoutInflater
        val dialog: View = inflater.inflate(R.layout.year_picker, null)
        val btnCancel = dialog.findViewById<Button>(R.id.btn_cancel)
        val btnConfirm = dialog.findViewById<Button>(R.id.btn_confirm)
        val yearPicker = dialog.findViewById<NumberPicker>(R.id.picker_year)
        btnCancel?.setOnClickListener { this@MaskYearPickDialog.dialog?.cancel() }
        btnConfirm?.setOnClickListener {
            listener?.onDateSet(null, yearPicker.value, 0, 0)
            this@MaskYearPickDialog.dialog?.cancel()
        }
        yearPicker?.maxValue = 2099
        yearPicker?.minValue = 1900
        yearPicker?.value = Calendar.getInstance().get(Calendar.YEAR)
        builder.setView(dialog) // Add action buttons
        return builder.create()
    }
}