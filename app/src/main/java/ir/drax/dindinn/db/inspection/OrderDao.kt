package ir.drax.dindinn.db.inspection

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Completable
import ir.drax.dindinn.network.model.Order

@Dao
interface OrderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun add(order: Order)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(order: Order)

    @Delete
    fun delete(order: Order): Completable

    @Query("SELECT * FROM `order`")
    fun getAll(): LiveData<List<Order>>
}