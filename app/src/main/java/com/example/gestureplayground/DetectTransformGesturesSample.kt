package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize

@Composable
fun DetectTransformGesturesSample() {
    val logger = rememberLogger()
    var scale by remember { mutableFloatStateOf(1f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    Column {
        Image(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clipToBounds()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
                translationX = offset.x
                translationY = offset.y
            }
            .pointerInput(Unit) {
                detectTransformGestures { centroid, _, zoom, _ ->
                    scale *= zoom
                    offset = calculateOffset(scale, centroid, size)
                    logger.log("centroid=(%.0f, %.0f) scale=%.2f".format(centroid.x, centroid.y, scale))
                }
            },
            painter = painterResource(R.drawable.bumblebee),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}

private fun calculateOffset(scale: Float, centroid: Offset, size: IntSize): Offset {
    val y = (size.height / 2 - centroid.y) * (scale - 1f)
    val x = (size.width / 2 - centroid.x) * (scale - 1f)
    return Offset(x, y)
}
