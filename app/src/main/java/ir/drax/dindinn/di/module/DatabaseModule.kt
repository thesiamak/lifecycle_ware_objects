package ir.drax.dindinn.di.module

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import ir.drax.dindinn.db.DDDatabase
import ir.drax.dindinn.db.inspection.OrderDao
import ir.drax.dindinn.network.DDApiService
import ir.drax.dindinn.repository.OrdersRepository
import ir.drax.dindinn.repository.OrdersRepositoryImpl
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomDatabase(appContext: Context): DDDatabase {
        return Room.databaseBuilder(appContext, DDDatabase::class.java, "DD_db")
            .setJournalMode(RoomDatabase.JournalMode.WRITE_AHEAD_LOGGING)
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideOrderDao(ddDatabase: DDDatabase):OrderDao = ddDatabase.orderDao()
}