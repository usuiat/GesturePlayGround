package com.example.gestureplayground

import androidx.compose.foundation.clickable
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "SampleList", modifier = modifier) {
        composable(route = "SampleList") {
            SampleList(
                samples = samples,
                onSampleSelect = { sample -> navController.navigate(sample) }
            )
        }
        for (sample in samples) {
            composable(route = sample.name) {
                sample.content()
            }
        }
    }
}

@Composable
private fun SampleList(
    samples: List<Sample>,
    onSampleSelect: (sample: String) -> Unit
) {
    LazyColumn() {
        items(samples) { sample ->
            ListItem(
                headlineContent = { Text(sample.name) },
                modifier = Modifier.clickable { onSampleSelect(sample.name) }
            )
        }
    }
}

private class Sample(
    val name: String,
    val content: @Composable () -> Unit
)

private val samples = listOf(
    Sample(
        name = "combinedClickableのサンプル",
        content = { ClickableSample() }
    ),
    Sample(
        name = "detectTapGesturesのサンプル",
        content = { DetectTapGesturesSample() }
    ),
    Sample(
        name = "自分でジェスチャーを実装する方法の例",
        content = { GestureSample() }
    ),
    Sample(
        name = "イベントが子供から親へ伝わるサンプル",
        content = { EventPropagationSample() }
    ),
    Sample(
        name = "イベントを消費するサンプル",
        content = { EventConsumptionSample() }
    ),
    Sample(
        name = "PointerInputにKeyを設定しない失敗例",
        content = { PointerInputKeySample() } // TODO
    ),
    Sample(
        name = "PointerInputにkeyを設定する例",
        content = { PointerInputKeySample() } // TODO
    ),
    Sample(
        name = "タップとドラッグの判定の失敗例",
        content = { WithoutTouchSlopSample() }
    ),
    Sample(
        name = "タッチスロップを利用してタップとドラッグを判定する例",
        content = { TouchSlopSample() }
    ),
    Sample(
        name = "複数の指のフリングの失敗例",
        content = { MultiTouchSample() }
    ),
    Sample(
        name = "複数の指のフリングの修正例",
        content = { MultiTouchSampleFixed() }
    ),
    Sample(
        name = "Zoomableの基本的な実装のサンプル",
        content = { ZoomableSample() }
    ),
)
