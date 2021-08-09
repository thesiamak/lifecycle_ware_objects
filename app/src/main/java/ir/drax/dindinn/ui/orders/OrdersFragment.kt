package ir.drax.dindinn.ui.orders

import android.os.Bundle
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

            },
            onItemExpired= {
                // Show a different message here.
            })

        viewModel.orders.observe(viewLifecycleOwner){
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