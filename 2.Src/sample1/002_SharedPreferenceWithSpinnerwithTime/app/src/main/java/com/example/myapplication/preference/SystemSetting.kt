package com.example.myapplication.preference

data class SystemSetting(
    val name: String,
    val basicValue: Int,
    val basicTime: Int,
    val exValue: Int,
    val exTime: Int,
    val ordUnit: Int,
    val delFlg: Boolean
) {
}