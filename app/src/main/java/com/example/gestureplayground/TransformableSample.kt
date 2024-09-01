package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun TransformableSample() {
    val logger = rememberLogger()
    var scale by remember { mutableFloatStateOf(1f) }
    Column {
        Image(modifier = Modifier
            .fillMaxWidth()
            .weight(1f)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .clipToBounds()
            .graphicsLayer {
                scaleX = scale
                scaleY = scale
            }
            .transformable(
                state = rememberTransformableState { zoomChange, _, _ ->
                    scale *= zoomChange
                    logger.log("scale=%.2f".format(scale))
                }
            ),
            painter = painterResource(R.drawable.bumblebee),
            contentDescription = null,
            contentScale = ContentScale.Fit
        )
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}
