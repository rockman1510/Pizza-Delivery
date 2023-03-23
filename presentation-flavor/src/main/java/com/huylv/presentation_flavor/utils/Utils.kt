package com.huylv.presentation_flavor.utils

import android.app.AlertDialog
import android.content.Context
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.model.PizzaModel

object Utils {

    fun convertFlavorToPizza(data: List<FlavorModel>?): PizzaModel {
        var price = 0F
        val newData = ArrayList<FlavorModel>()
        data?.forEach {
            price += (it.price * it.quantity)
            if (it.quantity > 0F)
                newData.add(it)
        }
        return PizzaModel(newData, price)
    }

    fun isAPizza(data: List<FlavorModel>?): Boolean {
        var quantity = 0F
        data?.forEach {
            quantity += it.quantity
        }
        return quantity == 1F
    }

    fun showConfirmDialog(context: Context, title: String, message: String, onConfirm: () -> Unit) {
        AlertDialog.Builder(context)
            .setTitle(title).setMessage(message)
            .setPositiveButton(
                R.string.confirm
            ) { _, _ -> onConfirm.invoke() }.setNegativeButton(
                R.string.cancel
            ) { p0, _ -> p0?.dismiss() }.show()
    }
}