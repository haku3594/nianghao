package com.nianghaocalc

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import com.nianghaocalc.preference.SystemSetting
import kotlinx.android.synthetic.main.activity_main.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

class MainActivity : AppCompatActivity() {

    var dateData = DateData.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val c = Calendar.getInstance()
        dateData.inYear = c.get(Calendar.YEAR)
        dateData.inMonth = c.get(Calendar.MONTH)
        dateData.inDayOfMonth = c.get(Calendar.DAY_OF_MONTH)
        dateData.inHour = c.get(Calendar.HOUR_OF_DAY)
        dateData.inTime = c.get(Calendar.MINUTE)
        dateData.outHour = c.get(Calendar.HOUR_OF_DAY)
        dateData.outTime = c.get(Calendar.MINUTE)

        val inDateInfo = "${dateData.inYear}/${dateData.inMonth + 1}/${dateData.inDayOfMonth}"
        dateButton.text = inDateInfo
        dateButton.setOnClickListener {
            showDatePicker()
        }

        val inInfo = "${dateData.inHour}:${dateData.inTime}"
        inTimeButton.text = inInfo
        inTimeButton.setOnClickListener {
            showInTimePicker()
        }

        val outInfo = "${dateData.outHour}:${dateData.outTime}"
        outTimeButton.text = outInfo
        outTimeButton.setOnClickListener {
            showOutTimePicker()
        }

        calButton.setOnClickListener {
            calAccount()
        }

        createDefaultSystemSetting()
        setSystemList()
    }

    private fun showDatePicker() {

        val datePickerDialog = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
                dateData.inYear = year
                dateData.inMonth = month
                dateData.inDayOfMonth = dayOfMonth
                val dateInfo = "${dateData.inYear}/${dateData.inMonth + 1}/${dateData.inDayOfMonth}"
                dateButton.text =dateInfo
            },
            dateData.inYear,
            dateData.inMonth,
            dateData.inDayOfMonth
        )
        datePickerDialog.show()
    }

    private fun showInTimePicker() {

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                dateData.inHour = hour
                dateData.inTime = minute
                val inInfo = "${dateData.inHour}:${dateData.inTime}"
                inTimeButton.text = inInfo
            },
            dateData.inHour,
            dateData.inTime,
            true
        )
        timePickerDialog.show()
    }

    private fun showOutTimePicker() {

        val timePickerDialog = TimePickerDialog(
            this,
            TimePickerDialog.OnTimeSetListener { view, hour, minute ->
                dateData.outHour = hour
                dateData.outTime = minute
                val outInfo = "${dateData.outHour}:${dateData.outTime}"
                outTimeButton.text = outInfo
            },
            dateData.outHour,
            dateData.outTime,
            true
        )
        timePickerDialog.show()
    }

    /**
     * システム設定のデフォルト項目作成
     */
    private fun createDefaultSystemSetting() {

        val systemPreference = getSharedPreferences("SystemSetting", MODE_PRIVATE)
        val gson = Gson()

        /**
         * キー：0010
         * データ：　名称:男性チャージ
         *         基本料金：400（円）
         *         基本時間：30（分）
         *         延長料金：200（円）
         *         延長時間：15（分）
         *         オーダ単位：30(分)
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        var jsonString = gson.toJson(SystemSetting("男性チャージ", 400, 30, 200, 15, 30, false))
        systemPreference.edit().putString("0010", jsonString).apply()

        /**
         * キー：0020
         * データ：　名称:男性飲み放題
         *         基本料金：2400（円）
         *         基本時間：60（分）
         *         延長料金：1200（円）
         *         延長時間：30（分）
         *         オーダ単位：0(分)
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(SystemSetting("男性飲み放題", 2400, 60, 1200, 30, 0, false))
        systemPreference.edit().putString("0020", jsonString).apply()

        /**
         * キー：0030
         * データ：　名称:女性チャージ
         *         基本料金：200（円）
         *         基本時間：30（分）
         *         延長料金：100（円）
         *         延長時間：15（分）
         *         オーダ単位：30(分)
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(SystemSetting("女性チャージ", 200, 30, 100, 15, 30, false))
        systemPreference.edit().putString("0030", jsonString).apply()

        /**
         * キー：0040
         * データ：　名称:女性飲み放題
         *         基本料金：2000（円）
         *         基本時間：60（分）
         *         延長料金：1000（円）
         *         延長時間：30（分）
         *         オーダ単位：0(分)
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(SystemSetting("女性飲み放題", 2000, 60, 1000, 30, 0, false))
        systemPreference.edit().putString("0040", jsonString).apply()

    }

    private fun setSystemList() {

        val prefs = getSharedPreferences("SystemSetting", MODE_PRIVATE)
        val keys: Map<String, *> = prefs.all
        val systemList: ArrayList<SystemSetting> = ArrayList()

        keys.toSortedMap().forEach { (k, v) ->
            val jsonString = v.toString()
            val gson = Gson()
            val curSys = gson.fromJson(jsonString, SystemSetting::class.java)

            if (!curSys.delFlg) {
                systemList.add(
                    SystemSetting(
                        curSys.name,
                        curSys.basicValue,
                        curSys.basicTime,
                        curSys.exValue,
                        curSys.exTime,
                        curSys.ordUnit,
                        curSys.delFlg
                    )
                )
            }
        }

        // アダプターにアイテム配列を設定
        val customAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_dropdown_item,
            systemList
        )
        spinner1.adapter = customAdapter
        spinner1.setSelection(0)
    }

    private fun calAccount() {

        val curSys = spinner1.selectedItem as SystemSetting
        // 日跨ぎ
        val nextDay = if (nextDaycheck.isChecked) 1 else 0

        val inDate = LocalDateTime.of(
            dateData.inYear,
            dateData.inMonth,
            dateData.inDayOfMonth,
            dateData.inHour,
            dateData.inTime
        )
        val outDate = LocalDateTime.of(
            dateData.inYear,
            dateData.inMonth,
            dateData.inDayOfMonth + nextDay,
            dateData.outHour,
            dateData.outTime
        )

        // 滞在時間(分)
        val totalStay = ChronoUnit.MINUTES.between(inDate, outDate)
        val stayHour = ChronoUnit.HOURS.between(inDate, outDate)
        val stayMinutes = totalStay - (stayHour * 60)

        //　システム金額
        var sysFee = curSys.basicValue
        if (totalStay > curSys.basicTime) {
            // 延長あり
            sysFee = sysFee + Math.ceil(((totalStay - curSys.basicTime).toDouble() / curSys.exTime))
                .toInt() * curSys.exValue
        }

        // 必要オーダー数
        val ordNumber =
            if (curSys.ordUnit != 0) Math.ceil((totalStay.toDouble() / curSys.ordUnit))
                .toInt() else 0

        val sysInfo = "${curSys.name} $stayHour 時間 $stayMinutes 分"
        val feeInfo = "${sysFee}円/必要オーダー数$ordNumber"
        calInfo1View.text = sysInfo
        calInfo2View.text = feeInfo
    }

}