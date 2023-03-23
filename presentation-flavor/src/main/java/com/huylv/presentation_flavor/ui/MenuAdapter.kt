package com.huylv.presentation_flavor.ui

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.databinding.LayoutFlavorItemBinding
import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.model.PizzaModel
import com.huylv.presentation_flavor.utils.Utils

class MenuAdapter(private val makePizzaListener: MakePizzaListener) :
    ListAdapter<FlavorModel, MenuAdapter.ViewHolder>(DiffCallBack()) {

    private var isEnableSpinner = true

    private val listener = object : SelectFlavorListener {

        @SuppressLint("NotifyDataSetChanged")
        override fun onChangeFlavorSelected(flavor: FlavorModel, position: Int) {
            currentList.let {
                flavor.let { flavor ->
                    currentList.set(position, flavor)
                    isEnableSpinner = !Utils.isAPizza(currentList)
                    makePizzaListener.onMakePizza(
                        Utils.convertFlavorToPizza(currentList),
                        !isEnableSpinner
                    )
                    notifyDataSetChanged()
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: LayoutFlavorItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.layout_flavor_item,
            parent,
            false
        )
        val spinnerAdapter = ArrayAdapter.createFromResource(
            parent.context,
            R.array.flavor_quantity,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        }
        return ViewHolder(binding, spinnerAdapter)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val isShow: Boolean = position == 0
        holder.bind(position, getItem(position), listener, isShow, isEnableSpinner)
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    class ViewHolder(
        private val binding: LayoutFlavorItemBinding,
        private val spinnerAdapter: ArrayAdapter<CharSequence>
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            position: Int,
            flavor: FlavorModel,
            listener: SelectFlavorListener,
            isShowTitle: Boolean = false,
            isEnableSpinner: Boolean
        ) {
            if (isShowTitle)
                binding.llTitle.visibility = View.VISIBLE
            binding.flavor = flavor
            binding.spinner.adapter = spinnerAdapter
            binding.spinner.isEnabled = !(flavor.quantity == 0.0F && !isEnableSpinner)
            setSpinnerSelection(flavor, binding.spinner)
            binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    val newFlavorModel = FlavorModel(
                        flavor.name,
                        flavor.price,
                        (spinnerAdapter.getItem(p2) as String).toFloat()
                    )
                    listener.onChangeFlavorSelected(newFlavorModel, position)
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {

                }
            }
        }

        private fun setSpinnerSelection(flavor: FlavorModel, spinner: Spinner) {
            when (flavor.quantity) {
                0F -> {
                    spinner.setSelection(0)
                }
                0.5F -> {
                    spinner.setSelection(1)
                }
                1F -> {
                    spinner.setSelection(2)
                }
            }
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<FlavorModel>() {

        override fun areItemsTheSame(oldItem: FlavorModel, newItem: FlavorModel): Boolean {
            return oldItem.quantity == newItem.quantity
        }

        override fun areContentsTheSame(oldItem: FlavorModel, newItem: FlavorModel): Boolean {
            return oldItem == newItem
        }
    }

    interface SelectFlavorListener {
        fun onChangeFlavorSelected(flavor: FlavorModel, position: Int)
    }

    interface MakePizzaListener {
        fun onMakePizza(pizzaModel: PizzaModel, isEnable: Boolean)
    }
}
