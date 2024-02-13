package com.example.galeria.utils

import android.app.Activity
import android.view.View
import androidx.core.view.WindowCompat

class PhoneSystem(val view: View) {
    inner class Interface {
        private val window = (view.context as Activity).window
        private val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)

        fun hide(hideSection: Int) {
            windowInsetsController.hide(hideSection)
        }

        fun show(showSection: Int) {
            windowInsetsController.show(showSection)
        }
    }
}