package com.golddog.mask_location.ui.dialog

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.net.Uri
import android.os.Bundle
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.golddog.mask_location.R
import com.golddog.mask_location.ext.showToast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_infowindow.*
import java.net.URLEncoder
import kotlin.properties.Delegates

class InfoBottomSheet : BottomSheetDialogFragment(), View.OnClickListener {

    private lateinit var text: SpannableString
    private lateinit var name: String
    private var lat by Delegates.notNull<Double>()
    private var lng by Delegates.notNull<Double>()

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
        btn_infowindow.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val url =
            "nmap://place?lat=${lat}&lng=${lng}&name=${URLEncoder.encode(name, "UTF-8")}&appname=com.golddog.mask_location"

        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        intent.addCategory(Intent.CATEGORY_BROWSABLE)

        val list: List<ResolveInfo> =
            activity!!.packageManager.queryIntentActivities(
                intent,
                PackageManager.MATCH_DEFAULT_ONLY
            )
        if (list == null || list.isEmpty()) {
            activity?.showToast("네이버 지도가 설치되어 있지 않습니다. 스토어로 이동합니다.")
            context!!.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("market://details?id=com.nhn.android.nmap")
                )
            )
        } else {
            activity!!.startActivity(intent)
            activity?.showToast("병원 이름이 아니더라도 위치는 동일합니다.")
        }
    }

    fun showWithInfo(context: Context, info: SpannableString, lat: Double, lng: Double, name: String) {
        text = info
        show((context as FragmentActivity).supportFragmentManager, "infoWindow")
        this.lat = lat
        this.lng = lng
        this.name = name
    }
}