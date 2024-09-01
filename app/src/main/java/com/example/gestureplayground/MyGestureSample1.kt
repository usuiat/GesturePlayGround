package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun MyGestureSample1() {
    val logger = rememberLogger()
    var mirroring by remember { mutableStateOf(false) }
    Column {
        Image(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .pointerInput(Unit) {
                awaitEachGesture {
                    var dx = 0f
                    do {
                        val event = awaitPointerEvent()
                        dx += event.calculatePan().x
                    } while (event.changes.any { it.pressed })

                    if (dx > 100) {
                        logger.log("Swipe Right")
                        mirroring = true
                    } else if (dx < -100) {
                        logger.log("Swipe Left")
                        mirroring = false
                    }
                }
            }
            .graphicsLayer { rotationY = if (mirroring) 180f else 0f },
            painter = painterResource(R.drawable.bumblebee),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}
