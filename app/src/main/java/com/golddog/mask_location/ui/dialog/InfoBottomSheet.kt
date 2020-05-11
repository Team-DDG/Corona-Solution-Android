package com.golddog.mask_location.ui.dialog

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.golddog.mask_location.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_infowindow.*

class InfoBottomSheet : BottomSheetDialogFragment(){

    private lateinit var text: SpannableString

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_infowindow, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tv_infowindow.text = text
        btn_infowindow.setOnClickListener {
            val uri = "nmap://map?&appname=com.golddog.mask_location"
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(uri)))
        }
    }

    fun showWithInfo(context: Context, info : SpannableString) {
        text = info
        show((context as FragmentActivity).supportFragmentManager, "infoWindow")
    }
}