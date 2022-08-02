package com.company.strengthtracker.presentation.test_screen.graph_utils

import android.app.ActionBar
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.strengthtracker.domain.util.GraphData
import kotlin.math.roundToInt







@Composable
fun ChartYAxis(graphData: GraphData, colors: ColorScheme, scale: Float, offset: Offset) {
    val density = LocalDensity.current
    val totalYMax = graphData.graphDataList.totalYMax.value + 20f
    val totalYMin = graphData.graphDataList.totalYMin.value - 20f
    val textPaint = remember { mutableStateOf(Paint()) }
    textPaint.value =
        Paint().apply {
            color = Color.BLACK
            textAlign = Paint.Align.RIGHT
//            textSize = density.run { 12.sp.toPx() }
            textSize = density.run { 12.dp.toPx() }
        }
    textPaint.value.isSubpixelText = true

    textPaint.value.isAntiAlias = true
    textPaint.value.isLinearText = true
    Canvas(modifier = Modifier
        .fillMaxSize()
        .graphicsLayer {
            scaleY = scale
            scaleX = scale
            translationY = -offset.y * scale
            translationX = -offset.x * scale
            transformOrigin = TransformOrigin(0f, 0f)
        }
    ) {
        val maxY = size.height * (scale - 1f) / scale
        val maxX = size.width * (scale - 1f) / scale

        textPaint.value.textSize /= scale
        drawCircle(
            color = colors.secondary,
            radius = 15f / scale,
            center = offset,
        )
        drawLine(
            color = colors.onSurfaceVariant,
            strokeWidth = (5.dp.toPx()) / scale,
            start = Offset(
                offset.x + (size.width - maxX),
                offset.y
            ),
            end = Offset(
                offset.x + (size.width - maxX),
                size.height
            )
        )

        val increment = (size.height / (totalYMax - totalYMin))
        var step = size.height
        var text = 0f
        for (i in 0..totalYMax.toInt()) {
            if(i % 10 == 0){
                drawContext.canvas.nativeCanvas.drawText(
                    "${text.toInt()}",
                    (( offset.x) + ((35.dp.toPx()) / scale)),
                    step - (increment / 10f) + (5.dp.toPx() / scale),
                    textPaint.value
                )
                drawLine(
                    start = Offset(offset.x + ((size.width - 6.dp.toPx()) / scale), step),
                    end = Offset(offset.x + ((size.width + 20.dp.toPx()) / scale), step),
                    color = androidx.compose.ui.graphics.Color.Black,
                    strokeWidth = 5f ,
                    alpha = 0.3f
                )

            }
            text += 1f

            step -= increment
        }
    }
}
