package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource

@Composable
fun DetectTapGesturesSample() {
    val logger = rememberLogger()
    Column {
        Image(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .pointerInput(Unit) {
                detectTapGestures { offset -> logger.log("Click at $offset") }
            },
            painter = painterResource(R.drawable.bumblebee),
            contentDescription = null,
        )
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}