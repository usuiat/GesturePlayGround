package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun TouchSlopSample() {
    val logger = rememberLogger()
    Column {
        Image(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
.pointerInput(Unit) {
    awaitEachGesture {
        var distance = 0f
        do {
            val event = awaitPointerEvent()
            distance += event.calculatePan().getDistance()
        } while (event.changes.any { it.pressed })
        if (distance > viewConfiguration.touchSlop) {
            logger.log("Drag")
        } else {
            logger.log("Tap")
        }
    }
},
            painter = painterResource(R.drawable.bumblebee),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}
