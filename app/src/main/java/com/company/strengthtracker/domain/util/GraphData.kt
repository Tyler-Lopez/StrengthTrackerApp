package com.company.strengthtracker.domain.util

import com.company.strengthtracker.presentation.test_screen.graph_utils.CoordinateFormatter

data class GraphData(
    val graphDataList:GraphDataList,
    val padding: Float,
    val coordinateFormatter: CoordinateFormatter,
)

