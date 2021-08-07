package ir.drax.dindinn.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ir.drax.dindinn.databinding.OrderListItemBinding
import ir.drax.dindinn.network.model.Order

class OrdersListAdapter(private val onItemClicked: (Order) -> Unit) : RecyclerView.Adapter<OrdersListAdapter.RepositoryViewHolder>() {
    // List of repositories
    var repositories: List<Order> = ArrayList(0)
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoryViewHolder {
        val binding = OrderListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            null,
            false
        )

        return RepositoryViewHolder(binding) {
            // callback lambda function and passes the proper data by finding the data based on its position
            onItemClicked(repositories[it])
        }
    }

    override fun onBindViewHolder(holder: RepositoryViewHolder, position: Int) {
        holder.bind(repositories[position])
    }

    override fun getItemCount(): Int = repositories.size

    /**
     * A ViewHolder for the [OrdersListAdapter]
     * Receives @param [onItemClicked] as lambda function. The lambda function is called with the [getAbsoluteAdapterPosition] of selected item
     */
    inner class RepositoryViewHolder(private val binding: OrderListItemBinding, onItemClicked: (Int) -> Unit) : RecyclerView.ViewHolder(binding.root) {
        init {
            binding.root.setOnClickListener {
                // when item is clicked, callback lambda with its position
                onItemClicked(adapterPosition)
            }
        }

        fun bind(order: Order) {
            with(binding) {
                // setup bind data
                this.order = order
            }
        }
    }
}