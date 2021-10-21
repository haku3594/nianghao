package com.example.myapplication.preference

data class ItemSetting(
    val name: String,
    val value: Int,
    val ordCount: Int,
    val timeSubtract: Int,
    val delFlg: Boolean
) {
}