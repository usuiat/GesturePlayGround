package com.example.gestureplayground

import android.annotation.SuppressLint
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.calculateZoom
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ZoomableSample2() {
    val logs = remember { mutableStateListOf<String>() }
    val scrollState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val printLog = {log: String ->
        logs.add(log)
        scope.launch {
            scrollState.scrollToItem(logs.lastIndex)
        }
    }

    Column {
        val imageResources = listOf(
            R.drawable.koala,
            R.drawable.jellyfish,
            R.drawable.iguana,
        )
        HorizontalPager(
            state = rememberPagerState { imageResources.size },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) { pageIndex ->
            val painter = painterResource(imageResources[pageIndex])
            val zoomState = rememberZoomState2()
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.clipToBounds()
                    .scale(zoomState.scale)
                    .zoomable(zoomState)
//                    .pointerInput(Unit) {
//                        awaitEachGesture {
//                            do {
//                                val event = awaitPointerEvent()
//                                val zoom = event.calculateZoom()
//                                if (zoomState.canConsumeGesture(zoom)) {
//                                    zoomState.applyZoom(zoom)
//                                    event.changes.forEach { it.consume() }
//                                    printLog("$zoomState")
//                                }
//                            } while (event.changes.any { it.pressed })
//                        }
//                    }
            )
        }

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {
            items(logs) { event ->
                Text(event)
            }
        }
    }
}

@SuppressLint("ModifierFactoryUnreferencedReceiver")
fun Modifier.zoomable(zoomState: ZoomState2): Modifier = composed {
    Modifier.pointerInput(Unit) {
        awaitEachGesture {
            do {
                val event = awaitPointerEvent()
                val zoom = event.calculateZoom()
                if (zoomState.canConsumeGesture(zoom)) {
                    zoomState.applyZoom(zoom)
                    event.changes.forEach { it.consume() }
                }
            } while (event.changes.any { it.pressed })
        }
    }
}

@Stable
class ZoomState2 {
    var scale by mutableFloatStateOf(1f)
        private set

    fun canConsumeGesture(zoom: Float): Boolean {
        return scale != 1f || zoom != 1f
    }

    fun applyZoom(zoom: Float) {
        scale = (scale * zoom).coerceAtLeast(1f)
    }
}

@Composable
fun rememberZoomState2() = remember { ZoomState2() }