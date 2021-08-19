package ir.drax.samples.lifecycle.ui.orders

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.drax.samples.lifecycle.ui.BaseFragment
import ir.drax.samples.lifecycle.ui.SharedViewModel
import ir.drax.samples.lifecycle.util.message
import kotlinx.android.synthetic.main.fragment_orders.*
import lifecycle.R
import lifecycle.databinding.FragmentOrdersBinding


class OrdersFragment : BaseFragment<FragmentOrdersBinding, SharedViewModel>(FragmentOrdersBinding::inflate) {

    private lateinit var ordersListAdapter: OrdersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersListAdapter = OrdersListAdapter(requireContext(),viewLifecycleOwner,
            onItemAccepted = {
                message(R.string.item_accepted)
                viewModel.deleteOrder(it)
            },
            onItemExpired= {
                // Show a different message here.
                viewModel.deleteOrder(it)
            })

        viewModel.orders.observe(viewLifecycleOwner){
            if (it.isEmpty())
                viewModel.updateOrders()

                ordersListAdapter.add(it)
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
