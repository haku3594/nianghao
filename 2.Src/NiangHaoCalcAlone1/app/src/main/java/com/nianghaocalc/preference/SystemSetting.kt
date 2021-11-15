package com.nianghaocalc.preference

import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit

data class SystemSetting(
    val name: String,
    val basicValue: Int,
    val basicTime: Int,
    val exValue: Int,
    val exTime: Int,
    val ordUnit: Int,
    val delFlg: Boolean
){

    override fun toString(): String {
        return name
    }
}