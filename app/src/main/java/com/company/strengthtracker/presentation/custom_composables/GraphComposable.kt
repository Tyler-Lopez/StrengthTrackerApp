package com.company.strengthtracker.presentation.custom_composables

import android.content.res.Resources
import androidx.annotation.RequiresPermission
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.company.strengthtracker.ui.theme.*

//@Composable
//fun GraphMainView() {
//    Column(modifier = Modifier.fillMaxSize(0.9f)) {
//        Canvas(modifier = Modifier.fillMaxSize()){
//            val canvasHeight = size.height
//            val canvasWidth = size.width
//            drawLine(
//                start = Offset(x = canvasWidth, y = 0f),
//                end = Offset(x = 0f, y = canvasHeight),
//            color = Color
//             )
//        }
//    }
//}
//
//
//enum class ColorState{
//    DARK, LIGHT;
//
//    val surfaceColor:Color
//    @Composable
//    @ReadOnlyComposable
//    get() = when(this) {
//        DARK -> MaterialTheme.colorScheme.onSurface
//        LIGHT -> MaterialTheme.colorScheme.onSurface
//
//    }
//}
//@Preview
//@Composable
//fun GraphMainViewPreview() {
//    GraphMainView()
//}