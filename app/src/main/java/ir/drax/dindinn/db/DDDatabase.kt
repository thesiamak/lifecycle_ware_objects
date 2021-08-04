package ir.drax.dindinn.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ir.drax.dindinn.db.inspection.Converters

@Database(entities = [

                     ],
    version = 1,
    exportSchema = false)
@TypeConverters(value = [Converters::class])
abstract class DDDatabase : RoomDatabase() {

    /*abstract fun addressDAo(): AddressDao
    abstract fun inspectionDao(): InspectionDao
*/
}