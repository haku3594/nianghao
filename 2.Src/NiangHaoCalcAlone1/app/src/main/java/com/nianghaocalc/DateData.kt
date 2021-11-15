package com.nianghaocalc

import java.util.*

class DateData private constructor() {

    var inYear: Int = 0
    var inMonth: Int = 0
    var inDayOfMonth: Int = 0
    var inHour: Int = 0
    var inTime: Int = 0
    var outHour: Int = 0
    var outTime: Int = 0
    // 日跨ぎ対応

    companion object {

        private var instance: DateData? = null

        fun getInstance(): DateData {
            if (instance == null)
                instance = DateData()

            return instance!!
        }
    }
}