package com.freekickr.roombascore.di

import com.freekickr.roombascore.App
import com.freekickr.roombascore.di.module.ActivitiesInjectionModule
import com.freekickr.roombascore.di.module.AppModule
import com.freekickr.roombascore.di.module.FragmentsInjectionModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, ActivitiesInjectionModule::class, FragmentsInjectionModule::class, AppModule::class])
interface AppComponent {

    fun inject(app: App)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance application: App): AppComponent
    }

}