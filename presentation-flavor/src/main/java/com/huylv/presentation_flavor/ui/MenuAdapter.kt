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

class MenuAdapter : ListAdapter<FlavorModel, MenuAdapter.ViewHolder>(DiffCallBack()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rootView = LayoutInflater.from(parent.context).inflate(
            R.layout.layout_flavor_item,
            parent,
            false
        )
        return ViewHolder(rootView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.updateView(position, getItem(position))
    }

    override fun getItemCount(): Int {
        return currentList.size
    }


    class ViewHolder(private val rootView: View) : RecyclerView.ViewHolder(rootView) {
        fun updateView(position: Int, flavor: FlavorModel) {
            if (position == 0)
                rootView.llTitle.visibility = View.VISIBLE
            else
                rootView.llTitle.visibility = View.GONE

            rootView.tvFlavor.text = flavor.name
            rootView.tvPrice.text =
                rootView.context.getString(R.string.price_str, flavor.price.toString())
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
