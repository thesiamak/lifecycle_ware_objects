package ir.drax.dindinn.network.model

import androidx.room.PrimaryKey

data class Ingredient(val id:Long,
                      val title:String,
                      val quantity:Int,
                      val available:Boolean,
                      val type:Type,
                      val coverUrl:String )

enum class Type{
    Bento, Main, Appetizer, Chicken
}