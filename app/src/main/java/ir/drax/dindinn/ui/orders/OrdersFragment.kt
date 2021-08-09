package ir.drax.dindinn.ui.orders

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ir.drax.dindinn.R
import ir.drax.dindinn.databinding.FragmentOrdersBinding
import ir.drax.dindinn.ui.BaseFragment
import ir.drax.dindinn.ui.SharedViewModel
import ir.drax.dindinn.util.message
import kotlinx.android.synthetic.main.fragment_orders.*


class OrdersFragment : BaseFragment<FragmentOrdersBinding, SharedViewModel>(FragmentOrdersBinding::inflate) {

    private lateinit var ordersListAdapter: OrdersListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ordersListAdapter = OrdersListAdapter(viewLifecycleOwner,
            onItemAccepted = {
                message(R.string.item_accepted)
                viewModel.deleteOrder(it)
            },
            onItemExpired= {
                // Show a different message here.
                viewModel.deleteOrder(it)
            })

        viewModel.orders.observe(viewLifecycleOwner){
            Log.e("orders size","${it.size}")
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