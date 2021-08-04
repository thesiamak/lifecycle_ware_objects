package ir.drax.dindinn.network.model

data class Order(val id:Int, val title:String, val addon:List<Addon>, val quantity:Int, val created_at:String,val alerted_at:String,val expired_at:String)
data class Addon(val id:Int, val title:String, val quantity:Int)