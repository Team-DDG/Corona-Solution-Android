package com.golddog.mask_location.ui.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.golddog.mask_location.R
import com.gun0912.tedpermission.TedPermissionResult
import com.tedpark.tedpermission.rx2.TedRx2Permission

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupPermission()
    }

    @SuppressLint("CheckResult")
    private fun setupPermission() {
        TedRx2Permission.with(this)
            .setRationaleTitle(R.string.require_authority)
            .setRationaleMessage(R.string.require_authority_content)
            .setPermissions(Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION)
            .request()
            .subscribe { tedPermissionResult: TedPermissionResult ->
                if (tedPermissionResult.isGranted) {
                    Toast.makeText(this, R.string.permisstion_granted, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Toast.makeText(
                        this,
                        R.string.permission_denied.toString() + tedPermissionResult.deniedPermissions.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
    }
}
