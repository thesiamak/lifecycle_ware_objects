package ir.drax.dindinn.db.inspection

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.drax.dindinn.network.model.Addon
import ir.drax.dindinn.network.model.Order


class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun listOfAddonsToJson(value: List<Addon>?) = Gson().toJson(value)

        @TypeConverter
        @JvmStatic
        fun jsonToListOfAddons(value: String?):List<Addon>?{
            val listType = object : TypeToken<List<Order>>() {}.type
            return Gson().fromJson(value, listType)
        }
    }
}