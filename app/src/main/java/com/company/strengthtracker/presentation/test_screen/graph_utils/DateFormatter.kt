package com.company.strengthtracker.presentation.test_screen.graph_utils;

import java.time.Instant
import java.time.ZoneId

public class DateFormatter {

    fun dateFormatter(date: Long): Long {
        return Instant.ofEpochMilli(date)
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
            .toString()
            .replace("-", "")
            .replaceRange(0, 1, "")
            .toLong()
    }

    fun dateBuilder(date: Long): String {
        var sd = date.toString()
        sd = "20${date}"
        if (sd.length == 8)
            sd = sd.substring(0..3) + "-" + sd.substring(4..5) + "-" + sd.substring(6..7)
        return sd
    }

}

