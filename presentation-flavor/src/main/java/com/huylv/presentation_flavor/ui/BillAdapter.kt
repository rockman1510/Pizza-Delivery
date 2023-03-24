package com.huylv.presentation_flavor.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.model.FlavorModel
import kotlinx.android.synthetic.main.layout_flavor_item.view.*

class BillAdapter : ListAdapter<FlavorModel, BillAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_bill_item, parent, false
        )
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    class ViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun updateView(flavor: FlavorModel) {
            rootView.tvFlavor.text = flavor.name
        }
    }

    class DiffCallBack : DiffUtil.ItemCallback<FlavorModel>() {

        override fun areItemsTheSame(oldItem: FlavorModel, newItem: FlavorModel): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: FlavorModel, newItem: FlavorModel): Boolean {
            return oldItem == newItem
        }
    }
}
