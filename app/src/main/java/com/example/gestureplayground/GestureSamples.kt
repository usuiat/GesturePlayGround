package com.example.gestureplayground

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntSize
import kotlinx.coroutines.launch


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GestureSamples() {
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
        var scale by remember { mutableFloatStateOf(1f) }
        var offset by remember { mutableStateOf(Offset.Zero) }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                // その1
//                .clickable(
//                    onClickLabel = "clickableの動作を確認する",
//                    role = Role.Button
//                ) { printLog("Click") }
                // その2
//                .pointerInput(Unit) {
//                    detectTapGestures { offset -> printLog("Click at $offset") }
//                }
                .transformable(
                    state = rememberTransformableState { zoomChange, panChange, rotationChange ->
                        scale *= zoomChange
                        printLog("scale=%.2f".format(scale))
                    }
                )
//                .pointerInput(Unit) {
//                    detectTransformGestures { centroid, pan, zoom, rotation ->
//                        scale *= zoom
//                        offset = calclateOffset(scale, centroid, size)
//                        printLog("centroid=(%.0f, %.0f) scale=%.2f".format(centroid.x, centroid.y, scale))
//                    }
//                }

        ) {
            Image(
                painter = painterResource(R.drawable.koala),
                contentDescription = null,
                modifier = Modifier.clipToBounds().graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                    translationX = offset.x
                    translationY = offset.y
                }
            )
        }

        LazyColumn(state = scrollState, modifier = Modifier.weight(1f)) {
            items(logs) { event ->
                Text(event)
            }
        }
    }

    // Click Level1
//    Box(
//        modifier = Modifier.fillMaxSize()
//            .clickable { printLog("Click") }
//    )

    // Click Level2
//    Box(
//        modifier = Modifier.fillMaxSize()
//            .pointerInput(Unit) {
//                detectTapGestures { offset ->
//                    printLog("Click at $offset")
//                }
//            }
//    )


}

fun calclateOffset(scale: Float, centroid: Offset, size: IntSize): Offset {
    val y = (size.height / 2 - centroid.y) * (scale - 1f)
    val x = (size.width / 2 - centroid.x) * (scale - 1f)
    return Offset(x, y)
}