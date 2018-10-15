package com.jinli.mykotlin.witgets

import android.text.Editable
import android.text.TextWatcher

/**
 * Created by Jin on 10/15/2018
 */
open class DefaultTextWatcher:TextWatcher{
    override fun afterTextChanged(s: Editable?) {
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}
