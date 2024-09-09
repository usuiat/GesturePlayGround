package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MultiTouchSampleFixed() {
  val logger = rememberLogger()
  val scope = rememberCoroutineScope()
  val dragState = remember { DragState() }
  Column {
    Box(modifier = Modifier
      .fillMaxWidth()
      .weight(1f)
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .pointerInput(Unit) {
        awaitEachGesture {
          do {
            val event = awaitPointerEvent()
            val centroid = event.calculateCentroid()
            val time = event.changes[0].uptimeMillis
            if (centroid.isSpecified) {
              scope.launch {
                dragState.dragTo(centroid)
                if (event.changes.size == 1) {
                  dragState.trackVelocity(centroid, time)
                }
                logger.log("Velocity ${dragState.velocity}")
              }
            }
          } while (event.changes.any { it.pressed })
          scope.launch { dragState.doFling() }
        }
      }
    ) {
      Image(
        modifier = Modifier
          .size(80.dp)
          .offset { dragState.offset - IntOffset(40.dp.roundToPx(), 40.dp.roundToPx()) },
        painter = painterResource(R.drawable.flamingo),
        contentDescription = null
      )
    }
    LogConsole(logger = logger, modifier = Modifier.weight(1f))
  }
}
