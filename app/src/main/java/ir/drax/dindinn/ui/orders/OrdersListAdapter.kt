package ir.drax.dindinn.ui.orders

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import ir.drax.dindinn.databinding.OrderListItemBinding
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.network.model.OrderWrapper

class OrdersListAdapter(private val mlifecycleOwner:LifecycleOwner,private val onItemAccepted: (Order) -> Unit, private val onItemExpired: (Order) -> Unit) : RecyclerView.Adapter<OrdersListAdapter.OrderViewHolder>() {
    // List of orders
    var orders: MutableList<Order> = ArrayList(0)


    fun add(newOrders:List<Order>){
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            null,
            false
        )

        return OrderViewHolder(mlifecycleOwner,binding, {
            // callback lambda function and passes the proper data by finding the data based on its position
            onItemAccepted(orders[it])
        }){
            onItemExpired(orders[it])
        }
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    /**
     * A ViewHolder for the [OrdersListAdapter]
     * Receives @param [onItemClicked] as lambda function. The lambda function is called with the [getAbsoluteAdapterPosition] of selected item
     */
    inner class OrderViewHolder(mlifecycleOwner:LifecycleOwner, private val binding: OrderListItemBinding,
                                onItemAccepted: (Int) -> Unit,
                                onItemExpired: (Int) -> Unit) :
        RecyclerView.ViewHolder(binding.apply {
            lifecycleOwner = mlifecycleOwner
            mlifecycleOwner.lifecycle.addObserver(rejectCounter)
        }.root
        ) {

        init {
            binding.acceptBtn.setOnClickListener {
                onItemAccepted(adapterPosition)
            }

            binding.expiredBtn.setOnClickListener {
                onItemExpired(adapterPosition)
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

        fun clear(){
            binding.rejectCounter.clear()
        }
        fun pulse(){
            binding.rejectCounter.triggerTimer()
        }
    }

    override fun onViewAttachedToWindow(holder: OrderViewHolder) {
        super.onViewAttachedToWindow(holder)
        holder.pulse()
    }

    override fun onViewDetachedFromWindow(holder: OrderViewHolder) {
        super.onViewDetachedFromWindow(holder)
        holder.clear()
    }
}