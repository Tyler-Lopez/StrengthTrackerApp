package com.company.strengthtracker.domain.util

import com.company.strengthtracker.presentation.test_screen.graph_utils.CoordinateFormatter

class GraphData(
    height: Float,
    width: Float,
    xListInitial: MutableList<Float>,
    xListCurrent: MutableList<Float>,
    yListInitial: MutableList<Float>,
    yListCurrent: MutableList<Float>,
    totalYMaxInit: Float,
    totalYMinInit: Float,
    totalXMax: Float,
    totalXMin: Float,
    padding: Float,
    coordinateFormatter: CoordinateFormatter,
) {
  val height = height
  val width = width
  val xListInitial = xListInitial
  val xListCurrent = xListCurrent
  val yListInitial = yListInitial
  val yListCurrent = yListCurrent
  val totalYMaxInit = totalYMaxInit
  val totalYMinInit = totalYMinInit
  val totalXMax = totalXMax
  val totalXMin = totalXMin
  val padding = padding
  val coordinateFormatter = coordinateFormatter
}

