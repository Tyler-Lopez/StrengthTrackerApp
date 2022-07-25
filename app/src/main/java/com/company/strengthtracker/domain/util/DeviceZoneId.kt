package com.company.strengthtracker.domain.util

import java.time.ZoneId
import java.time.ZoneOffset

enum class DeviceZoneId (zoneId:ZoneId){
    DEFAULT(ZoneId.systemDefault())
}