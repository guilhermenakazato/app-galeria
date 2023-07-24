package com.example.frontend.components

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.example.frontend.R

class PermissionDialogFragment(private val showPermission: () -> Unit): DialogFragment() {
    @SuppressLint("InflateParams")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        this.isCancelable = false

        return activity?.let {
            val inflater = requireActivity().layoutInflater.inflate(R.layout.fragment_permission_dialog, null);

            val builder = AlertDialog.Builder(it)
            val tryAgainButton: Button = inflater.findViewById(R.id.permission_button)

            tryAgainButton.setOnClickListener {
                dismiss()
                showPermission()
            }

            builder.setView(inflater)

            builder.create().apply {
                window?.setGravity(Gravity.BOTTOM)
                window?.setBackgroundDrawableResource(R.drawable.dialog_background)
            }
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}