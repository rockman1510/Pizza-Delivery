package com.huylv.presentation_flavor.utils

import android.app.AlertDialog
import android.content.Context
import android.widget.ArrayAdapter
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.model.FlavorModel

object Utils {

    fun createSpinnerFlavorAdapter(
        context: Context,
        id: Int,
        data: List<FlavorModel>
    ): ArrayAdapter<String> {
        val newData = ArrayList<String>()
        newData.add(context.getString(R.string.select_one))
        data.forEach {
            it.name?.let { name ->
                newData.add(name)
            }
        }
        return ArrayAdapter(context, id, newData)
    }

    fun showConfirmDialog(
        context: Context,
        title: String,
        message: String,
        onConfirm: (isConfirm: Boolean) -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(title).setMessage(message)
            .setPositiveButton(
                R.string.confirm
            ) { _, _ -> onConfirm.invoke(true) }.setNegativeButton(
                R.string.cancel
            ) { p0, _ ->
                p0?.dismiss()
                onConfirm.invoke(false)
            }.show()
    }
}