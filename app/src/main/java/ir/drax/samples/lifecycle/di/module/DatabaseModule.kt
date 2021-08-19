package ir.drax.samples.lifecycle.di.module

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import ir.drax.samples.lifecycle.db.DDDatabase
import ir.drax.samples.lifecycle.db.order.OrderDao
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
    fun provideOrderDao(ddDatabase: DDDatabase): OrderDao = ddDatabase.orderDao()
}