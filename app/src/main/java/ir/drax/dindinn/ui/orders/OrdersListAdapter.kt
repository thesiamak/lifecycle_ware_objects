package ir.drax.dindinn.ui.orders

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import ir.drax.dindinn.databinding.OrderListItemBinding
import ir.drax.dindinn.network.model.Order
import ir.drax.dindinn.network.model.OrderWrapper
import ir.drax.dindinn.util.SoundUtil
import java.time.Instant
import java.util.concurrent.TimeUnit

class OrdersListAdapter(private val context: Context,private val mlifecycleOwner:LifecycleOwner,private val onItemAccepted: (Order) -> Unit, private val onItemExpired: (Order) -> Unit) : RecyclerView.Adapter<OrdersListAdapter.OrderViewHolder>() {
    // List of orders
    var orders: MutableList<Order> = ArrayList(0)
    var timerDisposables = mutableMapOf<Long,Disposable>()

    fun add(newOrders:List<Order>){
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()

        setAlarmTimers(orders)
    }

    fun setAlarmTimers(list: List<Order>){
        list.forEach {
            val diff =  Instant.parse(it.alerted_at).toEpochMilli() - System.currentTimeMillis()
            if (diff>0)
            {
                timerDisposables.putIfAbsent(
                    it.id,

                    Observable.create<String> { emitter ->
                        emitter.onNext(it.alerted_at)
                        emitter.onComplete()
                    }
                        .subscribeOn(Schedulers.io())
                        .delay(diff, TimeUnit.MILLISECONDS)
                        .doOnError { error ->
                            error.printStackTrace()
                        }
                        .doOnComplete {
                            timerDisposables.remove(it.id)
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            Log.d("Alert thrown at", it)
                            SoundUtil.playSound(context)
                        }
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = OrderListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            null,
            false
        )

        return OrderViewHolder(mlifecycleOwner,binding,
            onItemAccepted = {
                // callback lambda function and passes the proper data by finding the data based on its position
                orders[it].let{order->
                    onItemAccepted(order)

                    // dispose alarm timer so that there will no outdated alarms.
                    timerDisposables[order.id]?.dispose()
                }
            },

            onItemExpired={
                onItemExpired(orders[it])
            })
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {

        holder.bind(orders[position])
    }

    override fun getItemCount(): Int = orders.size

    override fun getItemViewType(position: Int) = orders[position].id.toInt()

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