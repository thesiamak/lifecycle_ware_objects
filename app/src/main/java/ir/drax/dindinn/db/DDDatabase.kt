package ir.drax.dindinn.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.drax.dindinn.db.inspection.Converters
import ir.drax.dindinn.db.inspection.OrderDao
import ir.drax.dindinn.network.model.Order

@Database(entities = [
    Order::class
],
    version = 2,
    exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class DDDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao
}