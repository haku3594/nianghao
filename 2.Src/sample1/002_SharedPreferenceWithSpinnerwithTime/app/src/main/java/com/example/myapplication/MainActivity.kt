package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myapplication.preference.ItemSetting
import com.google.gson.Gson
import com.example.myapplication.preference.SystemSetting

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //　初期化処理
        initApp()

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }

    }

    //region 初回起動時の処理
    /**
     * 初回起動時の処理
     */
    private fun initApp() {
        // 初回起動判定

        // AppInfo.xmlが存在しない場合、初回起動と判定し、以降の初回起動処理を実行する
        val appInfoPreference = getSharedPreferences("AppInfo", MODE_PRIVATE)
        if (appInfoPreference.getString("installData", null) == null) {

            // インストール情報書き込み
            // todo インストール情報は初回起動かどうかの判定でしか現在使用しない。とりあえず今はInstall日入れとく。
            appInfoPreference.edit().putString("installData", "20211009").apply()

            // システム設定のデフォルト情報を作成
            createDefaultSystemSetting()

            // アイテム設定のデフォルト情報を作成
            createDefaultItemSetting()
        }
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

    /**
     * アイテム設定のデフォルト項目作成
     */
    private fun createDefaultItemSetting() {
        /**
         * アイテム設定
         */
        val itemPreference = getSharedPreferences("ItemSetting", MODE_PRIVATE)
        val gson = Gson()

        /**
         * キー：0010
         * データ：　名称:オリカク
         *         料金：1000（円）
         *         オーダーカウント：1（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        var jsonString = gson.toJson(ItemSetting("オリカク", 1000, 1, 0, false))
        itemPreference.edit().putString("0010", jsonString).apply()

        /**
         * キー：0020
         * データ：　名称:ソフトドリンク
         *         料金：600（円）
         *         オーダーカウント：1（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(ItemSetting("ソフトドリンク", 600, 1, 0, false))
        itemPreference.edit().putString("0020", jsonString).apply()

        /**
         * キー：0030
         * データ：　名称:アルコール
         *         料金：700（円）
         *         オーダーカウント：1（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(ItemSetting("アルコール", 700, 1, 0, false))
        itemPreference.edit().putString("0030", jsonString).apply()

        /**
         * キー：0040
         * データ：　名称:ショット
         *         料金：800（円）
         *         オーダーカウント：1（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(ItemSetting("ショット", 800, 1, 0, false))
        itemPreference.edit().putString("0040", jsonString).apply()

        /**
         * キー：0050
         * データ：　名称:乾杯セット
         *         料金：1300（円）
         *         オーダーカウント：2（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(ItemSetting("乾杯セット", 1300, 2, 0, false))
        itemPreference.edit().putString("0050", jsonString).apply()

        /**
         * キー：0060
         * データ：　名称:至福セット
         *         料金：2000（円）
         *         オーダーカウント：3（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(ItemSetting("至福セット", 2000, 3, 0, false))
        itemPreference.edit().putString("0060", jsonString).apply()

        /**
         * キー：0070
         * データ：　名称:初めましてセット
         *         料金：2500（円）
         *         オーダーカウント：2（個）
         *         チャージ減産：0（分）
         *         削除ﾌﾗｸﾞ：false（削除でない）
         * */
        jsonString = gson.toJson(ItemSetting("初めましてセット", 2500, 2, 0, false))
        itemPreference.edit().putString("0070", jsonString).apply()
    }
    //endregion

}

