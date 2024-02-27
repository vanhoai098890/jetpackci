package com.example.jetpackcomposeexample.customviewpath

import android.util.Log
import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInteropFilter

data class Point(
    val x: Float,
    val y: Float
)

data class PointPath(
    val points: List<Point> = listOf()
)

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CustomViewPath() {
    val totalPathState = remember {
        mutableStateListOf(PointPath())
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .pointerInteropFilter {
            when (it.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    Log.d("xxx", "ACTION_DOWN aaddddd")
                    val lastPath = totalPathState[totalPathState.size -1].points.toMutableList().apply { add(Point(it.x,it.y)) }.toList()
                    totalPathState[totalPathState.size -1] = PointPath(lastPath)
                }

                MotionEvent.ACTION_MOVE -> {
                    Log.d("xxx", "ACTION_MOVE aadd")
                    val lastPath = totalPathState[totalPathState.size -1].points.toMutableList().apply { add(Point(it.x,it.y)) }.toList()
                    totalPathState[totalPathState.size -1] = PointPath(lastPath)
                }

                MotionEvent.ACTION_UP -> {
                    Log.d("xxx", "ACTION_UP aadd")
                    Log.d("xxx", "ACTION_UP ${totalPathState.size}")
                    val lastPath = totalPathState[totalPathState.size -1].points.toMutableList().apply { add(Point(it.x,it.y)) }.toList()
                    totalPathState[totalPathState.size -1] = PointPath(lastPath)
                    totalPathState.add(PointPath())
                }

                else -> {
                }
            }
            return@pointerInteropFilter true
        }) {
        Canvas(modifier = Modifier.fillMaxSize(), onDraw = {
            Log.d("xxx", "${totalPathState.size}")
            totalPathState.forEach { pathState ->
                if (pathState.points.isNotEmpty()) {
                    val p = Path()
                    p.moveTo(pathState.points[0].x, pathState.points[0].y)
                    pathState.points.forEach {
                        p.lineTo(it.x, it.y)
                    }
                    drawPath(
                        p,
                        color = Color.White,
                        style = Stroke(width = 3f, join = StrokeJoin.Round)
                    )
                }
            }
        })
    }
}
