package com.example.gestureplayground

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun rememberLogger() = remember { Logger() }

@Stable
class Logger {
    private val _logs = mutableStateListOf<String>()
    val logs: List<String> = _logs

    fun log(log: String) {
        _logs.add(log)
    }
}

@Composable
fun LogConsole(logger: Logger, modifier: Modifier = Modifier) {
    val scrollState = rememberLazyListState()
    LaunchedEffect(logger.logs.size) {
        if (logger.logs.isNotEmpty()) {
            scrollState.scrollToItem(logger.logs.lastIndex)
        }
    }
    LazyColumn(state = scrollState, modifier = modifier) {
        items(logger.logs) { event ->
            Text(event)
        }
    }
}
