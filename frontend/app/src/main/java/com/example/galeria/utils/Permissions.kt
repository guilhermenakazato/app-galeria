package com.example.galeria.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.example.galeria.R
import com.example.galeria.components.PermissionDialog
import com.example.galeria.screens.openAppSettings
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

class Permissions {
    companion object {
        @OptIn(ExperimentalPermissionsApi::class)
        @Composable
        fun singlePermission(permission: String) {
            val readMediaPermissionState = rememberPermissionState(permission = permission)
            val shouldShowRationale = readMediaPermissionState.status.shouldShowRationale

            if (!readMediaPermissionState.status.isGranted) {
                showPermissionDialog(shouldShowRationale) {
                    readMediaPermissionState.launchPermissionRequest()
                }
            }
        }

        @OptIn(ExperimentalPermissionsApi::class)
        @Composable
        fun multiplePermissions(permissions: List<String>) {
            val readMediaPermissionsState = rememberMultiplePermissionsState(permissions = permissions)
            val shouldShowRationale = readMediaPermissionsState.shouldShowRationale

            if (!readMediaPermissionsState.allPermissionsGranted) {
                showPermissionDialog(shouldShowRationale) {
                    readMediaPermissionsState.launchMultiplePermissionRequest()
                }
            }
        }

        @Composable
        private fun showPermissionDialog(shouldShowRationale: Boolean, launchSystemDialog: () -> Unit) {
            val confirmed = remember { mutableStateOf(false) }
            val openDialog = remember { mutableStateOf(true) }

            if(shouldShowRationale) {
                if(openDialog.value) {
                    PermissionDialog(
                        onDismissRequest = {  },
                        onConfirm = { confirmed.value = true },
                        description = "Você recusou a permissão, para continuar utilizando a Galeria permita o acesso.",
                        buttonText = "Configurações",
                        icon = R.drawable.ic_alert
                    )
                }

                if(confirmed.value) {
                    openAppSettings()
                    confirmed.value = false
                }
            } else {
                SideEffect {
                    launchSystemDialog()
                }
            }
        }
    }
}