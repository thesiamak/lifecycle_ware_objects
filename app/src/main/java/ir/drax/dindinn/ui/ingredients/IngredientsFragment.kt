package ir.drax.dindinn.ui.ingredients

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import ir.drax.dindinn.databinding.FragmentIngredientsBinding
import ir.drax.dindinn.ui.BaseFragment
import ir.drax.dindinn.ui.SharedViewModel


class IngredientsFragment : BaseFragment<FragmentIngredientsBinding, SharedViewModel>(FragmentIngredientsBinding::inflate) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}