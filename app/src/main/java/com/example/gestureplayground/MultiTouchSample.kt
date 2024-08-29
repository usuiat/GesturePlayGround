package com.example.gestureplayground

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.exponentialDecay
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculateCentroid
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.isSpecified
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.util.VelocityTracker
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun MultiTouchSample() {
    val logs = remember { mutableStateListOf<String>() }
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val printLog = { log: String ->
        logs.add(log)
        scope.launch {
            scrollState.scrollToItem(logs.lastIndex)
        }
    }

    Column {
        val x = remember { Animatable(0f) }
        val y = remember { Animatable(0f) }
        val velocityTracker = remember { VelocityTracker() }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        do {
                            val event = awaitPointerEvent()
                            val centroid = event.calculateCentroid()
                            if (centroid.isSpecified) {
                                scope.launch {
                                    x.snapTo(centroid.x)
                                    y.snapTo(centroid.y)
                                    velocityTracker.addPosition(event.changes.first().uptimeMillis, centroid)
                                }
                            }
                        } while (event.changes.any { it.pressed })
                        val velocity = velocityTracker.calculateVelocity()
                        scope.launch {
                            x.animateDecay(velocity.x, exponentialDecay())
                        }
                        scope.launch {
                            y.animateDecay(velocity.y, exponentialDecay())
                        }
                        velocityTracker.resetTracking()
                    }
                }
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .offset { IntOffset(x.value.toInt(), y.value.toInt()) }
                    .background(MaterialTheme.colorScheme.primary)
            )
        }

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {
            items(logs) { event ->
                Text(event)
            }
        }
    }
}