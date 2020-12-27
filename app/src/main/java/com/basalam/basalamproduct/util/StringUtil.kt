package com.basalam.basalamproduct.util

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.basalam.basalamproduct.R
import com.squareup.picasso.Picasso
import java.text.DecimalFormat
import java.text.NumberFormat


object StringUtil {


    //set image
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


    //change english number to persian
    @BindingAdapter("englishToPersian")
    @JvmStatic
    fun bindCurrency(view: TextView, name: String) {
        view.text = EnglishTopersian(name)
    }


    // here we add , ( comma ) for money format
    private val formatter: NumberFormat = DecimalFormat("###,###,###,###")

    //change price to money format , Division to 10 for TOMAN and add "تومان" at the last
    @BindingAdapter("moneyFormat")
    @JvmStatic
    fun bindMoneyFormat(view: TextView, name: Int) {
        val price: String = EnglishTopersian(formatter.format(name.toDouble() / 10)) + " تومان"
        view.text = price
    }


    //add "گرم" at the last
    @BindingAdapter("weightFormat")
    @JvmStatic
    fun bindWeight(view: TextView, name: Int) {
        val weight: String = EnglishTopersian(name.toString()) + " گرم"
        view.text = weight
    }


    //change english number to persian number (better to change font that support persian number)
    @BindingAdapter("rateFormat")
    @JvmStatic
    fun bindRate(view: TextView, name: Double) {
        val rate: String = EnglishTopersian(name.toString())
        view.text = rate
    }

    //add "غرفه" at the first
    @BindingAdapter("vendorFormat")
    @JvmStatic
    fun bindVendor(view: TextView, name: String) {
        val vendor: String = "غرفه: " + EnglishTopersian(name)
        view.text = vendor
    }


    //add "(" and ")" for count
    @BindingAdapter("countFormat")
    @JvmStatic
    fun bindCount(view: TextView, name: Int) {
        val count: String = "(" + EnglishTopersian(name.toString()) + ")"
        view.text = count
    }


    //fun to change english number to persian number (better to change font that support persian number)
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