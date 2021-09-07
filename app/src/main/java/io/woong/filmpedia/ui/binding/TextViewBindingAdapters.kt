package io.woong.filmpedia.ui.binding

import androidx.annotation.PluralsRes
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import io.woong.filmpedia.R
import io.woong.filmpedia.data.people.Person
import io.woong.filmpedia.util.DateUtil
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
    @BindingAdapter(value = ["plurals_resource", "plurals_count", "plurals_list"], requireAll = false)
    fun AppCompatTextView.bindPlurals(@PluralsRes res: Int?, count: Int?, list: List<Any>?) {
        val c = list?.size ?: 2

        if (res != null) {
            val plurals = resources.getQuantityText(res, c)
            this.text = plurals
        }
    }

    @JvmStatic
    @BindingAdapter("runtime")
    fun AppCompatTextView.bindRuntime(runtime: Int?) {
        if (runtime != null) {
            val hours = runtime / 60
            val minutes = runtime % 60

            val runtimeString = resources.getString(R.string.movie_runtime, hours, minutes)

            this.text = runtimeString
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

    @JvmStatic
    @BindingAdapter("gender")
    fun AppCompatTextView.bindGender(gender: Person.Gender?) {
        if (gender != null) {
            val genderString: String = when (gender) {
                Person.Gender.UNSPECIFIED -> resources.getString(R.string.person_gender_unspecified)
                Person.Gender.MALE -> resources.getString(R.string.person_gender_male)
                Person.Gender.FEMALE -> resources.getString(R.string.person_gender_female)
                Person.Gender.NON_BINARY -> resources.getString(R.string.person_gender_non_binary)
            }

            this.text = genderString
        }
    }

    @JvmStatic
    @BindingAdapter(value = ["birthday", "deathday"], requireAll = false)
    fun AppCompatTextView.bindLife(birthday: String?, deathday: String?) {
        if (birthday != null) {
            val age = DateUtil.getAge(birthday, deathday, -1)

            val builder = StringBuilder(birthday)

            if (deathday != null) {
                builder.append("~")
                builder.append(" ")
                builder.append("$deathday")
            }

            builder.append(" ")
            builder.append("($age)")

            this.text = builder.toString()
        }
    }
}
