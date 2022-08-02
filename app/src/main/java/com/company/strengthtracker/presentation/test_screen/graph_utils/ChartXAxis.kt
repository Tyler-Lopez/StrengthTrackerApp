package com.company.strengthtracker.presentation.test_screen.graph_utils

import android.graphics.Color
import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.company.strengthtracker.domain.util.GraphData

@Composable
fun ChartXAxis(graphData: GraphData, colors: ColorScheme, scale: Float, pan: Offset, offset: Offset) {
    val density = LocalDensity.current
    val totalXMax = graphData.graphDataList.totalXMax.value
    val totalXMin = graphData.graphDataList.totalXMax.value
    val thisOffset = Offset(offset.x, (offset.y + (35f / scale)))

    val textPaint = remember { mutableStateOf(Paint())}
     textPaint.value =
            Paint().apply {
                color = Color.BLACK
                textAlign = Paint.Align.LEFT
                textSize = density.run { 12.sp.toPx() }
            }
    textPaint.value.isAntiAlias = true
    textPaint.value.isLinearText = true
    Canvas(modifier = Modifier
        .fillMaxSize().clipToBounds().background(color = colors.surface)
        .graphicsLayer {
            scaleX = scale
            scaleY = scale
            translationX = -offset.x * scale
            translationY = -offset.y * scale
            transformOrigin = TransformOrigin(0f, 0f)
        }
    ) {
        val maxY = size.height * (scale - 1f) / scale
        val maxX = size.width * (scale - 1f) / scale
        textPaint.value.textSize /= scale
        drawLine(
            color = colors.secondary,
            strokeWidth = (3.dp.toPx()) / scale,
            start = Offset(
                offset.x - 10f,
                offset.y
            ),
            end = Offset(
                offset.x + (size.width),
                offset.y
            )
        )
        val increment = (size.width) / 10f
        var step = 0f
        var text = 0f
        for (i in 0..size.width.toInt()) {
            if(text.toInt() > 0f) {
                drawContext.canvas.nativeCanvas.drawText(
                    "${text.toInt()}",
                    step - (3.dp.toPx() / scale),
                    thisOffset.y + (10.dp.toPx() / scale),
                    textPaint.value
                )
                drawLine(
                    start = Offset(step, offset.y + (6.dp.toPx() / scale)),
                    end = Offset(step, offset.y ),
                    color = androidx.compose.ui.graphics.Color.Black,
                    strokeWidth = 5f ,
                    alpha = 0.3f
                )
            }
            text += 1f
            step += increment

        }
    }

}
