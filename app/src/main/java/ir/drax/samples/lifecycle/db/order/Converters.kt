package ir.drax.samples.lifecycle.db.order

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ir.drax.samples.lifecycle.network.model.Addon


class Converters {

    companion object {

        @TypeConverter
        @JvmStatic
        fun listOfAddonsToJson(value: List<Addon>?) = Gson().toJson(value)

        @TypeConverter
        @JvmStatic
        fun jsonToListOfAddons(value: String?):List<Addon>?{
            val listType = object : TypeToken<List<Addon>>() {}.type
            return Gson().fromJson(value, listType)
        }
    }
}