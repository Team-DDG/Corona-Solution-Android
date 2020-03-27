package com.golddog.mask_location.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.golddog.mask_location.R
import com.golddog.mask_location.base.BaseActivity
import com.golddog.mask_location.base.BaseApplication
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class InfoWindowDialog : BottomSheetDialogFragment(){

    private lateinit var textView: TextView
    private lateinit var text: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.dialog_infowindow, container, false)
        textView = view.findViewById(R.id.tv_infowindow)
        textView.text = text
        return view
    }

    fun setInfo(info: String){
        text = info
    }
}