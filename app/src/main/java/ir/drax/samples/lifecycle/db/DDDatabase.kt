package ir.drax.samples.lifecycle.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.drax.samples.lifecycle.db.inspection.Converters
import ir.drax.samples.lifecycle.db.inspection.OrderDao
import ir.drax.samples.lifecycle.network.model.Order

@Database(entities = [
    Order::class
],
    version = 9,
    exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class DDDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao
}