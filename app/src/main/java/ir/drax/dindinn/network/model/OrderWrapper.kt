package ir.drax.dindinn.network.model

import androidx.lifecycle.MutableLiveData
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.format.DateTimeFormatter
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