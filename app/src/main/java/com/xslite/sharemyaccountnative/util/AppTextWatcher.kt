package com.xslite.sharemyaccountnative.util

import android.text.Editable
import android.text.TextWatcher

abstract class AppTextWatcher:TextWatcher {
    override fun beforeTextChanged(p0 : CharSequence?, p1 : Int, p2 : Int, p3 : Int) {
        //empty
    }
    override fun afterTextChanged(p0 : Editable?) {
        //empty
    }
}