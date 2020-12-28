package com.basalam.basalamproduct.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.basalam.basalamproduct.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat

object StringUtil {
    @BindingAdapter(value = ["setImageUrl"])
    @JvmStatic
    fun ImageView.bindImageUrl(url: String?) {
        if (url != null && url.isNotBlank()) {
            Picasso.get()
                .load(url)
                .placeholder(R.drawable.ph)
                .into(this)
        }
    }

    @BindingAdapter("englishToPersian")
    @JvmStatic
    fun bindCurrency(view: TextView, name: String) {
        view.text = EnglishTopersian(name)
    }

    private val formatter: NumberFormat = DecimalFormat("###,###,###,###")

    @BindingAdapter("moneyFormat")
    @JvmStatic
    fun bindMoneyFormat(view: TextView, name: Int) {
        val price: String = EnglishTopersian(formatter.format(name.toDouble() / 10)) + " تومان"
        view.text = price
    }

    @BindingAdapter("weightFormat")
    @JvmStatic
    fun bindWeight(view: TextView, name: Int) {
        val weight: String = EnglishTopersian(name.toString()) + " گرم"
        view.text = weight
    }

    @BindingAdapter("rateFormat")
    @JvmStatic
    fun bindRate(view: TextView, name: Double) {
        val rate: String = EnglishTopersian(name.toString())
        view.text = rate
    }

    @BindingAdapter("vendorFormat")
    @JvmStatic
    fun bindVendor(view: TextView, name: String) {
        val vendor: String = "غرفه: " + EnglishTopersian(name)
        view.text = vendor
    }

    @BindingAdapter("countFormat")
    @JvmStatic
    fun bindCount(view: TextView, name: Int) {
        val count: String = "(" + EnglishTopersian(name.toString()) + ")"
        view.text = count
    }

    fun EnglishTopersian(persianStr: String): String {
        var result = ""
        var en = '0'
        for (ch in persianStr) {
            en = ch
            when (ch) {
                '0' -> en = '۰'
                '1' -> en = '۱'
                '2' -> en = '۲'
                '3' -> en = '۳'
                '4' -> en = '۴'
                '5' -> en = '۵'
                '6' -> en = '۶'
                '7' -> en = '۷'
                '8' -> en = '۸'
                '9' -> en = '۹'
            }
            result = "${result}$en"
        }
        return result
    }
}