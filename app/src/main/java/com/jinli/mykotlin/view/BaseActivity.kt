package com.jinli.mykotlin.view

import android.graphics.Color
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.jinli.mykotlin.R

/**
 * Created by Jin on 10/15/2018
 */
abstract class BaseActivity : AppCompatActivity() {
    protected var mSnackBar: Snackbar? = null

    //snackBar 弹出框
    fun snack(view: View, text: String, actionTextId: Int = 0, onClickListener: View.OnClickListener? = null) {
        val ssb = SpannableStringBuilder()
                .append(text)
        ssb.setSpan(
                ForegroundColorSpan(Color.WHITE),
                0,
                text.length,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        mSnackBar = Snackbar.make(view, ssb, Snackbar.LENGTH_INDEFINITE)
        mSnackBar?.view?.setBackgroundResource(R.color.common_red)
        if (actionTextId != 0) {
            mSnackBar?.setAction(actionTextId, onClickListener)
            mSnackBar?.setActionTextColor(Color.WHITE)
        }
        mSnackBar?.show()
    }
}