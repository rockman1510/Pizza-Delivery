package com.huylv.presentation_flavor.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.huylv.presentation_base.mvi.UiState
import com.huylv.presentation_flavor.R
import com.huylv.presentation_flavor.databinding.FragmentMenuBinding
import com.huylv.presentation_flavor.model.FlavorModel
import com.huylv.presentation_flavor.model.PizzaModel
import com.huylv.presentation_flavor.mvi.MenuViewModel
import com.huylv.presentation_flavor.utils.Utils
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment(), MenuAdapter.MakePizzaListener {

    private lateinit var menuViewModel: MenuViewModel

    private lateinit var binding: FragmentMenuBinding

    private lateinit var menuAdapter: MenuAdapter

    private lateinit var pizzaModel: PizzaModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        menuViewModel = ViewModelProvider(this)[MenuViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
        lifecycleScope.launchWhenCreated {
            onState(menuViewModel.uiStateFlow.value)
        }
    }

    private fun onState(state: UiState<List<FlavorModel>>) {
        when (state) {
            is UiState.Loading -> {

            }
            is UiState.Success -> {
                menuAdapter.submitList(state.data.toMutableList())
            }
            is UiState.Error -> {
                Toast.makeText(requireContext(), state.errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun initViews() {
        binding.rvFlavor.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        menuAdapter = MenuAdapter(this)
        binding.adapter = menuAdapter
        setOnClickButtons()
    }

    private fun setOnClickButtons() {
        binding.btOrder.setOnClickListener {
            pizzaModel.let {
                fun onConfirmCallBack() {
                    findNavController().navigate(MenuFragmentDirections.goToBill(it))
                }
                Utils.showConfirmDialog(
                    requireContext(),
                    getString(R.string.make_order),
                    getString(R.string.order_message),
                    ::onConfirmCallBack
                )
            }
        }
    }

    override fun onMakePizza(pizzaModel: PizzaModel, isEnable: Boolean) {
        this.pizzaModel = pizzaModel
        binding.isEnableOrder = isEnable
        binding.totalPrice = pizzaModel.price
    }

}