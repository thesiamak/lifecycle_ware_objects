package ir.drax.dindinn.network.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Order(@PrimaryKey val id:Int, val title:String, val addon:List<Addon>, val quantity:Int, val created_at:String, val alerted_at:String, val expired_at:String)
data class Addon(val id:Int, val title:String, val quantity:Int)