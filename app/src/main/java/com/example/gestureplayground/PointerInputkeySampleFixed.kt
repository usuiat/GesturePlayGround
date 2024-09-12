package com.example.gestureplayground

import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource

@Composable
fun PointerInputKeySampleFixed() {
    Column {
        var count by remember { mutableIntStateOf(1) }
        val logger = remember(count) { Logger().apply { log("Log:$count") } }

        Image(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .pointerInput(key1 = logger) {
                    awaitEachGesture {
                        do {
                            val event = awaitPointerEvent()
                            logger.log("${event.type}")
                        } while (event.changes.any { it.pressed })
                    }
                },
            painter = painterResource(R.drawable.dolphin),
            contentDescription = null,
        )

        LogConsole(logger = logger, modifier = Modifier.weight(1f))

        Button(onClick = { count++ }) { Text("Reset Log") }
    }
}