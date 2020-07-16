package com.gas.zhihu.dialog

import android.annotation.SuppressLint
import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import com.base.baseui.dialog.CommonDialog
import com.gas.zhihu.R

class CompassInputDialog {

    @SuppressLint("SetTextI18n")
    fun show(context: Context?, action: (num: Double) -> Unit = {}) {
        val view = LayoutInflater.from(context).inflate(R.layout.zhihu_dialog_compass_input, null)
        val etDegree = view.findViewById<EditText>(R.id.etDegree)
        val etMinute = view.findViewById<EditText>(R.id.etMinute)
        val etSecond = view.findViewById<EditText>(R.id.etSecond)
        val tvFormatNum = view.findViewById<TextView>(R.id.tvFormatNum)
        etDegree.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { num ->
                    var result = num
                    etMinute.text?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { minute ->
                        result += minute / 60
                    }
                    etSecond.text?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { second ->
                        result += second / 3600
                    }
                    tvFormatNum.text = String.format("%.4f", result)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        etMinute.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { num ->
                    var result = num / 60
                    etDegree.text?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { degree ->
                        result += degree
                    }
                    etSecond.text?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { second ->
                        result += second / 3600
                    }
                    tvFormatNum.text = String.format("%.4f", result)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        etSecond.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                s?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { num ->
                    var result = num / 3600
                    etDegree.text?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { degree ->
                        result += degree
                    }
                    etMinute.text?.toString()?.takeIf { it.isNotBlank() }?.toDouble()?.let { second ->
                        result += second / 60
                    }
                    tvFormatNum.text = String.format("%.4f", result)
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
        val dialog = CommonDialog.Builder()
                .setCancelable(true)
                .create(context, view)
        view.findViewById<View>(R.id.btn_sure).setOnClickListener {
            dialog.dismiss()
            tvFormatNum.text?.toString()?.takeIf { it.isNotBlank() }?.let {
                action.invoke(it.toDouble())
            }
        }
        dialog.show()
    }
}