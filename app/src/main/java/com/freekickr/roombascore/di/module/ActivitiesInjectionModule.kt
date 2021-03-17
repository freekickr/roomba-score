package com.freekickr.roombascore.di.module

import com.freekickr.roombascore.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesInjectionModule {

    @ContributesAndroidInjector
    abstract fun contributeActivityAndroidInjector(): MainActivity

}