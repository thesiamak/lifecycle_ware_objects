package ir.drax.samples.lifecycle.ui.ingredients

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.drax.samples.lifecycle.network.model.Ingredient
import lifecycle.databinding.IngredientsListItemBinding

class IngredientsListAdapter : RecyclerView.Adapter<IngredientsListAdapter.IngredientViewHolder>() {

    var list: List<Ingredient> = listOf()
    set(value) {
        field = value
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        val binding = IngredientsListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            null,
            false
        )

        return IngredientViewHolder(binding)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount(): Int = list.size

    /**
     * A ViewHolder for the [IngredientsListAdapter]
     */
    inner class IngredientViewHolder(private val binding: IngredientsListItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(ingredient: Ingredient) {
            with(binding) {
                // setup bind data
                this.ingredient = ingredient
            }
        }
    }
}