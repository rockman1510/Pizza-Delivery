package com.huylv.presentation_flavor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.databinding.FragmentBillBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BillFragment : Fragment() {

    private lateinit var binding: FragmentBillBinding

    private val safeArgs: BillFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bill, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        safeArgs.bill.flavors?.let {
            if (it.size == 2) {
                binding.pizzaModel = safeArgs.bill
            }
        }
    }
}