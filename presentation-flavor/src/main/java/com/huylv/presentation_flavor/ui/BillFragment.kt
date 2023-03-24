package com.huylv.presentation_flavor.ui

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.huylv.presentation_flavor.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_bill.*

@AndroidEntryPoint
class BillFragment : Fragment() {

    private val safeArgs: BillFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_bill, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        safeArgs.bill.flavors?.let {
            rvSelectedFlavor.layoutManager =
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            val billAdapter = BillAdapter()
            rvSelectedFlavor.adapter = billAdapter
            billAdapter.submitList(it)
            tvTotal.text = getString(R.string.total_price, safeArgs.bill.price.toString())
        }
        btClose.setOnClickListener {
            (activity as Activity).onBackPressed()
        }
    }
}