package com.andreirookie.essentialcookings.util

import android.content.Context
import android.view.View
import android.view.ViewTreeObserver
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object AppUtils {

    fun hideKeyboard(view: View) {
        val imm = view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    fun EditText.setCursorAtEndWithFocusAndShowKeyboard() {
        /**
         * This is to be called when the window already has focus.
         */

        fun EditText.showTheKeyboardNow() {
            post {
                // We still post the call, just in case we are being notified of the windows focus
                // but InputMethodManager didn't get properly setup yet.
                val imm =
                    context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)

            }
            // set cursor to (position) end
            this.setSelection(text.length)

        }

        if (hasWindowFocus()) {
            // No need to wait for the window to get focus.
            //showTheKeyboardNow()
        } else {
            clearFocus()
            showTheKeyboardNow()
            // We need to wait until the window gets focus.
            viewTreeObserver.addOnWindowFocusChangeListener(
                object : ViewTreeObserver.OnWindowFocusChangeListener {
                    override fun onWindowFocusChanged(hasFocus: Boolean) {
                        // This notification will arrive just before the InputMethodManager gets set up.
                        if (hasFocus) {
                            this@setCursorAtEndWithFocusAndShowKeyboard.showTheKeyboardNow()
                            // It’s very important to remove this listener once we are done.
                            viewTreeObserver.removeOnWindowFocusChangeListener(this)
                        }

                    }
                })
            requestFocus()
        }

    }
}