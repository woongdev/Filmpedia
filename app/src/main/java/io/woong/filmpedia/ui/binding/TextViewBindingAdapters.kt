package io.woong.filmpedia.ui.binding

import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import java.lang.StringBuilder
import java.text.DecimalFormat

object TextViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("multiline_text")
    fun AppCompatTextView.bindMultilineText(textList: List<String>?) {
        if (textList != null) {
            val builder = StringBuilder()

            for (index in textList.indices) {
                if (index > 0) {
                    builder.append("\n${textList[index]}")
                } else {
                    builder.append(textList[index])
                }
            }

            this.text = builder.toString()
        }
    }

    @JvmStatic
    @BindingAdapter("money")
    fun bindMoney(view: AppCompatTextView, money: Long?) {
        if (money != null) {
            if (money > 0) {
                val pattern = DecimalFormat("$ #,###")
                view.text = pattern.format(money)
            } else {
                view.text = "-"
            }
        }
    }
}
