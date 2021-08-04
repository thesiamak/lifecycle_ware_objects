package com.dotin.app.db.inspection

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.dotin.app.model.Address
import com.dotin.app.model.Inspection


@Dao
interface InspectionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(inspection: Inspection)

    @Query("SELECT * FROM inspection")
    suspend fun getAllIspection(): List<Inspection>

    @Query("SELECT * FROM inspection WHERE id = :id  LIMIT 1 ")
    suspend fun getSelectedInspcetion(id:String): Inspection
}