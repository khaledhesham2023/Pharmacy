package com.khaledamin.pharmacy_android.utils

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.text.Editable
import android.text.TextWatcher
import com.google.android.material.textfield.TextInputLayout
import java.util.Locale

object DisplayManager {

    fun showAlertDialog(
        context: Context,
        title: Int,
        message: Int,
        positiveText: Int,
        negativeText: Int,
        listener: DialogInterface.OnClickListener,
    ) {
        val builder =
            AlertDialog.Builder(context).setCancelable(true).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText, listener)
                .setNegativeButton(negativeText, null)
                .create()
        builder.show()
    }

    fun showErrorAlertDialog(
        context: Context,
        title: Int,
        message: String,
        positiveText: Int?,
        negativeText: Int,
        listener: DialogInterface.OnClickListener,
    ) {
        val builder =
            AlertDialog.Builder(context).setCancelable(true).setTitle(title).setMessage(message)
                .setPositiveButton(positiveText!!, listener)
                .setNegativeButton(negativeText, null)
                .create()
        builder.show()
    }

    fun removeErrorsWhenEditing(vararg textInputs: TextInputLayout) {
        for (textInput in textInputs) {
            textInput.editText!!.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
//                TODO("Not yet implemented")
                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    textInput.isErrorEnabled = false
                }

                override fun afterTextChanged(s: Editable?) {
//                TODO("Not yet implemented")
                }

            })
        }
    }

    fun setLocale(context: Context,language:String){
        val locale = Locale(language)
        Locale.setDefault(locale)
        val resources = context.resources
        val displayMetrics = resources.displayMetrics
        val configuration = resources.configuration
        configuration.setLocale(locale)
        resources.updateConfiguration(configuration,displayMetrics)
    }
}