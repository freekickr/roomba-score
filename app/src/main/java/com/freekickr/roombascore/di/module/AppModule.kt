package com.freekickr.roombascore.di.module

import android.content.Context
import com.freekickr.roombascore.App
import com.freekickr.roombascore.di.AppContext
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ProvidersModule::class])
abstract class AppModule {

    @Singleton
    @Binds
    @AppContext
    abstract fun provideContext(app: App): Context

}