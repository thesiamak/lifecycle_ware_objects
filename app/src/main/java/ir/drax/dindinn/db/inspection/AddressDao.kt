package ir.drax.dindinn.db.inspection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dotin.app.model.Address

@Dao
interface AddressDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(address:Address)

    @Query("SELECT * FROM address")
    suspend fun get(): List<Address>
}