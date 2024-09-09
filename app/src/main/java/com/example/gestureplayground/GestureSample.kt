package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun GestureSample() {
  val logger = rememberLogger()
  var mirroring by remember { mutableStateOf(false) }
  Column {
    Image(modifier = Modifier
      .fillMaxWidth()
      .weight(1f)
      .pointerInput(Unit) {
        awaitEachGesture {
          var dx = 0f
          do {
            val event = awaitPointerEvent()
            dx += event.calculatePan().x
          } while (event.changes.any { it.pressed })

          if (dx > 100) {
            logger.log("Swipe Right")
            mirroring = false
          } else if (dx < -100) {
            logger.log("Swipe Left")
            mirroring = true
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
