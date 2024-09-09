package com.example.gestureplayground

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.PointerEventType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.tooling.preview.Preview
import com.example.gestureplayground.ui.theme.GesturePlayGroundTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GesturePlayGroundTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)) {
//                        ClickableSample()
//                        DetectTapGesturesSample()
//                        GestureSample()
//                      EventPropagationSample()
//                      EventConsumptionSample()
//                      PointerInputKeySample()
//                      WithoutTouchSlopSample()
//                      TouchSlopSample()
//                      MultiTouchSample()
//                        MultiTouchSampleFixed()
                      ZoomableSample()
                    }
                }
            }
        }
    }
}


//class PointerInputChange {
//    // 指の位置
//    val position: Offset
//    val previousPosition: Offset
//    // イベント発生時刻
//    val uptimeMillis: Long
//    val previousUptimeMillis: Long
//    // 指が画面に触れているかどうか
//    val pressed: Boolean
//    val previousPressed: Boolean
//    // 指を区別するためのID
//    val id: PointerId
//}

//interface PointerInputChange {
//  fun changedToDown(): Boolean
//  fun changedToUp(): Boolean
//  fun positionChanged(): Boolean
//  fun positionChange(): Offset
//}
//
//class PointerInputChangeImpl() : PointerInputChange {
//  override fun changedToDown(): Boolean {
//    TODO("Not yet implemented")
//  }
//
//  override fun changedToUp(): Boolean {
//    TODO("Not yet implemented")
//  }
//
//  override fun positionChanged(): Boolean {
//    TODO("Not yet implemented")
//  }
//
//  override fun positionChange(): Offset {
//    TODO("Not yet implemented")
//  }
//
//}
//
//interface PointerEvent {
//  val changes: List<PointerInputChange>
//  val type: PointerEventType
//
//  fun calculateCentroid(): Offset
//  fun calculateCentroidSize(): Float
//  fun calculatePan(): Offset
//  fun calculateRotation(): Float
//  fun calculateZoom(): Float
//}
//
//
//class PointerEventImpl() : PointerEvent {
//  override val changes: List<PointerInputChange>
//    get() = TODO("Not yet implemented")
//  override val type: PointerEventType
//    get() = TODO("Not yet implemented")
//
//  override fun calculateCentroid(): Offset {
//    TODO("Not yet implemented")
//  }
//
//  override fun calculateCentroidSize(): Float {
//    TODO("Not yet implemented")
//  }
//
//  override fun calculatePan(): Offset {
//    TODO("Not yet implemented")
//  }
//
//  override fun calculateRotation(): Float {
//    TODO("Not yet implemented")
//  }
//
//  override fun calculateZoom(): Float {
//    TODO("Not yet implemented")
//  }
//
//}