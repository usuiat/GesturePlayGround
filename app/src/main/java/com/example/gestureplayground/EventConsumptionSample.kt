package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun EventConsumptionSample() {
    val logger = rememberLogger()
    Column {
        Box(
            modifier = Modifier
              .fillMaxWidth()
              .weight(1f)
              .pointerInput(Unit) {
                awaitEachGesture {
                  do {
                    val event = awaitPointerEvent()
                    if (event.changes.any { it.isConsumed.not() }) {
                      logger.log("Box ${event.type}")
                    }
                  } while (event.changes.any { it.pressed })
                }
              },
        ) {
            Image(
                modifier = Modifier
                  .fillMaxSize()
                  .pointerInput(Unit) {
                    awaitEachGesture {
                      do {
                        val event = awaitPointerEvent()
                        logger.log("Image ${event.type}")
                        event.changes.forEach { it.consume() }
                      } while (event.changes.any { it.pressed })
                    }
                  },
                painter = painterResource(R.drawable.chipmunk),
                contentDescription = null,
                contentScale = ContentScale.Fit
            )
        }
        LogConsole(logger = logger, modifier = Modifier.weight(1f))
    }
}
