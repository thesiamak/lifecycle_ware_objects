package ir.drax.dindinn.db.inspection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import ir.drax.dindinn.network.model.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(order: Order)

    @Query("SELECT * FROM `order`")
    suspend fun getAll(): List<Order>
}