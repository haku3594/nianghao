package com.example.myapplication

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.databinding.FragmentMainBinding
import com.example.myapplication.preference.SystemSetting
import com.google.gson.Gson
import com.example.myapplication.parts.DatePickerDialogFragment
import com.example.myapplication.parts.TimePickerDialogFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : Fragment(),
    DatePickerDialogFragment.OnSelectedDateListener,
    TimePickerDialogFragment.OnSelectedTimeListener
{
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // バインド設定　layout内のコントロールアクセスが簡単になります
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // spinner設定
        setSystemList()
        setItemList()

        // button設定
        setClearButton()
    }

    override fun selectedDate(year: Int, month: Int, date: Int) {
        txtDate.text= "${year}年${month}月${date}日"
    }

    override fun selectedTime(hour: Int, minute: Int) {
        txtTime.text = "${hour}時${minute}分"
    }

    private fun setClearButton() {
        // クリアボタン
        btnClear.setOnClickListener { clearAll() }

        // 日付ボタン
        btnDate.setOnClickListener {
            showDatePickerDialog()
        }

        // 時間ボタン
        btnTime.setOnClickListener {
            showTimePickerDialog()
        }

    }

    private fun showDatePickerDialog() {
        val datePickerDialogFragment = DatePickerDialogFragment()
        datePickerDialogFragment.show(childFragmentManager, null)
    }

    private fun showTimePickerDialog() {
        val timePickerDialogFragment = TimePickerDialogFragment()
        timePickerDialogFragment.show(childFragmentManager, null)
    }

    /**
     * 商品リストの作成
     */
    private fun setItemList() {

        val prefs = requireActivity().getSharedPreferences("ItemSetting", MODE_PRIVATE)
        val keys: Map<String, *> = prefs.all
        val itemList :ArrayList<String> = ArrayList()

        keys.toSortedMap().forEach { (k,v) ->
            val jsonString = v.toString()
            val gson = Gson()
            val curSystemSetting = gson.fromJson(jsonString, SystemSetting::class.java)

            if (!curSystemSetting.delFlg){
                itemList.add(curSystemSetting.name)
            }
        }

        // アダプターにアイテム配列を設定
        val customAdapter = ArrayAdapter(requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            itemList
        )
        spinner2.adapter = customAdapter
    }

    private fun setSystemList() {

        val prefs = requireActivity().getSharedPreferences("SystemSetting", MODE_PRIVATE)
        val keys: Map<String, *> = prefs.all
        val systemList :ArrayList<String> = ArrayList()

        keys.toSortedMap().forEach { (k,v) ->
            val jsonString = v.toString()
            val gson = Gson()
            val curSystemSetting = gson.fromJson(jsonString, SystemSetting::class.java)

            if (!curSystemSetting.delFlg){
                systemList.add(curSystemSetting.name)
            }
        }

        // アダプターにアイテム配列を設定
        val customAdapter = ArrayAdapter(requireActivity(),
            android.R.layout.simple_spinner_dropdown_item,
            systemList
        )
        spinner1.adapter = customAdapter
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        // TODO viewモデルによる情報保持をあとで実装
    }

    private fun clearAll(){
        // todo 画面クリア処理を後で実装。今はトーストでボタン押下しました　がでるたけ
        Toast.makeText(context, "クリアボタンを押しました", Toast.LENGTH_SHORT).show()
    }
}