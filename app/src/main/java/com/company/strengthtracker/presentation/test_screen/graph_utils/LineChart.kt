package com.company.strengthtracker.presentation.test_screen.graph_utils

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTransformGestures
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import com.company.strengthtracker.domain.util.GraphData

@Composable
fun LineChart(
    graphData: GraphData,
    colors: ColorScheme,
    scale: Float,
    offset: Offset,
    textPaint: Paint,
    lineSize:Float,
    onScaleChanged: (Float) -> Unit,
    onOffsetChanged: (Offset) -> Unit,
    gestureListener: (centroid: Offset, pan: Offset, zoom: Float) -> Unit
) {
    val totalYMax = graphData.graphDataList.totalYMax.value + 20f
    val totalYMin = graphData.graphDataList.totalYMin.value - 20f
    val totalXMax = graphData.graphDataList.totalXMax.value
    val totalXMin = graphData.graphDataList.totalYMin.value
    var offset by remember { mutableStateOf(offset) }
    var scale by remember { mutableStateOf(scale) }
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(color = colors.surface)
            .pointerInput(Unit) {
                detectTransformGestures(
                    panZoomLock = false,
                    onGesture = { centroid, pan, zoom, _ ->
                        val oldScale = scale
                        val newScale = (scale * zoom).coerceIn(1f, 2f)
                        val newOffset = (offset + centroid / oldScale) - (centroid / newScale + pan / oldScale)
                        val maxX = size.width * (newScale - 1f) / newScale
                        val maxY = size.height * (newScale - 1f) / newScale
                        offset = Offset(newOffset.x.coerceIn(0f, maxX), newOffset.y.coerceIn(0f, maxY))
                        scale = newScale
                        onScaleChanged(scale)
                        onOffsetChanged(offset)
                        gestureListener(centroid, pan, zoom)
                    }
                )
            }
            .graphicsLayer {
                translationX = (-offset.x * scale)
                translationY = (-offset.y * scale)
                scaleX = scale
                scaleY = scale
                transformOrigin = TransformOrigin(0f, 0f)
            }
    ) {

        val incrementY = (size.height / (totalYMax - totalYMin)) * 10f
        var stepY = size.height
        for (i in 0..size.height.toInt()) {
            drawLine(
                start = Offset(size.width, stepY),
                end = Offset(0f, stepY),
                color = Color.Black,
                strokeWidth = 7f / scale,
                alpha = 0.3f
            )
            stepY -= incrementY

        }
/* BOUND LINES FOR VISUAL TESTING
            drawCircle(
                color = colors.tertiary,
                radius = 15f,
                center = offset
            )
            drawLine(
               start = borderOffset,
                end = Offset(0f, borderOffset.y),
                color = colors.tertiary,
                strokeWidth = 5f
            )
            drawLine(
                start = borderOffset,
                end = Offset(borderOffset.x, 0f),
                color = colors.tertiary,
                strokeWidth = 5f
            )
            drawLine(
                start = Offset(0f, 0f),
                end = Offset(0f, borderOffset.y),
                color = colors.tertiary,
                strokeWidth = 5f
            )
            drawLine(
                start = Offset(0f,0f),
                end = Offset(borderOffset.x, 0f),
                color = colors.tertiary,
                strokeWidth = 5f
            )

*/
        val width = size.width.toFloat()
        val height = size.height.toFloat()
        graphData.graphDataList.coordinates.forEach { dataSet ->
            val temp = graphData.coordinateFormatter.normalizeCoordinates(
                listX = dataSet.coordinateArray[0],
                listY = dataSet.coordinateArray[1],
                yMax = totalYMax,
                yMin = totalYMin,
                xMax = dataSet.xMax,
                xMin = dataSet.xMin,
                height = size.height,
                width = size.width,
                padding = graphData.padding
            )

            var i = 0
            try {
                while (i < temp.size) {
                        drawLine(
                            color = colors.onSurface,
                            start = temp[i],
                            end = temp[i + 1],
                            strokeWidth = 10f
                        )
                    if(scale > 1.3f )
                        drawLine(
                            color = colors.onSurface,
                            start = temp[i],
                            end = temp[i + 1],
                            strokeWidth = 10f/ scale
                        )

                    drawCircle(color = colors.tertiaryContainer, radius = 10f / scale , center = temp[i])
                    i += 1
                }

            } catch (e:IndexOutOfBoundsException){

            }



        }
/*
            var current =
                graphData.coordinateFormatter.getCoordList(
                    listX = graphData.xListCurrent,
                    listY = graphData.yListCurrent,
                    yMax = totalYMax,
                    yMin = totalYMin,
                    xMax = graphData.xListCurrent.maxOrNull() ?: Float.MIN_VALUE,
                    xMin = graphData.xListCurrent.minOrNull() ?: Float.MIN_VALUE,
                    height = height,
                    width = width,
                    padding = graphData.padding
                )
            var initial =
                graphData.coordinateFormatter.getCoordList(
                    listX = graphData.xListInitial,
                    listY = graphData.yListInitial,
                    yMax = totalYMax,
                    yMin = totalYMin,
                    xMax = graphData.xListInitial.maxOrNull() ?: Float.MIN_VALUE,
                    xMin = graphData.xListInitial.minOrNull() ?: Float.MIN_VALUE,
                    height = height,
                    width = width,
                    padding = graphData.padding
                )

            var stepSize = 0f
            val increment = height / (totalYMax - totalYMin)
            val x1 = width / (totalXMax)
            var text = totalYMax
            for (i in totalYMin.toInt()..(totalYMax.toInt())) {
                if (i % 10 == 0 && text > totalYMin) {
                    drawContext.canvas.nativeCanvas.drawText(
                        "${text}",
                        (0.5f * (graphData.padding - totalXMin)),
                        (stepSize + (0.3f * textPaint.textSize)),
                        textPaint
                    )
                    drawLine(
                        color = colors.onSurface,
                        start = Offset(x = (graphData.padding - totalXMin) - 8f, y = stepSize),
                        end = Offset(x = (graphData.padding - totalXMin) + 8f, y = stepSize),
                        strokeWidth = 5f
                    )
                    drawLine(
                        color = colors.onSurface,
                        start = Offset((graphData.padding - totalXMin) + 8f, stepSize),
                        end = Offset(width + graphData.padding, stepSize),
                        strokeWidth = 2f,
                        alpha = 0.6f,
                        pathEffect =
                        PathEffect.dashPathEffect(
                            floatArrayOf(x1, x1, x1, x1),
                        )
                    )
                }
                text -= 1f
                stepSize += increment
            }

            for (i in current.indices) {
                if ((i + 1) < current.size) {
                    drawLine(
                        color = colors.onSurface,
                        start = current[i],
                        end = current[i + 1],
                        strokeWidth = 10f
                    )
                }
            }
            for (i in current.indices) {
                drawCircle(color = colors.onSurfaceVariant, radius = 10f, center = current[i])
            }

            for (i in initial.indices) {
                if ((i + 1) < current.size) {
                    drawLine(
                        color = colors.error,
                        start = initial[i],
                        end = initial[i + 1],
                        strokeWidth = 10f
                    )
                }
            }
            for (i in initial.indices) {
                drawCircle(color = colors.onSurfaceVariant, radius = 10f, center = initial[i])
            }
*/
    }

}
