package ir.drax.dindinn.ui.orders

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.drax.dindinn.databinding.FragmentOrdersBinding
import ir.drax.dindinn.ui.BaseFragment
import ir.drax.dindinn.ui.SharedViewModel
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : BaseFragment<FragmentOrdersBinding, SharedViewModel>(FragmentOrdersBinding::inflate) {

    private lateinit var ordersListAdapter: OrdersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersListAdapter = OrdersListAdapter(viewLifecycleOwner) {

        }

        viewModel.orders.observe(viewLifecycleOwner){
            if (it == null)
                viewModel.getOrders()
            ordersListAdapter.orders = it
        }

        list.apply {
            adapter = ordersListAdapter
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        }

        gotoIngredient.setOnClickListener {
            findNavController().navigate(OrdersFragmentDirections.ordersToIngredients())
        }
    }
}