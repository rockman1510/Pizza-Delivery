package com.huylv.presentation_flavor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huylv.presentation_base.mvi.UiState
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.model.PizzaModel
import com.huylv.presentation_flavor.mvi.MenuUiAction
import com.huylv.presentation_flavor.mvi.MenuViewModel
import com.huylv.presentation_flavor.utils.Utils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_menu.*
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MenuFragment : Fragment() {

    private lateinit var menuViewModel: MenuViewModel

    private lateinit var menuAdapter: MenuAdapter

    private lateinit var flavors: ArrayList<FlavorModel>
    private var flavorIndexSelected1 = -1
    private var flavorIndexSelected2 = -1
    private var totalPrice = 0.0F

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        return inflater.inflate(R.layout.fragment_menu, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        flavors = ArrayList()
        menuViewModel.submitAction(MenuUiAction.Load)
        lifecycleScope.launchWhenResumed {
            totalPrice = 0.0F
            updateTvTotalPrice(totalPrice)
            spinner1.adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_spinner_item,
                ArrayList<String>()
            )
            menuViewModel.uiStateFlow.collectLatest {
                onState(it)
            }
        }
    }

    private fun onState(state: UiState<List<FlavorModel>>) {
        when (state) {
            is UiState.Loading -> {
            }
            is UiState.Success -> {
                flavors = state.data as ArrayList<FlavorModel>
                menuAdapter.submitList(state.data.toMutableList())
                val spinnerAdapter = Utils.createSpinnerFlavorAdapter(
                    requireContext(),
                    android.R.layout.simple_spinner_item,
                    flavors
                )
                spinner1.adapter = spinnerAdapter
                spinner2.adapter = spinnerAdapter
            }
            is UiState.Error -> {
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        updateTvTotalPrice(totalPrice)
        rvFlavor.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        menuAdapter = MenuAdapter()
        rvFlavor.adapter = menuAdapter
        setOnClickButtons()
        setUpSpinners()
    }

    private fun updateTvTotalPrice(totalPrice: Float) {
        tvTotal.text = getString(R.string.total_price, totalPrice.toString())
    }

    private fun setUpSpinners() {
        spinner1.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                flavorIndexSelected1 = if (p2 > 0) {
                    p2 - 1
                } else {
                    -1
                }
                updateTotalPrice()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }

        spinner2.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                flavorIndexSelected2 = if (p2 > 0) {
                    p2 - 1
                } else {
                    -1
                }
                updateTotalPrice()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
    }

    private fun updateTotalPrice() {
        if (flavorIndexSelected1 >= 0 && flavorIndexSelected2 >= 0) {
            totalPrice =
                ((flavors[flavorIndexSelected1].price * 0.5) +
                        (flavors[flavorIndexSelected2].price * 0.5)).toFloat()
        } else if (flavorIndexSelected1 < 0 && flavorIndexSelected2 >= 0) {
            totalPrice = flavors[flavorIndexSelected2].price
        } else if (flavorIndexSelected1 >= 0) {
            totalPrice = flavors[flavorIndexSelected1].price
        } else {
            totalPrice = 0.0F
        }
        updateTvTotalPrice(totalPrice)
    }

    private fun setOnClickButtons() {
        btOrder.setOnClickListener {
            if (totalPrice > 0F) {
                val pizzaModel = PizzaModel(generateSelectedFlavors(), totalPrice)
                btOrder.isEnabled = false
                performOrderConfirmation(pizzaModel)
            } else {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.select_message_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun generateSelectedFlavors(): List<FlavorModel> {
        val selectedFlavors = ArrayList<FlavorModel>()
        if (flavorIndexSelected1 >= 0 && flavorIndexSelected2 >= 0) {
            if (flavorIndexSelected1 == flavorIndexSelected2) {
                selectedFlavors.add(flavors[flavorIndexSelected1])
            } else {
                selectedFlavors.add(flavors[flavorIndexSelected1])
                selectedFlavors.add(flavors[flavorIndexSelected2])
            }
        } else if (flavorIndexSelected1 < 0 && flavorIndexSelected2 >= 0) {
            selectedFlavors.add(flavors[flavorIndexSelected2])
        } else if (flavorIndexSelected1 >= 0 && flavorIndexSelected2 < 0) {
            selectedFlavors.add(flavors[flavorIndexSelected1])
        }
        return selectedFlavors
    }

    private fun performOrderConfirmation(pizzaModel: PizzaModel) {
        fun onConfirmCallBack(isConfirm: Boolean) {
            btOrder.isEnabled = true
            if (isConfirm)
                findNavController().navigate(MenuFragmentDirections.goToBill(pizzaModel))
        }
        Utils.showConfirmDialog(
            requireContext(),
            getString(R.string.make_order),
            getString(R.string.order_message),
            ::onConfirmCallBack
        )
    }

}