package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculatePan
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.PointerInputScope
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch

class Logger() {
    val logs = mutableStateListOf<String>()

    fun printLog(log: String) {
        logs.add(log)
    }
}

@Composable
fun PointerInputKeySample() {
    var count by remember { mutableIntStateOf(0) }
    val scrollState = rememberLazyListState()
    val logger = remember(count) { Logger() }
    LaunchedEffect(logger.logs) {
        if (logger.logs.isNotEmpty()) scrollState.scrollToItem(logger.logs.lastIndex)
    }

    Column {
        Image(
            painter = painterResource(R.drawable.koala),
            contentDescription = null,
            modifier = Modifier.pointerInput(logger) {
                awaitEachGesture {
                    do {
                        val event = awaitPointerEvent()
                        logger.printLog("Gesture ${event.changes.first().position}")
                    } while (event.changes.any { it.pressed })
                }
            }
        )

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {
            items(logger.logs) { event ->
                Text(event)
            }
        }

        Button(onClick = { count++ }) { Text("Reset Log") }
    }
}