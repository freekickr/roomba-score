package com.freekickr.roombascore.di.module

import androidx.room.Room
import com.freekickr.roombascore.App
import com.freekickr.roombascore.database.RoombaDatabase
import com.freekickr.roombascore.di.AppContext
import com.freekickr.roombascore.ui.ViewModelFactory
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ProvidersModule {

    @Singleton
    @Provides
    fun provideDatabase(context: App): RoombaDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            RoombaDatabase::class.java,
            "roomba_database"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Singleton
    @Provides
    @AppContext
    fun provideViewModelFactory(database: RoombaDatabase): ViewModelFactory {
        return ViewModelFactory(database)
    }
}