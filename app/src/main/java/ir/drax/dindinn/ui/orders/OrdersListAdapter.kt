package ir.drax.dindinn.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.updateLayoutParams
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ir.drax.dindinn.databinding.OrderListItemBinding
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.network.model.OrderWrapper

class OrdersListAdapter(private val mlifecycleOwner:LifecycleOwner,private val onItemClicked: (Order) -> Unit) : RecyclerView.Adapter<OrdersListAdapter.RepositoryViewHolder>() {
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

        return RepositoryViewHolder(mlifecycleOwner,binding) {
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
    inner class RepositoryViewHolder(mlifecycleOwner:LifecycleOwner,private val binding: OrderListItemBinding, onItemClicked: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.apply {
            lifecycleOwner = mlifecycleOwner
        }.root
        ) {

        init {
            binding.acceptBtn.setOnClickListener {
                onItemClicked(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }

            binding.expiredBtn.setOnClickListener {
                notifyItemRemoved(adapterPosition)
            }
        }
        fun bind(order: Order) {
            with(binding) {
                // setup bind data
                this.order = with(order) {
                    OrderWrapper(id,title,addon,quantity,created_at,alerted_at,expired_at)
                }
                this.addonsList.adapter = AddonsListAdapter(order.addon)
            }
        }
    }
}