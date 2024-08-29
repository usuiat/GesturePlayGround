package com.example.gestureplayground

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.util.fastAll
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MyGestureSamples() {
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
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .pointerInput(Unit) {
                    awaitEachGesture {
                        do {
                            val event = awaitPointerEvent()
                            if (event.changes.any { it.isConsumed.not() }) {
                                printLog("Gesture on Box")
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
        ) {
            Image(
                painter = painterResource(R.drawable.koala),
                contentDescription = null,
                modifier = Modifier.pointerInput(Unit) {
                    awaitEachGesture {
                        do {
                            val event = awaitPointerEvent()
                            if (event.calculatePan().x > 0) {
                                event.changes.forEach { change -> change.consume() }
                                printLog("Gesture on Image")
                            }
                        } while (event.changes.any { it.pressed })
                    }
                }
            )
        }

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {
            items(logs) { event ->
                Text(event)
            }
        }
    }
}