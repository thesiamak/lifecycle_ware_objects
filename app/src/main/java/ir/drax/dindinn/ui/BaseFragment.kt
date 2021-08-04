package ir.drax.dindinn.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel



typealias Inflate<T> = (LayoutInflater, ViewGroup?, Boolean) -> T
abstract class  BaseFragment<out T: ViewDataBinding, out E:ViewModel>(private val inflate:Inflate<T>) : Fragment() {

    protected abstract val viewModel: E

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflate.invoke(inflater,container,false).apply {
            lifecycleOwner = viewLifecycleOwner
            setVariable(BR.model,viewModel)
        }.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

}