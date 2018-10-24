package com.jinli.mykotlin.view.fragment

import android.graphics.Color
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jinli.mykotlin.R
import com.jinli.mykotlin.view.BaseActivity

/**
 * Created by Jin on 10/24/2018
 */
abstract class BaseFragment : Fragment() {

    @JvmField
    protected var rootView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (activity !is BaseActivity) {
            throw ClassCastException("Hosting Activity must extend BaseActivity")
        }
        if (savedInstanceState != null) {
            val isSupportHidden: Boolean = savedInstanceState.getBoolean("STATE_SAVE_IS_HIDDEN", false)
            val ft = fragmentManager?.beginTransaction()
            if (isSupportHidden) {
                ft?.hide(this)
            } else {
                ft?.show(this)
            }
            ft?.commit()
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        rootView = _onCreateView(inflater, container, savedInstanceState)
        return rootView
    }

    abstract fun _onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View?

    interface OnBackPressedListener {
        fun doBack(): Boolean
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState?.putBoolean("STATE_SAVE_IS_HIDDEN", isHidden)
    }


    protected var mSnackBar: Snackbar? = null
    fun snack(view: View, text: String, actionTextId: Int = 0, onClickListener: View.OnClickListener? = null) {
        if (!userVisibleHint) return
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

    fun dismissSnack() {
        if (mSnackBar?.isShown == true) {
            mSnackBar?.dismiss()
        }
    }
}