package ir.drax.samples.lifecycle.network.model

import androidx.lifecycle.MutableLiveData
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.*

class OrderWrapper(
    id: Long,
    title: String,
    addon: List<Addon>,
    quantity: Int,
    created_at: String,
    alerted_at: String,
    expired_at: String,
    val code:String=id.toString()
) : Order(id, title, addon, quantity,
    SimpleDateFormat("hh:mm aa",Locale.getDefault()).format(Date.from(Instant.parse(created_at))),
    alerted_at, expired_at) {

    val remainingMin: MutableLiveData<String> = MutableLiveData("0")
}