package ir.drax.dindinn.network.model

data class ServerResponse<T>(val data:T, val status:Status)
data class Status(val success:Boolean, val statusCode:Int,val message:String)