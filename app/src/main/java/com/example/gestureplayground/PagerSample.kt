package com.example.gestureplayground

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun PagerSample() {
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
        HorizontalPager(
            state = rememberPagerState { 3 },
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
        ) {
            var scale by remember { mutableFloatStateOf(1f) }
            Image(
                painter = painterResource(R.drawable.koala),
                contentDescription = null,
                modifier = Modifier.clipToBounds()
                    .scale(scale)
                    .pointerInput(Unit) {
                        detectTransformGestures { centroid, pan, zoom, rotation ->
                            scale *= zoom
                            printLog("centroid=(%.0f, %.0f) scale=%.2f".format(centroid.x, centroid.y, scale))
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