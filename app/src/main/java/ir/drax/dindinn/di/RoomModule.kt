package ir.drax.dindinn.di

import android.app.Application
import androidx.room.Room
import ir.drax.dindinn.db.DDDatabase
import ir.drax.dindinn.db.inspection.AddressDao
import com.dotin.app.db.inspection.InspectionDao
import com.dotin.app.model.DotinConst.DOTIN_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        application: Application,
    ): DDDatabase {
        return Room
            .databaseBuilder(application, DDDatabase::class.java, DOTIN_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    internal fun provideAddressDao(appDatabase: DDDatabase): AddressDao {
        return appDatabase.addressDAo()
    }

    @Provides
    @Singleton
    internal fun provideInspectionDao(appDatabase: DDDatabase): InspectionDao {
        return appDatabase.inspectionDao()
    }
}