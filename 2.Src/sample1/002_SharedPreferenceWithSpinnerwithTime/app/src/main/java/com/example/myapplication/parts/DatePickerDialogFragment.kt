package com.example.myapplication.parts

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import java.util.*

class DatePickerDialogFragment : DialogFragment(), DatePickerDialog.OnDateSetListener {
    // Activity や Fragment に選択結果を渡すためのリスナー
    interface OnSelectedDateListener {
        fun selectedDate(year: Int, month: Int, date: Int)
    }

    private lateinit var listener: OnSelectedDateListener

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Todo デバッグで確認。リスナーへの設定がうまくいってない
        //      contextがMainActivity?になっているのが問題（MainFragmentではなく、MainActivityに紐づいてる。。。
        //      これをMainFragmentに紐づけることができれば問題なさそう）
        if (context is OnSelectedDateListener) {
            listener = context
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = Date().time
        val context = context // smart cast
        return when {
            context != null -> {
                DatePickerDialog(
                    context,
                    this, // ここでは DatePickerDialog の リスナーを渡す
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DATE))
            }
            else -> super.onCreateDialog(savedInstanceState)
        }
    }

    // DatePickerDialog から選択結果が渡される。
    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        listener.selectedDate(year, month, dayOfMonth)
    }

    companion object {
        private val TAG = DatePickerDialogFragment::class.java.simpleName
    }
}