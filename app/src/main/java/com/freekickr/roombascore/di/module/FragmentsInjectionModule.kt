package com.freekickr.roombascore.di.module

import com.freekickr.roombascore.ui.gameover.GameOverFragment
import com.freekickr.roombascore.ui.gameplay.GameplayFragment
import com.freekickr.roombascore.ui.gameplayers.PlayersFragment
import com.freekickr.roombascore.ui.gamesetup.GameSetupFragment
import com.freekickr.roombascore.ui.highscores.HighscoresFragment
import com.freekickr.roombascore.ui.mainfragment.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentsInjectionModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

    @ContributesAndroidInjector
    abstract fun contributeGameSetupFragment(): GameSetupFragment

    @ContributesAndroidInjector
    abstract fun contributePlayersFragment(): PlayersFragment

    @ContributesAndroidInjector
    abstract fun contributeGameplayFragment(): GameplayFragment

    @ContributesAndroidInjector
    abstract fun contributeGameOverFragment(): GameOverFragment

    @ContributesAndroidInjector
    abstract fun contributeHighscoresFragment(): HighscoresFragment

}