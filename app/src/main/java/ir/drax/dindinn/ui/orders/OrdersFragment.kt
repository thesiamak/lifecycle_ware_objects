package ir.drax.dindinn.ui.orders

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.drax.dindinn.databinding.FragmentOrdersBinding
import ir.drax.dindinn.ui.BaseFragment
import ir.drax.dindinn.ui.SharedViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class OrdersFragment : BaseFragment<FragmentOrdersBinding, SharedViewModel>(FragmentOrdersBinding::inflate) {
    override val viewModel: SharedViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}