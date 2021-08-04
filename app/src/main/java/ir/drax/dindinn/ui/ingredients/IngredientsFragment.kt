package ir.drax.dindinn.ui.ingredients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import ir.drax.dindinn.databinding.FragmentIngredientsBinding
import ir.drax.dindinn.ui.BaseFragment
import ir.drax.dindinn.ui.SharedViewModel
import kotlinx.coroutines.InternalCoroutinesApi

@AndroidEntryPoint
class IngredientsFragment : BaseFragment<FragmentIngredientsBinding, SharedViewModel>(FragmentIngredientsBinding::inflate) {
    override val viewModel: SharedViewModel by viewModels()

    @InternalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}