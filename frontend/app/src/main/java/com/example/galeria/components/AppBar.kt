package com.example.galeria.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.galeria.R
import com.example.galeria.ui.theme.Black

@Composable
fun AppBar(shouldShowLogo: Boolean = true, shouldShowBackButton: Boolean = false, onBackButtonClick: (() -> Unit)?) {
    Row(
        Modifier
            .background(Black)
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        Arrangement.Center,
        Alignment.CenterVertically,
    ) {
        if(shouldShowBackButton) {
            Box(modifier = Modifier.weight(1f)) {
                BackButton(modifier = Modifier, onClick = {
                    if (onBackButtonClick != null) {
                        onBackButtonClick()
                    }
                })
            }
        }

        if(shouldShowLogo) {
            Image(
                painter = painterResource(R.drawable.logo),
                contentDescription = "Logo",
                modifier = Modifier.padding(vertical = 10.dp).weight(1f),
            )
        }

        if(shouldShowBackButton) {
            Spacer(modifier = Modifier.border(1.dp, Color.White).weight(1f))
        }
    }
}