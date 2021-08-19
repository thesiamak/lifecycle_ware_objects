package ir.drax.samples.lifecycle.ui.ingredients

import android.os.Bundle
import android.view.View
import androidx.core.widget.doAfterTextChanged
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import io.reactivex.disposables.Disposable
import ir.drax.samples.lifecycle.databinding.FragmentIngredientsBinding
import ir.drax.samples.lifecycle.network.model.Type
import ir.drax.samples.lifecycle.ui.BaseFragment
import ir.drax.samples.lifecycle.ui.SharedViewModel
import kotlinx.android.synthetic.main.fragment_ingredients.*


class IngredientsFragment : BaseFragment<FragmentIngredientsBinding, SharedViewModel>(FragmentIngredientsBinding::inflate) {

    private lateinit var ingredientAdapter :IngredientsListAdapter
    private var queryJob: Disposable?=null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ingredientsList.apply {
            layoutManager = GridLayoutManager(requireContext(),5)
            ingredientAdapter = IngredientsListAdapter()
            adapter = ingredientAdapter
        }
        viewModel.ingredients.observe(viewLifecycleOwner){ ingredientsList->
            ingredientAdapter.list = ingredientsList

            tabs.removeAllTabs()
            ingredientsList.groupBy {
                it.type
            }.keys.forEach {type->
                tabs.addTab(tabs.newTab().setText(type.name).apply {
                    this.view.setOnClickListener {
                        filterType(type)
                    }
                })
            }
        }
        viewModel.getIngredients()

        searchInput.doAfterTextChanged {
            queryJob?.dispose()

            queryJob = if (it.isNullOrEmpty())
                viewModel.getIngredients(it.toString())
            else
                viewModel.getIngredients(it.toString())

        }

        back.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun filterType(type: Type){
         viewModel.ingredients.value?.let {
             ingredientAdapter.list = it.filter { it.type == type }
        }
    }
}