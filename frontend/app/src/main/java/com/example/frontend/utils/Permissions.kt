package com.example.frontend.utils

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat

fun AppCompatActivity.isPermissionGranted(permission: String) =
    ActivityCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED

fun AppCompatActivity.requestPermission(permission: Array<String>, requestId: Int) =
    ActivityCompat.requestPermissions(this, permission, requestId)